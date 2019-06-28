package ch.vfl.jtris.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class GameView {

    private Field field;
    private Next next;
    private Score score;

    public GameView() {}

    public Scene run() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
        Scene scene = new Scene(root, 240, 400);

        // TODO instanciate and hook up Field, Next and Score

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            field.onKeyboardInput(key);
        });

        return scene;
    }
}
