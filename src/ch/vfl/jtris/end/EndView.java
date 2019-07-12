package ch.vfl.jtris.end;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.game.GameView;
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
    private Parent root;

    private int finalScore;

    public EndView(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public Scene start() throws IOException {
        root = FXMLLoader.load(getClass().getResource("EndView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        menuButton = (Button) root.lookup("#menu");
        retryButton = (Button) root.lookup("#retry");

        // display score
        ((Text) root.lookup("#finalscore")).setText(Integer.toString(finalScore));

        return scene;
    }

    @Override
    public void run(IViewController controller) {
        menuButton.setOnAction((ActionEvent e) -> { controller.setView(new StartView()); setLeaderboardEntry(); });
        retryButton.setOnAction((ActionEvent e) -> { controller.setView(new GameView()); setLeaderboardEntry(); });



    }
    private void setLeaderboardEntry() {
        Leaderboard instance = Leaderboard.getInstance();
        instance.setEntry(new LeaderboardEntry(((TextField) root.lookup("#name")).getText(), finalScore));
        try {
            instance.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
