package org.pdxfinder;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.pdxfinder.constants.Location;
import org.pdxfinder.constants.Resource;
import org.pdxfinder.loader.FileHandler;
import org.pdxfinder.query.Create;
import org.pdxfinder.query.Insert;

import java.io.IOException;
import java.util.*;

public class DbSeeder {

    private Insert insert;
    private Create create;
    private FileHandler fileHandler;

    public DbSeeder() {

        this.insert = new Insert();
        this.create = new Create();
        this.fileHandler = new FileHandler();
    }


    public void loadData()throws IOException {

        // Download Files
        this.fileHandler.downloadFile(Location.CIVIC_DB_URL_ONLINE, Location.CIVIC_DB_URL_LOCAL);
        this.fileHandler.downloadFile(Location.ONCO_MX_URL_ONLINE, Location.ONCO_MX_URL_LOCAL);

        // Extract Gene Data
        Set<String> civicDBGenes = this.fileHandler.extractCivicDBGenes(Location.CIVIC_DB_URL_LOCAL, "result");
        Set<String> oncomxGenes = this.fileHandler.extractOncomxGenes(Location.ONCO_MX_URL_LOCAL.get());

        // Initialize the postgres query file:
        fileHandler.delete(Location.DESTINATION.get());

        // Create table Queries
        DbTable resourceTable = create.resourceTable();
        DbTable geneTable = create.geneTable(resourceTable);

        // Insert into table Queries
        this.insert.resourceTable(resourceTable);
        this.insert.geneTable(geneTable, civicDBGenes, Resource.CIVIC, Location.CIVIC_DB_URL_PREFIX, 0);
        this.insert.geneTable(geneTable, oncomxGenes, Resource.ONCOMX, Location.ONCO_MX_URL_PREFIX, civicDBGenes.size());

    }


}



