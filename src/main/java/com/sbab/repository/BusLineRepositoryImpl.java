package com.sbab.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.model.ModelBase;
import com.sbab.model.datamodel.*;
import okhttp3.OkHttpClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import okhttp3.Request;
import okhttp3.Response;
import com.sbab.model.datamodel.JourneyPatternPointOnLine;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;

@Repository
public class BusLineRepositoryImpl implements BusLineRepository {

    @Value("${api.key}")
    private String apiKey;

    @Value("${line.url}")
    private String line_Url;

    @Value("${stop.point.url}")
    private String stop_Url;

    @Value("${journey.pattern.url}")
    private String journey_Url;

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = LoggerFactory.getLogger(BusLineRepositoryImpl.class);

    @Override
    public Collection<Line> getAllLines() throws IOException {
        return collectData(line_Url, new TypeReference<>() {
        });
    }

    @Override
    public Collection<StopPoint> getAllStopPoints() throws IOException {
        return collectData(stop_Url, new TypeReference<>() {
        });
    }

    @Override
    public Collection<JourneyPatternPointOnLine> getAllBusJourneyPatterns() throws IOException {
        return collectData(journey_Url, new TypeReference<>() {
        });
    }

    private LinkedHashMap<Integer, Collection<JourneyPatternPointOnLine>> getBusJourneyPatternsByTopRank(Integer topRanks) throws IOException {
        Collection<JourneyPatternPointOnLine> journeyPatterns = getAllBusJourneyPatterns();

        return journeyPatterns.stream()
                .collect(java.util.stream.Collectors.groupingBy(JourneyPatternPointOnLine::getLineNumber))
                .entrySet().parallelStream()
                .sorted(java.util.Comparator.comparingInt(entry -> -entry.getValue().size()))
                .limit(topRanks)
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (v1, v2) -> v1, java.util.LinkedHashMap::new));
    }

    public Map<String, Collection<?>> getStopPointsByLineNumber(Integer topRanks) throws IOException {
        Collection<StopPoint> allStopPoints = getAllStopPoints();
        LinkedHashMap<Integer, Collection<JourneyPatternPointOnLine>> topJourneyPatterns = getBusJourneyPatternsByTopRank(topRanks);
        Map<String, Collection<?>> sortedStopPointsByTopLines = new HashMap<>();

        for (Map.Entry<Integer, Collection<JourneyPatternPointOnLine>> entry : topJourneyPatterns.entrySet()) {
            Collection<StopPoint> stopPoints = new ArrayList<>();
            Collection<JourneyPatternPointOnLine> journeyPatterns = entry.getValue();

            for (JourneyPatternPointOnLine journeyPattern : journeyPatterns) {
                String journeyPatternPointNumber = String.valueOf(journeyPattern.getJourneyPatternPointNumber());

                List<String> stopPointNumbers = Arrays.asList(journeyPatternPointNumber.split(","));

                for (StopPoint stopPoint : allStopPoints) {
                    if (stopPointNumbers.contains(String.valueOf(stopPoint.getStopPointNumber()))) {
                        stopPoints.add(stopPoint);
                    }
                }
            }

            sortedStopPointsByTopLines.put(String.valueOf(entry.getKey()), stopPoints);
        }

        return sortedStopPointsByTopLines;
    }

    public  <T> Collection<T> collectData(String url, TypeReference<ModelBase<T>> typeReference) throws IOException {
        if (url == null) {
            logger.error("URL is null");
            throw new IllegalArgumentException("URL cannot be null");
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", apiKey)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                ModelBase<T> modelBase = objectMapper.readValue(response.body().string(), typeReference);

                if (modelBase.getResponseData() != null) {
                    return modelBase.getResponseData().getResult();
                } else {
                    logger.error("Error fetching data from {}", url);
                    throw new IOException("Unexpected code " + response);
                }
            } else {
                handleErrorResponse(url, response);
                return null;
            }
        }
    }

    private void handleErrorResponse(String url, Response response) throws IOException {
        logger.error("Error fetching data from {}. HTTP Status: {}, Message: {}",
                url, response.code(), response.message());
        throw new IOException("Unexpected code " + response.code() + ", Message: " + response.message());
    }
}
