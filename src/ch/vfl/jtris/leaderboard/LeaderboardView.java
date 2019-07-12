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

        ((Text) root.lookup("#entryA")).setText(prettyPrintEntry(leaderboard.get(0)));
        ((Text) root.lookup("#entryB")).setText(prettyPrintEntry(leaderboard.get(1)));
        ((Text) root.lookup("#entryC")).setText(prettyPrintEntry(leaderboard.get(2)));
        ((Text) root.lookup("#entryD")).setText(prettyPrintEntry(leaderboard.get(3)));
        ((Text) root.lookup("#entryE")).setText(prettyPrintEntry(leaderboard.get(4)));
    }

    private String prettyPrintEntry(LeaderboardEntry entry) {
        return entry.getName() + ": " + entry.getScore();
    }
}
