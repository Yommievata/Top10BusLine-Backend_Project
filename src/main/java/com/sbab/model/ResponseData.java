package com.sbab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;

@Data
public class ResponseData<E> {
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("Result")
    private Collection<E> result;
}
