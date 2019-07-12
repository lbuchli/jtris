package ch.vfl.jtris.util;

import java.util.UUID;

public class LeaderboardEntry {
    private UUID uuid;
    private String name;
    private int score;

    public LeaderboardEntry(String name, int score) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.score = score;
    } //Create LeaderboeardEntry type

    public LeaderboardEntry(String uuid, String data) {

    }

    public String getUUID() {
        return uuid.toString();
    } //Take uuid (from LeaderboardEntry) and return String

    public UUID getRawUUID() {
        return uuid;
    }

    public Integer getScore(){
        return score;
    }

    public String getRepresentation() {

        String product = name + "-," + score;

        return product;
    }//create information string
}
