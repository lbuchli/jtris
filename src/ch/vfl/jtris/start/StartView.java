package ch.vfl.jtris.start;

import ch.vfl.jtris.game.GameView;
import ch.vfl.jtris.options.Options;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView{

    private GameView game;
    private Options options;

    private Scene sOne, sTwo;

    Stage stage;

    public Scene start() throws IOException {
        game = new GameView();
        options = new Options();
        sOne = game.start();
        sTwo = options.start();

        Parent root = FXMLLoader.load(getClass().getResource("StartView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        Button button = new Button("Options");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                options.setStage(stage);
                stage.setScene(sTwo);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            stage.setScene(sOne);
            game.run();
        });

        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}