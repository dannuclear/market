package ru.bisoft.market.dto;

import org.springframework.beans.BeanUtils;

import com.google.protobuf.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.piapi.contract.v1.BrandData;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;
import ru.tinkoff.piapi.contract.v1.Share;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareDTO {
    // Figi-идентификатор инструмента.
    private volatile Object figi;
    // Тикер инструмента.
    private volatile Object ticker;
    // Класс-код (секция торгов).
    private volatile Object classCode;
    // Isin-идентификатор инструмента.
    private volatile Object isin;
    // Лотность инструмента. Возможно совершение операций только на количества
    // ценной бумаги, кратные параметру *lot*. Подробнее:
    // [лот](https://russianinvestments.github.io/investAPI/glossary#lot)
    private int lot;
    // Валюта расчётов.
    private volatile Object currency;
    // Коэффициент ставки риска длинной позиции по клиенту. 2 – клиент со
    // стандартным уровнем риска (КСУР). 1 – клиент с повышенным уровнем риска
    // (КПУР)
    private Quotation klong;
    // Коэффициент ставки риска короткой позиции по клиенту. 2 – клиент со
    // стандартным уровнем риска (КСУР). 1 – клиент с повышенным уровнем риска
    // (КПУР)
    private Quotation kshort;
    // Ставка риска начальной маржи для КСУР лонг.Подробнее: [ставка риска в
    // лонг](https://help.tinkoff.ru/margin-trade/long/risk-rate/)
    private Quotation dlong;
    // Ставка риска начальной маржи для КСУР шорт. Подробнее: [ставка риска в
    // шорт](https://help.tinkoff.ru/margin-trade/short/risk-rate/)
    private Quotation dshort;
    // Ставка риска начальной маржи для КПУР лонг. Подробнее: [ставка риска в
    // лонг](https://help.tinkoff.ru/margin-trade/long/risk-rate/)
    private Quotation dlongMin;
    // Ставка риска начальной маржи для КПУР шорт. Подробнее: [ставка риска в
    // шорт](https://help.tinkoff.ru/margin-trade/short/risk-rate/)
    private Quotation dshortMin;
    // Признак доступности для операций в шорт.
    private boolean shortEnabledFlag;
    // Название инструмента.
    private volatile Object name;
    // Tорговая площадка (секция биржи).
    private volatile Object exchange;
    // Дата IPO акции в часовом поясе UTC.
    private Timestamp ipoDate;
    // Размер выпуска.
    private long issueSize;
    // Код страны риска, т.е. страны, в которой компания ведёт основной бизнес.
    private volatile Object countryOfRisk;
    // Наименование страны риска, т.е. страны, в которой компания ведёт основной
    // бизнес.
    private volatile Object countryOfRiskName;
    // Сектор экономики.
    private volatile Object sector;
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
    private Quotation minPriceIncrement;
    // Параметр указывает на возможность торговать инструментом через API.
    private boolean apiTradeAvailableFlag;
    // Уникальный идентификатор инструмента.
    private volatile Object uid;
    // Реальная площадка исполнения расчётов (биржа).
    private int realExchange;
    // Уникальный идентификатор позиции инструмента.
    private volatile Object positionUid;
    // Уникальный идентификатор актива.
    private volatile Object assetUid;
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
    private Timestamp first1MinCandleDate;
    // Дата первой дневной свечи.
    private Timestamp first1DayCandleDate;
    // Информация о бренде.
    private BrandData brand;

    public static ShareDTO fromProto(Share proto) {
        ShareDTO dto = new ShareDTO();
        BeanUtils.copyProperties(proto, dto);
        return dto;
    }
}