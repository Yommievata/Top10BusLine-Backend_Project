package com.sbab.service;

import com.sbab.repository.BusLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Service
public class BusLineService {

    @Autowired
    private BusLineRepository busLineRepository;

    @Cacheable(value = "lines", key = "#topBusLines")
    public Map<String, Collection<?>> getAllBusLines(@RequestParam(required = false) Integer topBusLines) throws IOException {
        if (topBusLines != null && topBusLines > 0) {
            return busLineRepository.getStopPointsByLineNumber(topBusLines);
        } else {
            return Collections.singletonMap("Lines", busLineRepository.getAllLines());
        }
    }

}