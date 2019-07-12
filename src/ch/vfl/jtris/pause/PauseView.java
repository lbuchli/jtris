package ch.vfl.jtris.pause;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.start.StartView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class PauseView implements IView {

    private Scene currentScene;

    private Button continueButton;
    private Button menuButton;

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PauseView.fxml"));
        currentScene = new Scene(root, 500, 500);

        continueButton = (Button) root.lookup("#continue");
        menuButton = (Button) root.lookup("#menu");

        return currentScene;
    }

    @Override
    public void run(IViewController controller) {
        currentScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.C) {
                controller.setView(null); // go back
            }
        });
        continueButton.setOnAction((ActionEvent e) -> controller.setView(null)); // go back
        menuButton.setOnAction((ActionEvent e) -> controller.setView(new StartView()));
    }
}