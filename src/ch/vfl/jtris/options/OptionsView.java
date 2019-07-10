package ch.vfl.jtris.options;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.start.StartView;
import ch.vfl.jtris.util.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

import java.io.IOException;

public class OptionsView implements IView {

    private static final ObservableList<String> MUSIC_LIST = FXCollections.observableArrayList("A-Theme", "Katyusha");

    private ChoiceBox musicChoiceBox;
    private Slider musicVolumeSlider;
    private  Button backButton;

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OptionsView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        musicChoiceBox = (ChoiceBox<String>) root.lookup("#musicchoice");
        musicVolumeSlider = (Slider) root.lookup("#volume");
        backButton = (Button) root.lookup("#back");

        musicChoiceBox.setValue(Settings.getInstance().get("musictrack"));
        musicChoiceBox.setItems(MUSIC_LIST);

        musicVolumeSlider.adjustValue(Double.parseDouble(Settings.getInstance().get("volume")));

        return scene;
    }

    @Override
    public void run(IViewController controller) throws InterruptedException {
        backButton.setOnAction((ActionEvent e) -> {
            controller.setView(new StartView());

            Settings settings = Settings.getInstance();
            settings.set("musictrack", musicChoiceBox.getValue().toString());
            settings.set("volume", Double.toString(musicVolumeSlider.getValue()));

            try {
                Settings.getInstance().write();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}