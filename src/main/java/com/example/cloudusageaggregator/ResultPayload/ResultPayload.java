package com.example.cloudusageaggregator.ResultPayload;

import com.example.cloudusageaggregator.model.UsageResult;

import java.util.Collection;

import com.google.gson.annotations.SerializedName;

public class ResultPayload {
    private Collection<UsageResult> result;

    public ResultPayload(Collection<UsageResult> result) {
        this.result = result;
    }

    public Collection<UsageResult> getResult() {
        return result;
    }
}
