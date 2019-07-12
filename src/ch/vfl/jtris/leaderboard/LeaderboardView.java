package ch.vfl.jtris.leaderboard;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.start.StartView;
import ch.vfl.jtris.util.Leaderboard;
import ch.vfl.jtris.util.LeaderboardEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderboardView implements IView {

    private final static int ENTRY_MAX_LENGTH = 14;

    private Button menuButton;
    private Button backButton;
    private Parent root;

    public Scene start() throws IOException{
        root = FXMLLoader.load(getClass().getResource("LeaderboardView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        menuButton = (javafx.scene.control.Button) root.lookup("#menu");
        backButton = (javafx.scene.control.Button) root.lookup("#back");

        return scene;

    }

    @Override
    public void run(IViewController controller) {
        backButton.setOnAction((ActionEvent e) -> controller.setView(null));
        menuButton.setOnAction((ActionEvent e) -> controller.setView(new StartView()));

        // display leaderboard
        getLeaderboard();
    }

    private void getLeaderboard(){
        Leaderboard instance = Leaderboard.getInstance();
        ArrayList<LeaderboardEntry> leaderboard = instance.getTopEntries(5);

        int leaderboardSize = leaderboard.size();
        for (int i = 0; i < leaderboardSize; i++) {
            ((Text) root.lookup("#entry" + i)).setText(prettyPrintEntry(leaderboard.get(i)));
        }
    }

    private String prettyPrintEntry(LeaderboardEntry entry) {
        String name = entry.getName();
        String score = String.valueOf(entry.getScore());
        if (name.length() + score.length() > ENTRY_MAX_LENGTH) {
            name = name.substring(0, ENTRY_MAX_LENGTH - score.length() - 2).concat("...");
        }

        return name + ": " + score;
    }
}
