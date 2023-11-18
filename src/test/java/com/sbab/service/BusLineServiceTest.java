package com.sbab.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.sbab.repository.BusLineRepository;

@SpringJUnitConfig
@ContextConfiguration(classes = BusLineServiceTest.TestConfig.class)
public class BusLineServiceTest {

    @Mock
    private BusLineRepository busLineRepository;
    @InjectMocks
    private BusLineService busLineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    void testGetAllBusLinesWithTopBusLines() throws IOException {
        List<String> stops = Arrays.asList("StopA", "StopB", "StopC");
        Map<String, List<?>> stopPointsMap = Collections.singletonMap("StopPoints", stops);
        doAnswer(invocation -> {
            int topBusLines = invocation.getArgument(0);
            if (topBusLines > 0 && topBusLines <= stops.size()) {
                return Collections.singletonMap("StopPoints", stops.subList(0, topBusLines));
            } else {
                return stopPointsMap;
            }
        }).when(busLineRepository).getStopPointsByLineNumber(anyInt());

        Map<String, Collection<?>> result = busLineService.getAllBusLines(1);

        verify(busLineRepository, times(1)).getStopPointsByLineNumber(1);

        List<?> expectedStopsForTop1 = Collections.singletonList("StopA");
        Map<String, Collection<?>> expectedMapForTop1 = Collections.singletonMap("StopPoints", expectedStopsForTop1);

        assertEquals(expectedMapForTop1, result);

        result = busLineService.getAllBusLines(2);

        verify(busLineRepository, times(1)).getStopPointsByLineNumber(2);

        List<?> expectedStopsForTop2 = Arrays.asList("StopA", "StopB");
        Map<String, Collection<?>> expectedMapForTop2 = Collections.singletonMap("StopPoints", expectedStopsForTop2);

        assertEquals(expectedMapForTop2, result);
    }

    @Test
    void testGetAllBusLinesWithoutTopBusLines() throws IOException {
        Map<String, List<?>> linesMap = Collections.singletonMap("Lines", Arrays.asList("Line1", "Line2", "Line3", "Line4"));

        doReturn(linesMap.get("Lines")).when(busLineRepository).getAllLines();

        Map<String, Collection<?>> result = busLineService.getAllBusLines(null);

        verify(busLineRepository, times(1)).getAllLines();

        List<?> expectedLines = Arrays.asList("Line1", "Line2", "Line3", "Line4");
        Map<String, Collection<?>> expectedMap = Collections.singletonMap("Lines", expectedLines);

        assertEquals(expectedMap, result);
    }

    @Configuration
    static class TestConfig {
        @Bean
        public CacheManager cacheManager() {
            return new NoOpCacheManager();
        }
    }
}
