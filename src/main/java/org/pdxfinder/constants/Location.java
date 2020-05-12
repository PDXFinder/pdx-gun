package org.pdxfinder.constants;

public enum Location {

    DESTINATION(System.getProperty("user.dir")+"/init.sql"),

    CIVIC_DB_URL_ONLINE("https://civicdb.org/api/datatables/genes?count=1000000"),
    CIVIC_DB_URL_LOCAL(System.getProperty("user.dir")+"/civic.json"),
    CIVIC_DB_URL_PREFIX("https://civicdb.org/links/entrez_name/"),

    ONCO_MX_URL_ONLINE("https://data.oncomx.org/ln2wwwdata/reviewed/human_cancer_mutation.csv"),
    ONCO_MX_URL_LOCAL(System.getProperty("user.dir")+"/oncoMX.csv"),
    ONCO_MX_URL_PREFIX("https://oncomx.org/searchview/?gene=");


    private String value;

    private Location(String val) {
        value = val;
    }

    public String get() {
        return value;
    }
}
