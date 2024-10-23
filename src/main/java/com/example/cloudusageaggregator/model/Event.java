package com.example.cloudusageaggregator.model;

import com.google.gson.annotations.SerializedName;

public record Event(
        @SerializedName("customer_id") String customerId,
        @SerializedName("workload_id") String workloadId,
        long timestamp,
        @SerializedName("event_type") String eventType) {
}

