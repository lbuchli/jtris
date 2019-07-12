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

        this.name = this.name.replace(';', ' ');
    }

    public LeaderboardEntry(String uuid, String data) {
        this.uuid = UUID.fromString(uuid);
        String[] splitData = data.split(";");
        this.name = splitData[0];

        if (splitData[1].matches("[0-9]+"))
            this.score = Integer.parseInt(splitData[1]);
    }

    public String getUUID() {
        return uuid.toString();
    }

    public UUID getRawUUID() {
        return uuid;
    }

    public Integer getScore(){
        return score;
    }

    public String getName() { return name; }

    public String getRepresentation() {
        return name + ";" + score;
    }
}
