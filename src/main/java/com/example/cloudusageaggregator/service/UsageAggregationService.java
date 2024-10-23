package com.example.cloudusageaggregator.service;

import com.example.cloudusageaggregator.dto.ResultPayload;
import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.model.Event;
import com.example.cloudusageaggregator.model.UsageResult;
import com.google.gson.Gson;

import java.util.*;

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
        Map<String, UsageResult> results = new HashMap<>();
        // Ihre Verarbeitung bleibt gleich
        return new ArrayList<>(results.values());
    }

}
