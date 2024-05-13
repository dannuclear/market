package ru.bisoft.market.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.market.dto.InstrumentShortDTO;
import ru.bisoft.market.dto.OrderDTO;
import ru.bisoft.market.repo.InstrumentRepo;
import ru.tinkoff.piapi.contract.v1.FavoriteInstrument;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.contract.v1.MarketDataResponse;
import ru.tinkoff.piapi.contract.v1.OrderDirection;
import ru.tinkoff.piapi.contract.v1.OrderType;
import ru.tinkoff.piapi.contract.v1.PostOrderResponse;
import ru.tinkoff.piapi.contract.v1.SubscriptionStatus;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import ru.tinkoff.piapi.core.stream.StreamProcessor;
import ru.tinkoff.piapi.core.utils.MapperUtils;

@RestController
@RequestMapping("/api/v1/instruments")
@RequiredArgsConstructor
@Log4j2
public class InstrumentController {
    private final InvestApi investApi;
    private final InstrumentRepo instrumentRepo;

    @GetMapping
    public ResponseEntity<Page<InstrumentShortDTO>> search(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        MarketDataService marketDataService = investApi.getMarketDataService();

        List<InstrumentShortDTO> favorites = instrumentsService.getFavorites()
                .thenApply(fis -> fis.stream().map(InstrumentShortDTO::fromProto).toList()).join();
        List<String> favoriteUuids = favorites.stream().map(InstrumentShortDTO::getUid).toList();
        Page<InstrumentShortDTO> result;
        if (StringUtils.isEmpty(search)) {
            result = new PageImpl<>(favorites, pageable, 0);
        } else {
            List<InstrumentShortDTO> instruments = instrumentsService.findInstrument(search)
                    .thenApply(ins -> ins.stream()
                            .map(InstrumentShortDTO::fromProto)
                            .peek(i -> i.setFavorite(favoriteUuids.contains(i.getUid())))
                            .toList())
                    .join();
            if (instruments.size() > 20)
                instruments = instruments.subList(0, 20);
            instruments = instruments.stream().filter(i -> i.getInstrumentType().compareTo("share") == 0).toList();
            result = new PageImpl<>(instruments, pageable, instruments.size());
        }
        if (result.getSize() > 0) {
            List<String> uids = result.stream().map(InstrumentShortDTO::getUid).toList();
            List<LastPrice> prices = marketDataService.getLastPricesSync(uids);

            result.stream().forEach(ins -> {
                for (LastPrice price : prices)
                    if (price.getInstrumentUid().compareTo(ins.getUid()) == 0)
                        ins.setLastPrice(
                                new BigDecimal(price.getPrice().getUnits() + "." + price.getPrice().getNano()));
            });
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}/toggleFavorite")
    public ResponseEntity<String> toggleFavorite(@PathVariable String id) {
        InstrumentsService instrumentsService = investApi.getInstrumentsService();
        List<FavoriteInstrument> favorites = instrumentsService.getFavoritesSync();
        String uid = id.toString();
        FavoriteInstrument favoriteInstrument = favorites.stream().filter(i -> i.getUid().compareTo(uid) == 0)
                .findFirst().orElse(null);

        if (favoriteInstrument == null) {
            favorites = instrumentsService.addFavoritesSync(Arrays.asList(uid));
        } else {
            favorites = instrumentsService.deleteFavoritesSync(Arrays.asList(uid));
        }
        return ResponseEntity.ok("success");
    }

    @GetMapping("{figi}/info")
    public ResponseEntity<Instrument> getInstrumentInfo(@PathVariable String figi) {
        return ResponseEntity.ok(investApi.getInstrumentsService().getInstrumentByFigiSync(figi));
    }

    @PutMapping("favorites")
    public ResponseEntity<Page<FavoriteInstrument>> favorites(Pageable pageable) {
        List<FavoriteInstrument> favorites = investApi.getInstrumentsService().getFavoritesSync();
        return ResponseEntity.ok(new PageImpl<>(favorites, pageable, favorites.size()));
    }

    @GetMapping("lastPrices")
    public ResponseEntity<List<LastPrice>> getLastPrices(@RequestParam List<String> ids) {
        MarketDataService marketDataService = investApi.getMarketDataService();
        List<LastPrice> prices = marketDataService.getLastPrices(ids).join();
        return ResponseEntity.ok(prices);
    }

    @PostMapping("buy")
    public ResponseEntity<PostOrderResponse> buyOrder(@RequestBody OrderDTO order) {
        PostOrderResponse response = investApi.getOrdersService().postOrderSync(order.getInstrumentId(),
                order.getCount(), MapperUtils.bigDecimalToQuotation(order.getPrice()),
                OrderDirection.ORDER_DIRECTION_BUY, order.getAccountId(), OrderType.ORDER_TYPE_BESTPRICE,
                UUID.randomUUID().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{figi}/stream")
    public void start(@PathVariable String figi) {

        // Описываем, что делать с приходящими в стриме данными
        StreamProcessor<MarketDataResponse> processor = response -> {
            if (response.hasTradingStatus()) {
                log.info("Новые данные по статусам: {}", response);
            } else if (response.hasPing()) {
                log.info("пинг сообщение");
            } else if (response.hasCandle()) {
                log.info("Новые данные по свечам: {}", response);
            } else if (response.hasOrderbook()) {
                log.info("Новые данные по стакану: {}", response);
            } else if (response.hasTrade()) {
                log.info("Новые данные по сделкам: {}", response);
            } else if (response.hasSubscribeCandlesResponse()) {
                var subscribeResult = response.getSubscribeCandlesResponse().getCandlesSubscriptionsList().stream()
                        .collect(Collectors.groupingBy(
                                el -> el.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIPTION_STATUS_SUCCESS),
                                Collectors.counting()));
                logSubscribeStatus("свечи", subscribeResult.getOrDefault(true, 0L),
                        subscribeResult.getOrDefault(false, 0L));
            } else if (response.hasSubscribeInfoResponse()) {
                var subscribeResult = response.getSubscribeInfoResponse().getInfoSubscriptionsList().stream()
                        .collect(Collectors.groupingBy(
                                el -> el.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIPTION_STATUS_SUCCESS),
                                Collectors.counting()));
                logSubscribeStatus("статусы", subscribeResult.getOrDefault(true, 0L),
                        subscribeResult.getOrDefault(false, 0L));
            } else if (response.hasSubscribeOrderBookResponse()) {
                var subscribeResult = response.getSubscribeOrderBookResponse().getOrderBookSubscriptionsList().stream()
                        .collect(Collectors.groupingBy(
                                el -> el.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIPTION_STATUS_SUCCESS),
                                Collectors.counting()));
                logSubscribeStatus("стакан", subscribeResult.getOrDefault(true, 0L),
                        subscribeResult.getOrDefault(false, 0L));
            } else if (response.hasSubscribeTradesResponse()) {
                var subscribeResult = response.getSubscribeTradesResponse().getTradeSubscriptionsList().stream()
                        .collect(Collectors.groupingBy(
                                el -> el.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIPTION_STATUS_SUCCESS),
                                Collectors.counting()));
                logSubscribeStatus("сделки", subscribeResult.getOrDefault(true, 0L),
                        subscribeResult.getOrDefault(false, 0L));
            } else if (response.hasSubscribeLastPriceResponse()) {
                var subscribeResult = response.getSubscribeLastPriceResponse().getLastPriceSubscriptionsList().stream()
                        .collect(Collectors.groupingBy(
                                el -> el.getSubscriptionStatus().equals(SubscriptionStatus.SUBSCRIPTION_STATUS_SUCCESS),
                                Collectors.counting()));
                logSubscribeStatus("последние цены", subscribeResult.getOrDefault(true, 0L),
                        subscribeResult.getOrDefault(false, 0L));
            }
        };
        Consumer<Throwable> onErrorCallback = error -> log.error(error.toString());

        investApi.getMarketDataStreamService().newStream("orderbook_stream", processor, onErrorCallback).subscribeTrades(Arrays.asList(figi));
                //.subscribeLastPrices(Arrays.asList(figi));
                investApi.getMarketDataService().te
    }

    @GetMapping("{figi}/stop")
    public void stop(@PathVariable String figi) {
        //investApi.getMarketDataStreamService().getStreamById("orderbook_stream").unsubscribeCandles(Arrays.asList(figi));

        // закрытие стрима
        investApi.getMarketDataStreamService().getStreamById("orderbook_stream").cancel();
    }

    private static void logSubscribeStatus(String eventType, Long successed, Long failed) {
        log.info("удачных подписок на {}: {}. неудачных подписок на {}: {}.", eventType, successed, eventType, failed);
    }
}
