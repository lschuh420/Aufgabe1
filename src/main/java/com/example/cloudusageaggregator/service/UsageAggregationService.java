package com.example.cloudusageaggregator.service;

import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.model.Event;
import com.example.cloudusageaggregator.model.UsageResult;
import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class UsageAggregationService {
    private final Gson gson = new Gson();

    // Convert a JSON string to a DataSetResponse object.
    public DataSetResponse convertJsonToDataset(String json) {
        return gson.fromJson(json, DataSetResponse.class);
    }

    // Convert a ResultPayload object to a JSON string.
    public String convertResultToJson(List<UsageResult> results) {
        return gson.toJson(results);
    }



    // Process the dataset and aggregate results.
    public List<UsageResult> processDatasetToResult(Collection<Event> events) {
        Map<String, List<Event>> eventsByWorkload = events.stream()
                .collect(Collectors.groupingBy(Event::workloadId));

        Map<String, Long> customerConsumption = new HashMap<>();

        for (List<Event> workloadEvents : eventsByWorkload.values()) {
            workloadEvents.sort(Comparator.comparingLong(Event::timestamp));
            Long startTime = null;
            String customerId = null;

            for (Event event : workloadEvents) {
                customerId = event.customerId();

                if ("start".equals(event.eventType())) {
                    startTime = event.timestamp();
                } else if ("stop".equals(event.eventType()) && startTime != null) {
                    long consumption = event.timestamp() - startTime;
                    customerConsumption.merge(customerId, consumption, Long::sum);
                    startTime = null; // Reset für das nächste Paar
                }
            }
        }

        return customerConsumption.entrySet().stream()
                .map(entry -> new UsageResult(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


}
