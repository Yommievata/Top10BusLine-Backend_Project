package com.sbab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ModelBase<E> {

    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("Message")
    private String statusMessage;
    @JsonProperty("ExecutionTime")
    private int executionTime;
    @JsonProperty("ResponseData")
    private ResponseData<E> responseData;
}
