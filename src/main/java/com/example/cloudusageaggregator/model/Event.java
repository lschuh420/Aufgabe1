package com.example.cloudusageaggregator.model;

import com.google.gson.annotations.SerializedName;

public record Event(String customerId, String workloadId, long timestamp, String eventType) {
}

