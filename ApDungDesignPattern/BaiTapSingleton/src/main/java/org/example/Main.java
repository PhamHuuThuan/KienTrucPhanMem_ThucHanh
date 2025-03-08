package org.example;

public class Main {
    public static void main(String[] args) {
        AppConfigSingleton config = AppConfigSingleton.getInstance();
        config.setDatabaseConnection("jdbc:mysql://localhost:3306/mydb", "myApiKey");

        System.out.println("Database URL: " + config.getDatabaseUrl());
        System.out.println("API Key: " + config.getApiKey());
        System.out.println("Hash Code: " + config.hashCode() );

        AppConfigSingleton config2 = AppConfigSingleton.getInstance();
        System.out.println("Database URL: " + config2.getDatabaseUrl());
        System.out.println("API Key: " + config2.getApiKey());
        System.out.println("Hash Code: " + config2.hashCode() );
    }
}
