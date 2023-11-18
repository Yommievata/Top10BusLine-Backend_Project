package com.sbab.model.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StopPoint {
    @JsonProperty("StopPointNumber")
    private int stopPointNumber;
    @JsonProperty("StopPointName")
    private String StopPointName;
    @JsonProperty("StopAreaNumber")
    private int stopAreaNumber;
    @JsonProperty("LocationNorthingCoordinate")
    private Double locationNorthingCoordinate;
    @JsonProperty("LocationEastingCoordinate")
    private Double locationEastingCoordinate;
    @JsonProperty("ZoneShortName")
    private String zoneShortName;
    @JsonProperty("StopAreaTypeCode")
    private String stopAreaTypeCode;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;
}
