package ch.vfl.jtris.game;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class GameView {

    private Color[][] field;
    private Block current;
    private Block next;
    private int score;

    public GameView() {}

    public Scene run() throws IOException {
        Parent root = load(getClass().getResource("GameView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        return scene;
    }
}
