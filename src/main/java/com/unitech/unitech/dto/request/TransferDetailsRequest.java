package com.unitech.unitech.dto.request;

import java.math.BigDecimal;

public class TransferDetailsRequest {
    private String destinationAccountId;
    private BigDecimal amount;

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
