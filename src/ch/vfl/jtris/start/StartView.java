package ch.vfl.jtris.start;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.game.GameView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class StartView implements IView {

    private Scene currentScene;

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
        currentScene = new Scene(root, 240, 400);
        return currentScene;
    }

    @Override
    public void run(IViewController controller) throws InterruptedException {
        GameView gameView = new GameView();
        currentScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            controller.setView(gameView);
        });
    }
}