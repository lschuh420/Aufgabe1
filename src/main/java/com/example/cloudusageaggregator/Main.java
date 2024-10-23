package com.example.cloudusageaggregator;

import com.example.cloudusageaggregator.ResultPayload.ResultPayload;
import com.example.cloudusageaggregator.model.DataSetResponse;
import com.example.cloudusageaggregator.model.UsageResult;
import com.example.cloudusageaggregator.service.HttpConnector;
import com.example.cloudusageaggregator.service.UsageAggregationService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        UsageAggregationService usageAggregationService = new UsageAggregationService();

        try {
            // Datensatz abrufen
            String jsonDataset = httpConnector.fetchDatasetAsJson();
            System.out.println("Dataset JSON fetched successfully.");

            // JSON zu Dataset-Objekt konvertieren
            DataSetResponse dataset = usageAggregationService.convertJsonToDataset(jsonDataset);

            if (dataset != null && dataset.getEvents() != null) {
                System.out.println("Number of events fetched: " + dataset.getEvents().size());

                // Verarbeitung zu UsageResult-Liste
                List<UsageResult> usageResults = usageAggregationService.processDatasetToResult(dataset.getEvents());
                System.out.println("Number of usage results: " + usageResults.size());

                // Ergebnisse in ResultPayload einbetten
                ResultPayload resultPayload = new ResultPayload(usageResults);

                // JSON aus ResultPayload generieren
                String resultJson = usageAggregationService.convertResultToJson(resultPayload);
                System.out.println("Result JSON: " + resultJson);

                // Ergebnisse an den Server senden
                httpConnector.sendResult(resultJson);

                System.out.println("Results processed and sent successfully.");
            } else {
                System.err.println("Error: Dataset is null or event list is empty.");
            }

        } catch (Exception e) {
            System.err.println("Error processing usage data");
            e.printStackTrace();
        }
    }
}
