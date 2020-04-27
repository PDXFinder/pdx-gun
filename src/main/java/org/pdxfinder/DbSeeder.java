package org.pdxfinder;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.pdxfinder.constants.Location;
import org.pdxfinder.constants.Resource;
import org.pdxfinder.loader.FileHandler;
import org.pdxfinder.query.Create;
import org.pdxfinder.query.Insert;

import java.io.IOException;
import java.util.*;

import static java.lang.System.*;

public class DbSeeder {

    private Insert insert;
    private Create create;
    private FileHandler fileHandler;

    public DbSeeder() {

        this.insert = new Insert();
        this.create = new Create();
        this.fileHandler = new FileHandler();
    }


    public void testLoad(){

        // Download Files
        this.fileHandler.downloadFile(Location.CIVIC_DB_URL_ONLINE, Location.CIVIC_DB_FILE);
        Set<String> data = this.fileHandler.loadJsonList(Location.CIVIC_DB_FILE, "result");

        out.println(data);
    }


    public void loadData()throws IOException {

        // Download Files
       // this.fileHandler.downloadFile(Location.ONCO_MX_URL_ONLINE, Location.ONCO_MX_FILE);

        // Initialize the postgres query file:
        fileHandler.delete(Location.DESTINATION.get());

        DbTable resourceTable = create.resourceTable();
        DbTable geneTable = create.getGeneTable();
        DbTable resourceUrlTable = create.resourceUrlTable(geneTable, resourceTable);

        this.insert.resourceTable(resourceTable);

        Set<String> genes = this.fileHandler.loadGenes(Location.ONCO_MX_FILE.get());
        Map<Integer, List<Object>> geneMap = this.insert.geneTable(geneTable, genes);

        this.insert.resourceUrlTable(resourceUrlTable, geneMap, Resource.ONCOMX);
    }


}



