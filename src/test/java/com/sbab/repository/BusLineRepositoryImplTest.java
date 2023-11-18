package com.sbab.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.model.datamodel.JourneyPatternPointOnLine;
import com.sbab.model.datamodel.Line;
import com.sbab.model.datamodel.StopPoint;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = BusLineRepositoryImplTest.TestConfig.class)
public class BusLineRepositoryImplTest {
    @Autowired
    private Environment environment;
    @Mock
    private OkHttpClient okHttpClient;
    @InjectMocks
    private BusLineRepositoryImpl busLineRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(busLineRepository, "apiKey", environment.getProperty("api.key"));
        ReflectionTestUtils.setField(busLineRepository, "line_Url", environment.getProperty("line.url"));
        ReflectionTestUtils.setField(busLineRepository, "stop_Url", environment.getProperty("stop.point.url"));
        ReflectionTestUtils.setField(busLineRepository, "journey_Url", environment.getProperty("journey.pattern.url"));
    }

    @Test
    void testGetAllLines() throws IOException {
        when(okHttpClient.newCall(any(Request.class))).thenAnswer(invocation -> {
            return new okhttp3.Response.Builder()
                    .protocol(okhttp3.Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .request(invocation.getArgument(0))
                    .body(okhttp3.ResponseBody.create(mockLineResponse(), null))
                    .build();
        });

        Collection<Line> result = busLineRepository.getAllLines();

        List<Line> expectedLines = List.of(createLine());

        assertEquals(expectedLines, filterLines(result, 1));
        assertNotEquals(expectedLines, filterLines(result, 101));
    }

    private String mockLineResponse() {
        return "{\"responseData\": {\"result\": " +
                "[{\"lineNumber\": 1, \"lineDesignation\": 1, \"defaultTransportMode\": \"blåbuss\", " +
                "\"defaultTransportModeCode\": \"BUS\", \"lastModifiedUtcDateTime\": \"2007-08-24 00:00:00.000\", " +
                "\"existsFromDate\": \"2007-08-24 00:00:00.000\"}," +
                "{\"lineNumber\": 101, \"lineDesignation\": 101, \"defaultTransportMode\": \"some_mode\", " +
                "\"defaultTransportModeCode\": \"BUS\", \"lastModifiedUtcDateTime\": \"2023-06-08 00:00:00.000\", " +
                "\"existsFromDate\": \"2023-06-08 00:00:00.000\"}]}}";
    }

    private Line createLine() {
        Line line = new Line();
        line.setLineNumber(1);
        line.setLineDesignation("1");
        line.setDefaultTransportMode("blåbuss");
        line.setDefaultTransportModeCode("BUS");
        line.setLastModifiedUtcDateTime("2007-08-24 00:00:00.000");
        line.setExistsFromDate("2007-08-24 00:00:00.000");
        return line;
    }

    private List<Line> filterLines(Collection<Line> lines, int lineNumber) {
        return lines.stream()
                .filter(line -> line.getLineNumber() == lineNumber)
                .collect(Collectors.toList());
    }

    @Test
    void testGetAllStopPoints() throws IOException {
        when(okHttpClient.newCall(any(Request.class))).thenAnswer(invocation -> new Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .request(invocation.getArgument(0))
                .body(ResponseBody.create(mockStopPointsResponse(), null))
                .build());

        Collection<StopPoint> result = busLineRepository.getAllStopPoints();

        List<StopPoint> expectedStopPoints = List.of(createStopPoint());

        assertEquals(expectedStopPoints, filterStopPoints(result, 63305));
        assertNotEquals(expectedStopPoints, filterStopPoints(result, 12345));

        assertEquals(expectedStopPoints.size(), filterStopPoints(result, 63305).size(), "Number of stop points is not as expected");
        assertNotEquals(expectedStopPoints.size(), filterStopPoints(result, 12345).size(), "Number of stop points is as expected");
    }

    private String mockStopPointsResponse() {
        return "{\"responseData\": {\"result\": [" +
                "{\"StopPointNumber\": 63305, \"StopPointName\": \"Skeppsmyra affär\", " +
                "\"StopAreaNumber\": 63305, \"LocationNorthingCoordinate\": 59.828653999632, " +
                "\"LocationEastingCoordinate\": 19.052825999832, \"ZoneShortName\": \"C\", " +
                "\"StopAreaTypeCode\": \"BUSTERM\", \"LastModifiedUtcDateTime\": \"2022-08-22 00:00:00.000\", " +
                "\"ExistsFromDate\": \"2022-08-22 00:00:00.000\"}," +
                "{\"StopPointNumber\": 12345, \"StopPointName\": \"Example Stop\", " +
                "\"StopAreaNumber\": 12345, \"LocationNorthingCoordinate\": 59.123456789, " +
                "\"LocationEastingCoordinate\": 18.987654321, \"ZoneShortName\": \"A\", " +
                "\"StopAreaTypeCode\": \"BUSSTOP\", \"LastModifiedUtcDateTime\": \"2022-08-23 00:00:00.000\", " +
                "\"ExistsFromDate\": \"2022-08-23 00:00:00.000\"}]}}";
    }

    private StopPoint createStopPoint() {
        StopPoint stopPoint = new StopPoint();
        stopPoint.setStopPointNumber(63305);
        stopPoint.setStopPointName("Skeppsmyra affär");
        stopPoint.setStopAreaNumber(63305);
        stopPoint.setLocationNorthingCoordinate(59.828653999632);
        stopPoint.setLocationEastingCoordinate(19.052825999832);
        stopPoint.setZoneShortName("C");
        stopPoint.setStopAreaTypeCode("BUSTERM");
        stopPoint.setLastModifiedUtcDateTime("2022-08-22 00:00:00.000");
        stopPoint.setExistsFromDate("2022-08-22 00:00:00.000");
        return stopPoint;
    }

    private List<StopPoint> filterStopPoints(Collection<StopPoint> stopPoints, int stopPointNumber) {
        return stopPoints.stream()
                .filter(stopPoint -> stopPoint.getStopPointNumber() == stopPointNumber)
                .collect(Collectors.toList());
    }

    @Test
    void testGetAllBusJourneyPatterns() throws IOException {
        when(okHttpClient.newCall(any(Request.class))).thenAnswer(invocation -> new Response.Builder()
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .request(invocation.getArgument(0))
                .body(ResponseBody.create(mockJourneyPatternsResponse(), null))
                .build());

        Collection<JourneyPatternPointOnLine> result = busLineRepository.getAllBusJourneyPatterns();

        List<JourneyPatternPointOnLine> expectedJourneyPatterns = List.of(createJourneyPatternPointOnLine());

        assertEquals(expectedJourneyPatterns, filterJourneyPatterns(result, 1, 2, "2023-03-07 00:00:00.000"));
        assertNotEquals(expectedJourneyPatterns, filterJourneyPatterns(result, 112, 1, "2012-06-23 00:00:00.000"));
    }

    private String mockJourneyPatternsResponse() {
        return "{\"responseData\": {\"result\": [" +
                "{\"LineNumber\": 1, \"DirectionCode\": 2, \"JourneyPatternPointNumber\": 10065, " +
                "\"LastModifiedUtcDateTime\": \"2023-03-07 00:00:00.000\", " +
                "\"ExistsFromDate\": \"2023-03-07 00:00:00.000\"}," +
                "{\"LineNumber\": 135, \"DirectionCode\": 1, \"JourneyPatternPointNumber\": 13476, " +
                "\"LastModifiedUtcDateTime\": \"2012-06-23 00:00:00.000\", " +
                "\"ExistsFromDate\": \"2012-06-23 00:00:00.000\"}]}}";
    }

    private JourneyPatternPointOnLine createJourneyPatternPointOnLine() {
        JourneyPatternPointOnLine journeyPatternPointOnLine = new JourneyPatternPointOnLine();
        journeyPatternPointOnLine.setLineNumber(1);
        journeyPatternPointOnLine.setDirectionCode(2);
        journeyPatternPointOnLine.setJourneyPatternPointNumber(10065);
        journeyPatternPointOnLine.setLastModifiedUtcDateTime("2023-03-07 00:00:00.000");
        journeyPatternPointOnLine.setExistsFromDate("2023-03-07 00:00:00.000");
        return journeyPatternPointOnLine;
    }

    private List<JourneyPatternPointOnLine> filterJourneyPatterns(Collection<JourneyPatternPointOnLine> patterns, int lineNumber, int directionCode, String lastModifiedUtcDateTime) {
        return patterns.stream()
                .filter(pattern -> pattern.getLineNumber() == lineNumber &&
                        pattern.getDirectionCode() == directionCode &&
                        pattern.getLastModifiedUtcDateTime().equals(lastModifiedUtcDateTime))
                .collect(Collectors.toList());
    }

    @Test
    public void testGetStopPointsByLineNumber() throws IOException {
        Integer topRanking = 4;
        Map<String, Collection<?>> result = busLineRepository.getStopPointsByLineNumber(topRanking);

        assertNotNull(result);

        for (Map.Entry<String, Collection<?>> entry : result.entrySet()) {
            assertNotNull(entry.getKey());
            assertFalse(entry.getKey().isEmpty());

            assertNotNull(entry.getValue());
            assertFalse(entry.getValue().isEmpty());
        }

        assertEquals(topRanking, result.size());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }
}
