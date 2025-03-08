package org.example;

public class AppConfigSingleton {
    private static AppConfigSingleton instance;
    private String databaseUrl;
    private String apiKey;

    private AppConfigSingleton() {
        System.out.println("AppConfigSingleton init");
    }
    public static synchronized AppConfigSingleton getInstance() {
        if (instance == null) {
            instance = new AppConfigSingleton();
        }
        return instance;
    }
    public void setDatabaseConnection(String databaseUrl, String apiKey) {
        this.databaseUrl = databaseUrl;
        this.apiKey = apiKey;
    }
    public String getDatabaseUrl() {
        return databaseUrl;
    }
    public String getApiKey() {
        return apiKey;
    }
}
