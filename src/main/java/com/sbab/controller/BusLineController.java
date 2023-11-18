package com.sbab.controller;

import com.sbab.service.BusLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(path = "/buslines", produces = "application/json")
public class BusLineController {

    @Autowired
    private BusLineService busLineService;

    @GetMapping(path = "/all")
    public Map<String, Collection<?>> getAllBusLines(
            @RequestParam(name = "topBusLines", required = false) Integer top10RankBusLines
    ) throws IOException {
        return busLineService.getAllBusLines(top10RankBusLines);
    }
}
