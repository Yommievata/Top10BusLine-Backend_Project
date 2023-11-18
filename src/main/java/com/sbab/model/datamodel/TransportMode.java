/*package com.sbab.model.datamodel;

public enum TransportMode {
    BUS("BUSTERM");

    private final String stopAreaTypeCode;

    TransportMode(String stopAreaTypeCode) {
        this.stopAreaTypeCode = stopAreaTypeCode;
    }

    public String getStopAreaTypeCode() {
        return stopAreaTypeCode;
    }

    public static TransportMode getByStopAreaTypeCode(String stopAreaTypeCode) {
        for (TransportMode mode : values()) {
            if (mode.stopAreaTypeCode.equals(stopAreaTypeCode)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("No constant with stopAreaTypeCode " + stopAreaTypeCode + " found");
    }
}*/
