package crawlmanager.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES = new Properties();
    public static final String CONFIG_FILE = "crawl.properties";
    public static final String HOME = System.getProperty("user.home");
    public static final String SCRIPT_DIR = "SCRIPTS";
    public static final String OUTPUT_DIR = "OUTPUT";

    static {
        try {
            File file = new File(HOME + "/" + CONFIG_FILE);
            if (file.exists()) {
                System.out.println("File exists on root");
                PROPERTIES.load(new FileInputStream(file));
            } else {
                System.out.println("File does not exists on root");
                InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
                PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException("properties file now found, can not connect to db");
        }
    }

    public static String get(String key) {
        return String.valueOf(PROPERTIES.get(key));
    }

    public static void testScriptPath() {
        if (!new File(HOME + "/" + SCRIPT_DIR).exists()) {
            throw new IllegalStateException("scripts not found at " + HOME + "/" + SCRIPT_DIR);
        }
    }
}