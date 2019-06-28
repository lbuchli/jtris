package ch.vfl.jtris;

import ch.vfl.jtris.game.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GameView view = new GameView();

        stage.setTitle("Jtris");
        stage.setScene(view.run());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

