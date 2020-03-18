package org.pdxfinder.constants;

public enum Location {

    DESTINATION(System.getProperty("user.home")+"/Docker/dockerise-postgres-data/init.sql"),
    ONCO_MX_FILE(System.getProperty("user.home")+"/Documents/data-bridge/human_cancer_mutation.csv");

    private String value;

    private Location(String val) {
        value = val;
    }

    public String get() {
        return value;
    }
}
