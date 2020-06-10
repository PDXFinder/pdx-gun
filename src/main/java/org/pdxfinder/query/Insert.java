package org.pdxfinder.query;

import com.healthmarketscience.sqlbuilder.dbspec.basic.DbTable;
import org.pdxfinder.constants.Location;
import org.pdxfinder.constants.Resource;
import org.pdxfinder.loader.FileHandler;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Insert {


    private SqlFacade sqlFacade;
    private Map<Integer, List<Object>> dataMap;
    private FileHandler fileHandler;
    
    public Insert() {

        this.sqlFacade = new SqlFacade();
        this.fileHandler = new FileHandler();
    }


    public void resourceTable(DbTable resourceTable) {

        dataMap = new LinkedHashMap<>();

        // Resource table insert Queries
        int counter = 0;
        for (Resource resource : Resource.values()) {
            dataMap.put(++counter, Collections.singletonList(resource.get()));
        }

        String query = sqlFacade.insertQuery(resourceTable, dataMap);
        fileHandler.write(query, Location.DESTINATION.get() , true );

    }


    public void generateQuery(Map<Integer, List<Object>> dataMap, DbTable table){
        String query = sqlFacade.insertQuery(table, dataMap);
        fileHandler.write(query, Location.DESTINATION.get() , true );
    }

    public Map<Integer, List<Object>>  geneTableQuery(Set<String> dataSet,
                                                      Resource resource,
                                                      Location resourceUrlPrefix,
                                                      int startIndex){
        dataMap = new LinkedHashMap<>();
        // Gene table insert Queries
        AtomicInteger index = new AtomicInteger(startIndex);
        dataSet.forEach(data -> {
            List<Object> rowData = Arrays.asList(
                    data,
                    String.format("%s%s", resourceUrlPrefix.get(), data),
                    resource.ordinal()+1
            );
            dataMap.put(index.incrementAndGet(), rowData);
        });
        return dataMap;
    }

    public Map<Integer, List<Object>>  variantTableQuery(Map<String, String> dataSet,
                                                         Resource resource,
                                                         Location resourceUrlPrefix,
                                                         int startIndex){
        dataMap = new LinkedHashMap<>();
        AtomicInteger index = new AtomicInteger(startIndex);
        dataSet.forEach((variantId, variantName) -> {
            List<Object> rowData = Arrays.asList(
                    variantName,
                    String.format("%s%s", resourceUrlPrefix.get(), variantId),
                    resource.ordinal()+1
            );
            dataMap.put(index.incrementAndGet(), rowData);
        });
        return dataMap;
    }

    public void resourceUrlTable(DbTable resourceUrlTable, Map<Integer, List<Object>> geneMap, Resource resource) {

        dataMap = new LinkedHashMap<>();
        AtomicInteger index = new AtomicInteger(0);
        geneMap.forEach((key, value) -> {
                            List<Object> rowData = new ArrayList<>();
                            rowData.add(key);
                            rowData.add(resource.ordinal() + 1);
                            rowData.add("https://oncomx.org/searchview/?gene=" + value.get(0));
                            dataMap.put(index.incrementAndGet(), rowData);
                        }
        );

        String query = sqlFacade.insertQuery(resourceUrlTable, dataMap);
        fileHandler.write(query, Location.DESTINATION.get(), true);
    }

}
