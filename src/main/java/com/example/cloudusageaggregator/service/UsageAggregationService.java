package com.example.cloudusageaggregator.service;

import com.example.cloudusageaggregator.ResultPayload.ResultPayload;
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
    public String convertResultToJson(ResultPayload resultPayload) {
        return gson.toJson(resultPayload);
    }




    // Process the dataset and aggregate results.
    public List<UsageResult> processDatasetToResult(Collection<Event> events) {
        Map<String, List<Event>> eventsByWorkload = events.stream()
                .filter(event -> event.workloadId() != null)
                .collect(Collectors.groupingBy(Event::workloadId));

        Map<String, Long> customerConsumption = new HashMap<>();

        for (List<Event> workloadEvents : eventsByWorkload.values()) {
            workloadEvents.sort(Comparator.comparingLong(Event::timestamp));
            Long startTime = null;
            String customerId = null;

            for (Event event : workloadEvents) {
                if (!isValidEvent(event)) {
                    System.err.println("Invalid event detected: " + event);
                    continue;
                }

                customerId = event.customerId();
                String eventType = event.eventType();

                if ("start".equals(eventType)) {
                    startTime = event.timestamp();
                } else if ("stop".equals(eventType) && startTime != null) {
                    long consumption = event.timestamp() - startTime; // Verbrauch in Millisekunden
                    customerConsumption.merge(customerId, consumption, Long::sum);
                    startTime = null; // Zurücksetzen für das nächste Paar
                }
            }
        }

        return customerConsumption.entrySet().stream()
                .map(entry -> new UsageResult(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private boolean isValidEvent(Event event) {
        return event.customerId() != null && event.workloadId() != null && event.eventType() != null;
    }




}
