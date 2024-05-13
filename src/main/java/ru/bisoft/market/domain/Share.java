package ru.bisoft.market.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.piapi.contract.v1.BrandData;
import ru.tinkoff.piapi.contract.v1.MoneyValue;


@Data
@Table
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Share {
    // Уникальный идентификатор инструмента.
    @Id
    private UUID uid;
    // Figi-идентификатор инструмента.
    private String figi;
    // Тикер инструмента.
    private String ticker;
    // Класс-код (секция торгов).
    private String classCode;
    // Isin-идентификатор инструмента.
    private String isin;
    // Лотность инструмента. Возможно совершение операций только на количества
    // ценной бумаги, кратные параметру *lot*. Подробнее:
    // [лот](https://russianinvestments.github.io/investAPI/glossary#lot)
    private int lot;
    // Валюта расчётов.
    private String currency;
    // Коэффициент ставки риска длинной позиции по клиенту. 2 – клиент со
    // стандартным уровнем риска (КСУР). 1 – клиент с повышенным уровнем риска
    // (КПУР)
    private BigDecimal klong;
    // Коэффициент ставки риска короткой позиции по клиенту. 2 – клиент со
    // стандартным уровнем риска (КСУР). 1 – клиент с повышенным уровнем риска
    // (КПУР)
    private BigDecimal kshort;
    // Ставка риска начальной маржи для КСУР лонг.Подробнее: [ставка риска в
    // лонг](https://help.tinkoff.ru/margin-trade/long/risk-rate/)
    private BigDecimal dlong;
    // Ставка риска начальной маржи для КСУР шорт. Подробнее: [ставка риска в
    // шорт](https://help.tinkoff.ru/margin-trade/short/risk-rate/)
    private BigDecimal dshort;
    // Ставка риска начальной маржи для КПУР лонг. Подробнее: [ставка риска в
    // лонг](https://help.tinkoff.ru/margin-trade/long/risk-rate/)
    private BigDecimal dlongMin;
    // Ставка риска начальной маржи для КПУР шорт. Подробнее: [ставка риска в
    // шорт](https://help.tinkoff.ru/margin-trade/short/risk-rate/)
    private BigDecimal dshortMin;
    // Признак доступности для операций в шорт.
    private boolean shortEnabledFlag;
    // Название инструмента.
    private String name;
    // Tорговая площадка (секция биржи).
    private String exchange;
    // Дата IPO акции в часовом поясе UTC.
    private LocalDateTime ipoDate;
    // Размер выпуска.
    private long issueSize;
    // Код страны риска, т.е. страны, в которой компания ведёт основной бизнес.
    private String countryOfRisk;
    // Наименование страны риска, т.е. страны, в которой компания ведёт основной
    // бизнес.
    private String countryOfRiskName;
    // Сектор экономики.
    private String sector;
    // Плановый размер выпуска.
    private long issueSizePlan;
    // Номинал.
    private MoneyValue nominal;
    // Текущий режим торгов инструмента.
    private int tradingStatus;
    // Признак внебиржевой ценной бумаги.
    private boolean otcFlag;
    // Признак доступности для покупки.
    private boolean buyAvailableFlag;
    // Признак доступности для продажи.
    private boolean sellAvailableFlag;
    // Признак наличия дивидендной доходности.
    private boolean divYieldFlag;
    // Тип акции. Возможные значения:
    // [ShareType](https://russianinvestments.github.io/investAPI/instruments#sharetype)
    private int shareType;
    // Шаг цены.
    private BigDecimal minPriceIncrement;
    // Параметр указывает на возможность торговать инструментом через API.
    private boolean apiTradeAvailableFlag;
    // Реальная площадка исполнения расчётов (биржа).
    private int realExchange;
    // Уникальный идентификатор позиции инструмента.
    private String positionUid;
    // Уникальный идентификатор актива.
    private String assetUid;
    // Признак доступности для ИИС.
    private boolean forIisFlag;
    // Флаг отображающий доступность торговли инструментом только для
    // квалифицированных инвесторов.
    private boolean forQualInvestorFlag;
    // Флаг отображающий доступность торговли инструментом по выходным
    private boolean weekendFlag;
    // Флаг заблокированного ТКС
    private boolean blockedTcaFlag;
    // Флаг достаточной ликвидности
    private boolean liquidityFlag;
    // Дата первой минутной свечи.
    private LocalDateTime first1MinCandleDate;
    // Дата первой дневной свечи.
    private LocalDateTime first1DayCandleDate;
    // Информация о бренде.
    private BrandData brand;

    public static Share fromProto(ru.tinkoff.piapi.contract.v1.Share proto) {
        return Share.builder()
                .uid(UUID.fromString(proto.getUid()))
                .build();
    }
}