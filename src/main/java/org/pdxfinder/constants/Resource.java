package org.pdxfinder.constants;

public enum Resource {

    COSMIC("Cosmic Database"),
    COSMIC_HALLMARK("Cosmic HallMark Database"),
    CRAVAT("Open Cravat Database"),
    CIVIC("Civic Database"),
    ONCOMX("OncoMX Database");

    private String value;

    private Resource(String val) {
        value = val;
    }

    public String get() {
        return value;
    }

}
