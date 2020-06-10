package org.pdxfinder.constants;

public enum Location {

    DESTINATION(System.getProperty("user.dir")+"/init.sql"),

    CIVIC_GENE_URL_ONLINE("https://civicdb.org/api/datatables/genes?count=1000000"),
    CIVIC_GENE_URL_LOCAL(System.getProperty("user.dir")+"/civic.json"),
    CIVIC_GENE_URL_PREFIX("https://civicdb.org/links/entrez_name/"),

    CIVIC_VARIANT_URL_ONLINE("https://civicdb.org/api/variants?count=10000"),
    CIVIC_VARIANTS_URL_LOCAL(System.getProperty("user.dir")+"/civic-variants.json"),
    CIVIC_VARIANTS_URL_PREFIX("https://civicdb.org/links?idtype=variant&id="),

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
