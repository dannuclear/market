package ru.bisoft.market.dto;

import java.math.BigDecimal;

import lombok.Data;
import ru.tinkoff.piapi.contract.v1.Quotation;

@Data
public class OrderDTO {
    private String instrumentId;
    private String accountId;
    private Integer count;
    private BigDecimal price;

    public Quotation getPriceQuotation() {
        if (price == null)
            return null;
        String[] str = price.toString().split("\\.");
        long units = Long.parseLong(str[0]);
        int nano = Integer.parseInt(str[1]);
        return Quotation.newBuilder().setUnits(units).setNano(nano).build();
    }
}
