package com.example.cloudusageaggregator;

import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.dto.ResultPayload;
import com.example.cloudusageaggregator.model.UsageResult;
import com.example.cloudusageaggregator.service.HttpConnector;
import com.example.cloudusageaggregator.service.UsageAggregationService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        UsageAggregationService usageAggregationService = new UsageAggregationService();

        try {
            // Fetch dataset from the remote service
            String jsonDataset = httpConnector.fetchDatasetAsJson();
            System.out.println("Dataset JSON: " + jsonDataset);
            // Convert JSON to Dataset object
            DataSetResponse dataset = usageAggregationService.convertJsonToDataset(jsonDataset);

            // Überprüfen Sie die Anzahl der Ereignisse
            if (dataset != null && dataset.getEvents() != null) {
                System.out.println("Number of events fetched: " + dataset.getEvents().size());
            } else {
                System.err.println("Dataset is null or events list is empty.");
            }



            if (dataset != null && dataset.getEvents() != null) {
                // Process dataset into usage results
                List<UsageResult> results = usageAggregationService.processDatasetToResult(dataset.getEvents());

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
