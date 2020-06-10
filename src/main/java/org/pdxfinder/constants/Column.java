package org.pdxfinder.constants;

public enum Column {

    id,
    name,
    variant_id,
    gene,
    item_id,
    gene_id,
    resource_id,
    url;

    public String get() {
        return name();
    }
}
