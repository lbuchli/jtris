package ch.vfl.jtris.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final String FILEPATH = "config/settings.properties";

    private static Settings instance;

    private File settingsFile;
    private Properties properties;

    private Settings() throws IOException {
        settingsFile = new File(ClassLoader.getSystemClassLoader().getResource(FILEPATH).getFile());
        properties = new Properties();

        FileReader reader = new FileReader(settingsFile);
        properties.load(reader);
        reader.close();
    }

    public static Settings getInstance() {
        if (instance == null) {
            try {
                instance = new Settings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void write() throws IOException {
        FileWriter writer = new FileWriter(settingsFile);
        properties.store(writer, "User Settings");
        writer.close();
    }
}
