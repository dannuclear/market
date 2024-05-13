package ru.bisoft.market.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ru.bisoft.market.utils.ProtoUtils;
import ru.tinkoff.piapi.contract.v1.FavoriteInstrument;
import ru.tinkoff.piapi.contract.v1.InstrumentShort;

@Data
@Builder
public class InstrumentShortDTO {
    private String isin;
    private String figi;
    private String ticker;
    private String classCode;
    private String instrumentType;
    private String name;
    private String uid;
    private String positionUid;
    private int instrumentKind;
    private boolean apiTradeAvailableFlag;
    private boolean forIisFlag;
    private LocalDateTime first1MinCandleDate;
    private LocalDateTime first1DayCandleDate;
    private boolean forQualInvestorFlag;
    private boolean weekendFlag;
    private boolean blockedTcaFlag;
    private boolean favorite;
    private BigDecimal lastPrice;

    public static InstrumentShortDTO fromProto(InstrumentShort proto) {
        return InstrumentShortDTO.builder()
                .isin(proto.getIsin())
                .figi(proto.getFigi())
                .ticker(proto.getTicker())
                .classCode(proto.getClassCode())
                .instrumentType(proto.getInstrumentType())
                .name(proto.getName())
                .uid(proto.getUid())
                .positionUid(proto.getPositionUid())
                .instrumentKind(proto.getInstrumentKindValue())
                .apiTradeAvailableFlag(proto.getApiTradeAvailableFlag())
                .forIisFlag(proto.getForIisFlag())
                .first1MinCandleDate(ProtoUtils.fromTimestamp(proto.getFirst1MinCandleDate()))
                .first1DayCandleDate(ProtoUtils.fromTimestamp(proto.getFirst1DayCandleDate()))
                .forQualInvestorFlag(proto.getForQualInvestorFlag())
                .weekendFlag(proto.getWeekendFlag())
                .blockedTcaFlag(proto.getBlockedTcaFlag())
                .favorite(false)
                .build();
    }

    public static InstrumentShortDTO fromProto (FavoriteInstrument proto) {
        return InstrumentShortDTO.builder()
                .isin(proto.getIsin())
                .figi(proto.getFigi())
                .ticker(proto.getTicker())
                .classCode(proto.getClassCode())
                .instrumentType(proto.getInstrumentType())
                .name(proto.getName())
                .uid(proto.getUid())
                .instrumentKind(proto.getInstrumentKindValue())
                .apiTradeAvailableFlag(proto.getApiTradeAvailableFlag())
                .favorite(true)
                .build();
    }
}
