package ch.vfl.jtris.start;

import ch.vfl.jtris.game.GameView;
import ch.vfl.jtris.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView{

    private GameView game;
    private Scene sOne;

    Stage stage;

    public Scene start() throws IOException {
        game = new GameView();
        sOne = game.start();

        Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            System.out.println("Pressed");
            stage.setScene(sOne);
            game.run();
        });

        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}