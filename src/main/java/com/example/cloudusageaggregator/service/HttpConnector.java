package com.example.cloudusageaggregator.service;

import com.example.cloudusageaggregator.model.UrlExtensions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpConnector {
    private final String BASE_URL = "http://localhost:8080";

    // Fetch dataset as JSON from the remote server.
    public String fetchDatasetAsJson() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(getWebsiteUrl(UrlExtensions.DATASET)))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error fetching dataset: " + response.body());
            }
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


        // Send the result JSON to the server.
        public void sendResult(String json) {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(getWebsiteUrl(UrlExtensions.RESULT)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Response code: " + response.statusCode());
                System.out.println("Response body: " + response.body());

                if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Error: " + response.body());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }


    // Helper method to construct the full URL.
    private String getWebsiteUrl(UrlExtensions extension) {
        return BASE_URL + extension.path;
    }
}
