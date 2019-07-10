package ch.vfl.jtris.options;

import ch.vfl.jtris.start.StartView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Options{

    ObservableList<String> musicList = FXCollections.observableArrayList("A-Theme", "Katyusha");
    private StartView view;
    private Scene sOne;
    private ChoiceBox musicBox;

    Stage stage;

    private void initilize() {
        musicBox.setValue("A-Theme");
        musicBox.setItems(musicList);
    }

    public Scene start() throws IOException {
        view = new StartView();


        Parent root = FXMLLoader.load(getClass().getResource("Options.fxml"));
        Scene scene = new Scene(root, 240, 400);

        Button button = new Button("Back");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    sOne = view.start();
                }
                catch (IOException i) {
                    System.out.println("I'm sorry John...");
                }
                stage.setScene(sOne);
            }
        });

        return scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}