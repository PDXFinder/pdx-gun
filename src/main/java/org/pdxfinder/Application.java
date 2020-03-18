package org.pdxfinder;

public class Application {

    private static DbSeeder dbSeeder = new DbSeeder();

    public static void main(String[] args) throws Exception {

        dbSeeder.initPostgres();
    }

}