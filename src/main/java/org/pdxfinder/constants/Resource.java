package org.pdxfinder.constants;

public enum Resource {

    CIVIC("Civic Database"),
    ONCOMX("OncoMX Database"),
    COSMIC("Cosmic Database"),
    COSMIC_HALLMARK("Cosmic HallMark Database"),
    CRAVAT("Open Cravat Database");

    private String value;

    private Resource(String val) {
        value = val;
    }

    public String get() {
        return value;
    }

}
