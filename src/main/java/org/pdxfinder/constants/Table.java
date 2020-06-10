package org.pdxfinder.constants;

public enum Table {

    RESOURCE("resource"),
    GENE("gene"),
    VARIANT("variant"),
    RESOURCE_URL("resource_url");

    private String value;

    private Table(String val) {
        value = val;
    }

    public String get() {
        return value;
    }

}
