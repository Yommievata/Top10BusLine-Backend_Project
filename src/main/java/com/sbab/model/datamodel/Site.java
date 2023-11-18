package com.sbab.model.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class Site {
    @JsonProperty("SiteId")
    private int siteId;
    @JsonProperty("SiteName")
    private String siteName;
    @JsonProperty("StopAreaNumber")
    private int stopAreaNumber;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;
}
