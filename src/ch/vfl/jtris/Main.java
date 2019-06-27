package ch.vfl.jtris;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = load(getClass().getResource("view/GameView.fxml"));

        Scene scene = new Scene(root, 240, 400);

        stage.setTitle("Jtris");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}

