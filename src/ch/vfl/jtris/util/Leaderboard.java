package ch.vfl.jtris.util;

import java.io.*;
import java.util.*;

public class Leaderboard {
    private static final String FILEPATH = "saves/leaderboard.properties";

    private static Leaderboard instance;

    private Properties properties;

    private Leaderboard() throws IOException {
        InputStream leaderFile = ClassLoader.getSystemClassLoader().getResourceAsStream(FILEPATH);
        properties = new Properties();
        properties.load(leaderFile);
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
        properties.setProperty(entry.getUUID(), entry.getRepresentation());
    }

    public String getEntry(UUID key) {
        return properties.getProperty(key.toString());
    } //get data from UUID (key)

    public ArrayList<LeaderboardEntry> getTopEntries(int top) {
        ArrayList<String[]> scores = new ArrayList<>();
        ArrayList<LeaderboardEntry> leaderboard = new ArrayList<>();

        Enumeration<String> uuids = (Enumeration<String>) properties.propertyNames();
        HashMap<UUID, LeaderboardEntry> entries = new HashMap<>();
        while (uuids.hasMoreElements()) {
            UUID uuid = UUID.fromString(uuids.nextElement());
            entries.put(uuid, new LeaderboardEntry(uuid.toString(), (String) properties.get(uuid.toString())));
        }


        int leaderboardSize = top > entries.size() ? entries.size() : top;
        while (leaderboard.size() < leaderboardSize) {
            int bestScore = -1;
            LeaderboardEntry bestEntry = new LeaderboardEntry("", -1);

            Set<UUID> entryKeys = entries.keySet();
            for (UUID key : entryKeys) {
                LeaderboardEntry entry = entries.get(key);
                if (entry.getScore() > bestScore) {
                    bestScore = entry.getScore();
                    bestEntry = entry;
                }
            }

            leaderboard.add(bestEntry);
            entries.remove(bestEntry.getRawUUID());
        }

        return leaderboard;
    }


    public void write() throws IOException {
        OutputStream leaderFile = new FileOutputStream(new File(ClassLoader.getSystemClassLoader().getResource(FILEPATH).getFile()));
        properties.store(leaderFile, "Leaderboard");
        leaderFile.close();
    } //Write to file
}
