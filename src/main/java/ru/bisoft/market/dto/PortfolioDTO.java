package ru.bisoft.market.dto;

import java.math.BigDecimal;
import java.util.List;

import com.google.type.Money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;
import ru.tinkoff.piapi.core.models.VirtualPosition;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDTO {
    private Money totalAmountShares;
    private Money totalAmountBonds;
    private Money totalAmountEtfs;
    private Money totalAmountCurrencies;
    private Money totalAmountFutures;
    private Money totalAmountPortfolio;
    private BigDecimal expectedYield;
    private List<Position> positions;
    private Money totalAmountSp;
    private Money totalAmountOptions;
    private List<VirtualPosition> virtualPositions;

    public static PortfolioDTO fromProto(Portfolio proto) {
        return null;
        // return PortfolioDTO.builder()
        // .totalAmountShares(proto.getTotalAmountShares())
        // .totalAmountBonds(proto.getTotalAmountBonds())
        // .totalAmountEtf(proto.getTotalAmountEtf())
        // .totalAmountCurrencies(proto.getTotalAmountCurrencies())
        // .totalAmountFutures(proto.getTotalAmountFutures())
        // .expectedYield(proto.getExpectedYield())
        // .positions(proto.getPositionsList())
        // .accountId(proto.getAccountId())
        // .totalAmountOptions(proto.getTotalAmountOptions())
        // .totalAmountSp(proto.getTotalAmountSp())
        // .totalAmountPortfolio(proto.getTotalAmountPortfolio())
        // .virtualPositions(proto.getVirtualPositionsList())
        // .build();
    }
}
