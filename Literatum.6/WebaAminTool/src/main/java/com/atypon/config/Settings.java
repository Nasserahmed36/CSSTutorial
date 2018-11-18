package com.atypon.config;


import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * for internal usage
 *
 * @author Nasser Alkhateeb
 * @version 1.0, 2018/08/14
 */
public class Settings {

    // Suppresses default constructor, ensuring non-instantiability.
    private Settings() {
    }

    private static ClassLoader classLoader = Settings.class.getClassLoader();
    private static File file = new File(classLoader.getResource("config/settings.properties").getFile());
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileReader(file));
        } catch (Exception e) {
            throw new Error("Can not load settings file: " + e.getMessage());
        }
    }

    // db
    public static String dbDriverClassName() {
        return properties.getProperty("DB_DRIVER_CLASS");
    }

    public static String dbUrl() {
        return properties.getProperty("DB_URL");
    }

    public static String dbUsername() {
        return properties.getProperty("DB_USERNAME");
    }

    public static String dbPassword() {
        return properties.getProperty("DB_PASSWORD");
    }

    public static int plMaxActive() {
        return Integer.parseInt(properties.getProperty("PL_MAX_ACTIVE"));
    }

    public static int maxCacheSize() {
        return Integer.parseInt(properties.getProperty("MAX_CASH_SIZE"));
    }

}
