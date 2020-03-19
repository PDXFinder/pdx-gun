package org.pdxfinder.loader;

import org.pdxfinder.constants.Location;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class FileHandler {

    private Logger log = Logger.getLogger(FileHandler.class.getName());

    // Create a new trust manager that trust all certificates
    private TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };

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


    public void downloadFile(Location urlStr, Location destination) {

        // Delete file if exist
        this.delete(destination.get());

        try {
            // Activate the new trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(urlStr.get());
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

                String inputLine;
                int progress = 1;
                while ((inputLine = in.readLine()) != null) {

                    this.write(inputLine+"\n", destination.get(), true);
                    if (progress%100000 == 0) this.getSize(destination, progress);
                    progress++;
                }
            }

        } catch (Exception e) {
            log.warning(String.format("Unable to read from URL %s %s", urlStr, e));
        }

    }


    public void write(String data, String destination, Boolean shouldAppend){

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


    private void getSize(Location fileLocation, int count){

        File file = new File(fileLocation.get());

        double size = file.length() / (1024 * 1024);

        String anim= "|/-\\";
        System.out.print( String.format("\r %s %s MegaByte %s file Downloaded ", anim.charAt(count % anim.length()),
                                        size, fileLocation) );
    }


}
