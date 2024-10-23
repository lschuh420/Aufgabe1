package com.example.cloudusageaggregator.model;

import com.google.gson.annotations.SerializedName;

public class UsageResult {
    private String customerId;
    private long consumption;

    public UsageResult(String customerId, long consumption) {
        this.customerId = customerId;
        this.consumption = consumption;
    }

    public String getCustomerId() {
        return customerId;
    }

    public long getConsumption() {
        return consumption;
    }

    public void setConsumption(long consumption) {
        this.consumption = consumption;
    }
}
