package ch.vfl.jtris.util;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;

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
    } //set data to (entry.getRepresentation) UUID (entry.getUUID)

    public String getEntry(UUID key) {
        return properties.getProperty(key.toString());
    } //get data from UUID (key)

    public LeaderboardEntry[] getTopEntries(int top, LeaderboardEntry entry) {
        String[][] scores = new String[500][1];
        LeaderboardEntry[] leaderboard = new LeaderboardEntry[top];

        Enumeration<UUID> enums = (Enumeration<UUID>) properties.propertyNames();
        while (enums.hasMoreElements()) {
            int counter = 0;

            UUID key = enums.nextElement();
            String nowuuid = getEntry(key);
            int nowscore = entry.getScore();
            while(Integer.parseInt(scores[counter][0]) > nowscore){
                counter ++;
            }
            scores[counter][0] = Integer.toString(nowscore);
            scores[counter][1] = nowuuid;
            leaderboard[counter] = entry;
        }


        //Cycle trough Keys
            //int counter = 0;
            //getEntry(Key) to String
            //getScore(Key) to Int
            //save UUID with score in 2d array scores[score][uuid]
            //while scores[score] > getScore
                //counter++;
            //save score and uuid in scores[score][uuid]
        //save LeaderboardEntry in LeaderboardEntry[]


        return null;
    } //Get Array with (top)*top players

    public void write() throws IOException {
        OutputStream leaderFile = new FileOutputStream(new File(ClassLoader.getSystemClassLoader().getResource(FILEPATH).getFile()));
        properties.store(leaderFile, "Leaderboard");
        leaderFile.close();
    } //Write to file
}
