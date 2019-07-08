package ch.vfl.jtris.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class GameView {

    private Field field;
    private Next next;
    private Score score;

    public GameView() {}

    public Scene run() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        score = new Score((Text) scene.lookup("#score"));
        next = new Next((Canvas) scene.lookup("#next"));
        field = new Field((Canvas) scene.lookup("#field"), next);

        // TODO instantiate and hook up Field, Next and Score

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            field.onKeyboardInput(key);
        });

        return scene;
    }
}
