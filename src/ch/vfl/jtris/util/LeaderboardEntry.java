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
    }

    public String getUUID() {
        return uuid.toString();
    }

    public String getXMLRepresentation() {
        // TODO


        return "";
    }
}
