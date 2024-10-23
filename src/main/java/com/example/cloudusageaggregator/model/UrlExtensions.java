package com.example.cloudusageaggregator.model;

public enum UrlExtensions {
    DATASET("/v1/dataset"),
    RESULT("/v1/result"),
    HEALTH("/health");

    public final String path;

    UrlExtensions(String extension) {
        this.path = extension;
    }
}
