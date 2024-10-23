package com.example.cloudusageaggregator.service;

import com.example.cloudusageaggregator.dto.ResultPayload;
import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.model.Event;
import com.example.cloudusageaggregator.model.UsageResult;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashMap;

public class UsageAggregationService {
    private final Gson gson = new Gson();

    // Convert a JSON string to a DataSetResponse object.
    public DataSetResponse convertJsonToDataset(String json) {
        return gson.fromJson(json, DataSetResponse.class);
    }

    // Convert a ResultPayload object to a JSON string.
    public String convertResultToJson(ResultPayload result) {
        return gson.toJson(result);
    }

    // Process the dataset and aggregate results.
    public ResultPayload processDatasetToResult(Collection<Event> events) {
        HashMap<String, UsageResult> results = new HashMap<>();
        events.forEach(event -> {
            results.computeIfAbsent(event.customerId(), k -> new UsageResult(k, 0));

            UsageResult customerResult = results.get(event.customerId());
            long consumptionChange = event.eventType().equals("start") ? -event.timestamp() : event.timestamp();
            customerResult.setConsumption(customerResult.getConsumption() + consumptionChange);
        });

        return new ResultPayload(results.values());
    }
}
