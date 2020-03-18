package org.pdxfinder.query;

import com.healthmarketscience.sqlbuilder.CreateTableQuery;
import com.healthmarketscience.sqlbuilder.InsertQuery;
import com.healthmarketscience.sqlbuilder.dbspec.basic.*;
import org.pdxfinder.constants.Column;
import org.pdxfinder.constants.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SqlFacade {

    private Integer ignore = null;

    private static  final String QUERY_END = ";\n";

    public DbTable addTable(Table tableName){

        // create default schema
        DbSpec spec = new DbSpec();
        DbSchema schema = spec.addDefaultSchema();

        // add table with basic data
        return schema.addTable(tableName.get());
    }


    public DbColumn addIntegerColumn(DbTable dbTable, Column column){
        String typeName = "INTEGER NOT NULL";
        return dbTable.addColumn(column.get(),typeName, ignore);
    }

    public DbColumn addPrimaryKey(DbTable dbTable, Column column){
        String typeName = "INTEGER PRIMARY KEY";
        return dbTable.addColumn(column.get(), typeName, ignore);
    }

    public DbColumn addVarchar(DbTable dbTable, Column column){
        String typeName = "VARCHAR";
        int typeLength = 255;
        return dbTable.addColumn(column.get(), typeName, typeLength);
    }

    public DbColumn addForeignKeyColumn(DbTable dbTable, Column column, String refDetails){
        String typeName = "INTEGER REFERENCES "+refDetails+" ON DELETE RESTRICT";

        return dbTable.addColumn(column.get(),typeName, ignore);
    }


    public String insertQueries(DbTable table, List<DbColumn> dbColumns, List values){

        DbColumn[] dbColumnArray = dbColumns.toArray(new DbColumn[0]);
        Object[] valuesArray = values.toArray(new Object[0]);

        String insertQuery = new InsertQuery(table)
                .addColumns(dbColumnArray, valuesArray)
                .validate().toString();

        return String.format("%s%s", insertQuery, QUERY_END);

    }

    public String insertQuery(DbTable table, Map<Integer, List<Object>> rowDataMap){

        StringBuilder insertQuery = new StringBuilder();

        rowDataMap.forEach((key, value) -> {

            List<Object> rowDataList = new ArrayList<>(value);
            rowDataList.add(0,key);

            insertQuery.append(insertQueries(table, table.getColumns(), rowDataList));

        });

        System.out.println("Insert Query generated for table: "+ table.getTableNameSQL());

        return insertQuery.toString();
    }


    public String createTableQuery(DbTable table){

        String createQuery = new CreateTableQuery(table, true)
                .validate().toString();

        String cleanForPostGres = createQuery.replace("CONSTRAINT constraint_cfk", "");

        System.out.println("Create Query generated for table: "+ table.getTableNameSQL());

        return String.format("%s%s", cleanForPostGres, QUERY_END);
    }

    public void addFKConstraint(DbTable table, DbTable refTable, DbColumn constrainedColumn){

        DbColumn[] constrainedColumns = {constrainedColumn};
        DbColumn[] refColumns = {refTable.getColumns().get(0)};

        table.foreignKey("constraint_cfk", constrainedColumns, refTable, refColumns);

    }


}

// https://data.oncomx.org/ln2wwwdata/reviewed/human_cancer_mutation.csv