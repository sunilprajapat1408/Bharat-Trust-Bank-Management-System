package com.bharattrustbank.model;

public enum AccountStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    CLOSED("CLOSED");
    
    private final String value;
    
    AccountStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static AccountStatus fromString(String value) {
        if (value == null) {
            return ACTIVE;
        }
        for (AccountStatus status : AccountStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return ACTIVE;
    }
}

