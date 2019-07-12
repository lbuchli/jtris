package ch.vfl.jtris.start;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.game.GameView;
import ch.vfl.jtris.leaderboard.LeaderboardView;
import ch.vfl.jtris.options.OptionsView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class StartView implements IView {

    private Scene currentScene;

    private Button startButton;
    private Button optionsButton;
    private Button quitButton;
    private Button leaderboardButton;

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
        currentScene = new Scene(root, 500, 500);

        startButton =       (Button) root.lookup("#start");
        optionsButton =     (Button) root.lookup("#options");
        quitButton =        (Button) root.lookup("#quit");
        leaderboardButton = (Button) root.lookup("#leaderboard");

        return currentScene;
    }

    @Override
    public void run(IViewController controller) {
        currentScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.S) {
                controller.setView(new GameView());
            }
        });
        startButton.setOnAction((ActionEvent e) ->       controller.setView(new GameView()));
        optionsButton.setOnAction((ActionEvent e) ->     controller.setView(new OptionsView()));
        quitButton.setOnAction((ActionEvent e) ->        controller.setView(null));
        leaderboardButton.setOnAction((ActionEvent e) -> controller.quit());
    }
}