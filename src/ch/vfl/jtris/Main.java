package ch.vfl.jtris;

import ch.vfl.jtris.start.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        StartView view = new StartView();

        stage.setHeight(440);
        stage.setWidth(500);
        stage.setTitle("Jtris");
        Scene currentScene = view.start();

        view.setStage(stage);

        stage.setScene(currentScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}