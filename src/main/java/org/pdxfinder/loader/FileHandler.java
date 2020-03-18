package org.pdxfinder.loader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class FileHandler {

    private Logger log = Logger.getLogger(FileHandler.class.getName());

    public Set<String> loadGenes(String csvSource) throws IOException {

        Set<String> geneSet = new HashSet<>();

        try (BufferedReader in = new BufferedReader(new FileReader(csvSource))) {
            String line;
            while ((line = in.readLine()) != null) {
                String geneid = line.split(",")[0].replaceAll("\"", "");
                geneSet.add(geneid);
            }
        }

        return geneSet;
    }



    public void write(String data, String destination, Boolean shouldAppend){

        // Write to the file using BufferedReader and FileWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination, shouldAppend))){
            writer.append(data);
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }


    public Boolean delete(String localDirectory) {

        boolean report = false;
        try {
            Path path = Paths.get(localDirectory);
            Files.deleteIfExists(path);

            report = true;
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
        return report;
    }

}
