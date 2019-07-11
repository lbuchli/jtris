package ch.vfl.jtris.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.UUID;

public class Leaderboard {
    private static final String FILEPATH = "config/leaderboard.xml";

    private static Leaderboard instance;

    private Properties properties;

    private Leaderboard() throws IOException {
        InputStream leaderFile = ClassLoader.getSystemClassLoader().getResourceAsStream(FILEPATH);
        properties = new Properties();
        properties.loadFromXML(leaderFile);
        leaderFile.close();
    }

    public static Leaderboard getInstance() {
        if (instance == null) {
            try {
                instance = new Leaderboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public void setEntry(LeaderboardEntry entry) {
        properties.setProperty(entry.getUUID(), entry.getXMLRepresentation());
    }

    public String getEntry(UUID key) {
        return properties.getProperty(key.toString());
    }

    public LeaderboardEntry[] getTopEntries(int top) {
        return null; // TODO
    }

    public void write() throws IOException {
        OutputStream leaderFile = new FileOutputStream(new File(ClassLoader.getSystemClassLoader().getResource(FILEPATH).getFile()));
        properties.storeToXML(leaderFile, "Leaderboard", Charset.defaultCharset());
        leaderFile.close();
    }
}
