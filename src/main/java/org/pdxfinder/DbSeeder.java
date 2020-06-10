package org.pdxfinder;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.pdxfinder.constants.Location;
import org.pdxfinder.constants.Resource;
import org.pdxfinder.constants.Table;
import org.pdxfinder.loader.FileHandler;
import org.pdxfinder.query.Create;
import org.pdxfinder.query.Insert;

import java.io.IOException;
import java.util.*;

public class DbSeeder {

    private Insert insert;
    private Create create;
    private FileHandler fileHandler;

    DbSeeder() {
        this.insert = new Insert();
        this.create = new Create();
        this.fileHandler = new FileHandler();
    }


    void loadData()throws IOException {

        // Download Files
        this.fileHandler.downloadFile(Location.CIVIC_GENE_URL_ONLINE, Location.CIVIC_GENE_URL_LOCAL);
        this.fileHandler.downloadFile(Location.CIVIC_VARIANT_URL_ONLINE, Location.CIVIC_VARIANTS_URL_LOCAL);
        this.fileHandler.downloadFile(Location.ONCO_MX_URL_ONLINE, Location.ONCO_MX_URL_LOCAL);

        // Extract Gene and Variants Data
        Set<String> civicDBGenes = this.fileHandler.extractCivicData(Location.CIVIC_GENE_URL_LOCAL);
        Map<String, String> civicVariantData = this.fileHandler.extractCivicVariantData(Location.CIVIC_VARIANTS_URL_LOCAL);
        Set<String> oncoMxGenes = this.fileHandler.extractOncomxGenes(Location.ONCO_MX_URL_LOCAL.get());


        // Initialize the postgres query file:
        fileHandler.delete(Location.DESTINATION.get());

        // Create table Queries
        DbTable resourceTable = create.resourceTable();
        DbTable geneTable = create.dataTable(resourceTable, Table.GENE);
        DbTable variantTable = create.dataTable(resourceTable, Table.VARIANT);

        // Insert into table Queries
        this.insert.resourceTable(resourceTable);

        Map<Integer, List<Object>> query = insert.geneTableQuery(civicDBGenes, Resource.CIVIC, Location.CIVIC_GENE_URL_PREFIX, 0);
        insert.generateQuery(query, geneTable);

        query = this.insert.geneTableQuery(oncoMxGenes, Resource.ONCOMX, Location.ONCO_MX_URL_PREFIX, civicDBGenes.size());
        insert.generateQuery(query, geneTable);

        query = insert.variantTableQuery(civicVariantData, Resource.CIVIC, Location.CIVIC_VARIANTS_URL_PREFIX, 0);
        insert.generateQuery(query, variantTable);

    }


}



