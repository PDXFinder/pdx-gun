package org.pdxfinder.constants;

public enum Resource {

    CIVIC("Civic"),
    ONCOMX("OncoMX"),
    COSMIC("Cosmic"),
    COSMIC_HALLMARK("CosmicHallMark"),
    CRAVAT("Cravat");

    private String value;

    private Resource(String val) {
        value = val;
    }

    public String get() {
        return value;
    }

}
