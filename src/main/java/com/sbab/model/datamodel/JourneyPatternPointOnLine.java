package com.sbab.model.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class JourneyPatternPointOnLine {
    @JsonProperty("LineNumber")
    private int lineNumber;
    @JsonProperty("DirectionCode")
    private int directionCode;
    @JsonProperty("JourneyPatternPointNumber")
    private int journeyPatternPointNumber;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime; //you can use Instant instead of LocalDateTime
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;
}
