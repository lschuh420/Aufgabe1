package com.example.cloudusageaggregator;

import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.dto.ResultPayload;
import com.example.cloudusageaggregator.service.HttpConnector;
import com.example.cloudusageaggregator.service.UsageAggregationService;

public class Main {

    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        UsageAggregationService usageAggregationService = new UsageAggregationService();

        try {
            // Fetch dataset from the remote service
            String jsonDataset = httpConnector.fetchDatasetAsJson();

            // Convert JSON to Dataset object
            DataSetResponse dataset = usageAggregationService.convertJsonToDataset(jsonDataset);

            if (dataset != null && dataset.getEvents() != null) {
                // Process dataset into usage results
                ResultPayload results = usageAggregationService.processDatasetToResult(dataset.getEvents());

                // Convert the result list into JSON format
                String resultJson = usageAggregationService.convertResultToJson(results);

                // Send the results as JSON back to the server
                httpConnector.sendResult(resultJson);

                System.out.println("Results processed and sent successfully.");
            } else {
                System.err.println("Error: Dataset is null or event list is empty.");
            }

        } catch (Exception e) {
            System.err.println("Error processing usage data: " + e.getMessage());
        }
    }
}
