package com.sbab.repository;

import com.sbab.model.datamodel.JourneyPatternPointOnLine;
import com.sbab.model.datamodel.Line;
import com.sbab.model.datamodel.StopPoint;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

@Repository
public interface BusLineRepository{

    Collection<Line> getAllLines() throws IOException;
    Collection<StopPoint> getAllStopPoints() throws IOException;
    Collection<JourneyPatternPointOnLine> getAllBusJourneyPatterns() throws IOException;
    Map<String, Collection<?>> getStopPointsByLineNumber(Integer topRanks) throws IOException;
}

