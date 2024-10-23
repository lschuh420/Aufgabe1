package com.example.cloudusageaggregator.model;

public record Event(String customerId, String workloadId, long timestamp, String eventType) {
    // No need for additional getters, handled by record.
}
