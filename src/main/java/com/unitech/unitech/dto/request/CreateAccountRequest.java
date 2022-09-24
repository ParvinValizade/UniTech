package com.unitech.unitech.dto.request;

import java.math.BigDecimal;

public class CreateAccountRequest {
    private BigDecimal initialCredit;
    private String userId;

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public String getUserId() {
        return userId;
    }
}
