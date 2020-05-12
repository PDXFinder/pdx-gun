package org.pdxfinder.query;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbColumn;
import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.pdxfinder.constants.Column;
import org.pdxfinder.constants.Location;
import org.pdxfinder.constants.Table;
import org.pdxfinder.loader.FileHandler;

public class Create {

    private SqlFacade sqlFacade;
    private FileHandler fileHandler;


    public Create() {

        this.sqlFacade = new SqlFacade();
        this.fileHandler = new FileHandler();
    }


    public DbTable resourceTable() {

        DbTable resourceTable = sqlFacade.addTable(Table.RESOURCE);

        // Add Columns to resource tables
        sqlFacade.addPrimaryKey(resourceTable, Column.id);
        sqlFacade.addVarchar(resourceTable, Column.name);

        // Create Resource table Queries
        String createTable = sqlFacade.createTableQuery(resourceTable);

        // write query to File
        fileHandler.write(createTable, Location.DESTINATION.get() , true );

        return resourceTable;
    }


    public DbTable geneTable(DbTable resourceTable) {

        DbTable geneTable = sqlFacade.addTable(Table.GENE);

        // Add Columns to Gene table
        sqlFacade.addPrimaryKey(geneTable, Column.id);
        sqlFacade.addVarchar(geneTable, Column.name);
        sqlFacade.addVarchar(geneTable, Column.url);
        DbColumn resourceId = sqlFacade.addIntegerColumn(geneTable, Column.resource_id);

        // Add Foreign key Constraints
        sqlFacade.addFKConstraint(geneTable, resourceTable, resourceId);

        // Create Gene table Queries
        String createTable = sqlFacade.createTableQuery(geneTable);

        // write to file
        fileHandler.write(createTable, Location.DESTINATION.get() , true );

        return  geneTable;
    }



    public DbTable resourceUrlTable(DbTable geneTable, DbTable resourceTable) {

        DbTable resourceUrlTable = sqlFacade.addTable(Table.RESOURCE_URL);

        // Add Columns to resource_url table
        sqlFacade.addPrimaryKey(resourceUrlTable, Column.id);
        DbColumn geneId = sqlFacade.addIntegerColumn(resourceUrlTable, Column.gene_id);
        DbColumn resourceId = sqlFacade.addIntegerColumn(resourceUrlTable, Column.resource_id);
        sqlFacade.addVarchar(resourceUrlTable, Column.url);

        // Add Foreign key Constraints
        sqlFacade.addFKConstraint(resourceUrlTable, geneTable, geneId);
        sqlFacade.addFKConstraint(resourceUrlTable, resourceTable, resourceId);

        // Generate ResourceUrl table Queries
        String createTable = sqlFacade.createTableQuery(resourceUrlTable);

        // write to file
        fileHandler.write(createTable, Location.DESTINATION.get() , true );

        return resourceUrlTable;
    }
}
