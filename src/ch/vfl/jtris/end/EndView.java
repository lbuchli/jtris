package ch.vfl.jtris.end;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.game.GameView;
import ch.vfl.jtris.leaderboard.LeaderboardView;
import ch.vfl.jtris.start.StartView;
import ch.vfl.jtris.util.Leaderboard;
import ch.vfl.jtris.util.LeaderboardEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class EndView implements IView {

    private Button menuButton;
    private Button retryButton;
    private Button leaderboardButton;
    private Parent root;

    private int finalScore;

    private boolean savedToLeaderboard;

    public EndView(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public Scene start() throws IOException {
        root = FXMLLoader.load(getClass().getResource("EndView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        menuButton =        (Button) root.lookup("#menu");
        retryButton =       (Button) root.lookup("#retry");
        leaderboardButton = (Button) root.lookup("#leaderboard");

        // display score
        ((Text) root.lookup("#finalscore")).setText(Integer.toString(finalScore));

        savedToLeaderboard = false;

        return scene;
    }

    @Override
    public void run(IViewController controller) {
        menuButton.setOnAction((ActionEvent e) ->        { setLeaderboardEntry(); controller.setView(new StartView()); });
        retryButton.setOnAction((ActionEvent e) ->       { setLeaderboardEntry(); controller.setView(new GameView()); });
        leaderboardButton.setOnAction((ActionEvent e) -> { setLeaderboardEntry(); controller.setView(new LeaderboardView()); });



    }
    private void setLeaderboardEntry() {
        if (!savedToLeaderboard) {
            Leaderboard instance = Leaderboard.getInstance();
            instance.setEntry(new LeaderboardEntry(((TextField) root.lookup("#name")).getText(), finalScore));
            try {
                instance.write();
            } catch (IOException e) {
                e.printStackTrace();
            }

            savedToLeaderboard = true;
        }
    }
}
