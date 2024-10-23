package com.example.cloudusageaggregator.dto;

import com.example.cloudusageaggregator.model.UsageResult;

import java.util.Collection;

public class ResultPayload {
    private Collection<UsageResult> results;

    public ResultPayload(Collection<UsageResult> results) {
        this.results = results;
    }

    public Collection<UsageResult> getResults() {
        return results;
    }
}
