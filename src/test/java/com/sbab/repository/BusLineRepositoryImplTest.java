package com.sbab.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbab.model.ModelBase;
import com.sbab.model.ResponseData;
import com.sbab.model.datamodel.JourneyPatternPointOnLine;
import com.sbab.model.datamodel.Line;
import com.sbab.model.datamodel.StopPoint;
import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = BusLineRepositoryImplTest.TestConfig.class)
/*@TestPropertySource(properties = {

               /* "api.key=test-key",
                "api.url=https://api.test.com/test?key=${api.key}",
                "line.url=${api.url}&model=line&DefaultTransportModeCode=BUS",
                "stop.point.url=${api.url}&model=stop",
                "journey.pattern.url=${api.url}&model=jour&DefaultTransportModeCode=BUS"*/

/*
        "api.key=23eb6d33db5543ac867804ad31d9b65f",
        "api.url=https://api.sl.se/api2/linedata.json?key=${api.key}",
        "line.url=${api.url}&model=line&DefaultTransportModeCode=BUS",
        "stop.point.url=${api.url}&model=stop",
        "journey.pattern.url=${api.url}&model=jour&DefaultTransportModeCode=BUS"*/

//})*/

class BusLineRepositoryImplTest {

    @Mock
    private OkHttpClient okHttpClient;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    @Mock
    private Call call;

    @InjectMocks
    private BusLineRepositoryImpl busLineRepository;

    @Autowired
    private Environment environment;

    @Spy
    private ModelBase modelBase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLines() throws IOException {
        // Arrange
        String url = environment.getProperty("line.url");
        TypeReference<ResponseData<Line>> typeReference = new TypeReference<>() {};
        ResponseData<Line> responseData = new ResponseData<>();
        Line line = new Line();
        line.setLineNumber(1);
        line.setLineDesignation("LineA");
        line.setDefaultTransportMode("blåbuss");
        line.setDefaultTransportModeCode("BUS");
        line.setLastModifiedUtcDateTime("2021-03-01T00:00:00");
        line.setExistsFromDate("2021-03-01T00:00:00");

        responseData.setResult(Collections.singletonList(line));

        assert url != null;

        // Mock the Call interface
        Call call = mock(Call.class);

        // Create a Response object
        Response response = new Response.Builder()
                .request(new Request.Builder().url(url).build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("", MediaType.parse("application/json")))
                .build();

        // Configure the mockito behavior
        doReturn(call).when(okHttpClient).newCall(any());
        doReturn(response).when(call).execute();
        doReturn(responseData).when(objectMapper).readValue(anyString(), any(TypeReference.class));

        // Act
        Collection<Line> result = busLineRepository.getAllLines();

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        verify(call, times(1)).execute();  // Verify that execute() is called on the mocked Call
        verify(objectMapper, times(1)).readValue(anyString(), any(TypeReference.class));
        assertEquals(1, result.size());
    }


   /* @Test
    void testGetAllStopPoints() throws IOException {
        // Arrange
        String url = environment.getProperty("stop.point.url");
        TypeReference<ResponseData<StopPoint>> typeReference = new TypeReference<>() {};

        // Create a mock ResponseData object with non-null values
        StopPoint stopPoint = new StopPoint();
        // Set properties of stopPoint as needed
        stopPoint.setStopPointNumber(1);
        stopPoint.setStopPointName("StopA");

        ResponseData<StopPoint> responseData = new ResponseData<>();
        responseData.setResult(Collections.singletonList(stopPoint));

        // Create a mock Response object
        assert url != null;
        Response response = new Response.Builder()
                .request(new Request.Builder().url(url).build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(objectMapper.writeValueAsString(responseData), MediaType.parse("application/json")))
                .build();

        // Use doReturn to specify the behavior of newCall
        doReturn(mockCall(response)).when(okHttpClient).newCall(any());

        // Act
        Collection<StopPoint> result = busLineRepository.getAllStopPoints();

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        assertEquals(1, result.size());
    }
    */

    // Helper method to create a mock Call object
    private Call mockCall(Response response) throws IOException {
        Call call = mock(Call.class);
        doReturn(response).when(call).execute();
        return call;
    }
    @Test
    void testGetAllStopPoints() throws IOException {
        // Arrange
        String url = environment.getProperty("stop.point.url");
        TypeReference<ResponseData<StopPoint>> typeReference = new TypeReference<>() {};

        // Create a mock ResponseData object with non-null values
        StopPoint stopPoint = new StopPoint();
        // Set properties of stopPoint as needed
        stopPoint.setStopPointNumber(1);
        stopPoint.setStopPointName("StopA");

        ResponseData<StopPoint> responseData = new ResponseData<>();
        responseData.setResult(Collections.singletonList(stopPoint));

        // Create a mock Response object
        assert url != null;
        Response response = new Response.Builder()
                .request(new Request.Builder().url(url).build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create(objectMapper.writeValueAsString(responseData), MediaType.parse("application/json")))
                .build();

        // Use doReturn to specify the behavior of newCall
        doReturn(mockCall(response)).when(okHttpClient).newCall(any());

        // Act
        Collection<StopPoint> result = busLineRepository.getAllStopPoints();

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        assertEquals(1, result.size());
    }



    @Test
    void testGetAllBusJourneyPatterns() throws IOException {
        // Arrange
        String url = environment.getProperty("journey.pattern.url");
        TypeReference<ResponseData<JourneyPatternPointOnLine>> typeReference = new TypeReference<>() {};
        ResponseData<JourneyPatternPointOnLine> responseData = new ResponseData<>();
        responseData.setResult(Collections.singletonList(new JourneyPatternPointOnLine()));

        // Use doReturn to specify the behavior of newCall
        doReturn(call).when(okHttpClient).newCall(any());

        // Use doReturn to specify the behavior of execute on the Call mock
        doReturn(response).when(call).execute();
        doReturn(responseData).when(objectMapper).readValue(anyString(), any(TypeReference.class));


        // Act
        Collection<JourneyPatternPointOnLine> result = busLineRepository.getAllBusJourneyPatterns();

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        verify(call, times(1)).execute(); // Verify execute on the Call mock
        verify(objectMapper, times(1)).readValue(anyString(), any(TypeReference.class));
        assertEquals(1, result.size());
    }


    @Test
    void testGetAllStopPointsGroupedByLineNumber() throws IOException {
        // Arrange
        String url = "test-journey-url";
        TypeReference<ResponseData<JourneyPatternPointOnLine>> typeReference = new TypeReference<>() {
        };
        ResponseData<JourneyPatternPointOnLine> responseData = new ResponseData<>();
        responseData.setResult(Collections.singletonList(new JourneyPatternPointOnLine()));
        Response response = new Response.Builder()
                .request(new Request.Builder().url(url).build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("", MediaType.parse("application/json")))
                .build();
        doReturn(response).when(okHttpClient).newCall(any()).execute();
        doReturn(responseData).when(objectMapper).readValue(anyString(), any(TypeReference.class));

        // Act
        Map<String, Collection<?>> result = busLineRepository.getStopPointsByLineNumber(1);

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        verify(objectMapper, times(1)).readValue(anyString(), any(TypeReference.class));
        assertEquals(1, result.size());
    }

    @Test
    void testCollectData() throws IOException {
        // Arrange
        String url = "test-url";
        TypeReference<ModelBase<Line>> typeReference = new TypeReference<>() {
        };
        ModelBase<Line> modelBase = new ModelBase<>();
        modelBase.setResponseData(new ResponseData<>());
        modelBase.getResponseData().setResult(Collections.singletonList(new Line()));
        Response response = new Response.Builder()
                .request(new Request.Builder().url(url).build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(ResponseBody.create("", MediaType.parse("application/json")))
                .build();
        doReturn(response).when(okHttpClient).newCall(any()).execute();
        doReturn(modelBase).when(objectMapper).readValue(anyString(), any(TypeReference.class));

        // Act
        Collection<Line> result = busLineRepository.collectData(url, typeReference);

        // Assert
        verify(okHttpClient, times(1)).newCall(any());
        verify(objectMapper, times(1)).readValue(anyString(), any(TypeReference.class));
        assertEquals(1, result.size());
    }

    @Configuration
    static class TestConfig {
        @Bean
        public ObjectMapper testObjectMapper() {
            return new ObjectMapper();
        }
    }
}
