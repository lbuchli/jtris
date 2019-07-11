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
    private Slider effectVolumeSlider;
    private  Button backButton;

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OptionsView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        effectVolumeSlider = (Slider) root.lookup("#effect_volume");
        musicChoiceBox = (ChoiceBox<String>) root.lookup("#music_choice");
        musicVolumeSlider = (Slider) root.lookup("#music_volume");
        backButton = (Button) root.lookup("#back");

        musicChoiceBox.setValue(Settings.getInstance().get("music_track"));
        musicChoiceBox.setItems(MUSIC_LIST);

        musicVolumeSlider. adjustValue(Double.parseDouble(Settings.getInstance().get("music_volume")));
        effectVolumeSlider.adjustValue(Double.parseDouble(Settings.getInstance().get("effect_volume")));

        return scene;
    }

    @Override
    public void run(IViewController controller) {
        backButton.setOnAction((ActionEvent e) -> {
            controller.setView(new StartView());

            String music = musicChoiceBox.getValue().toString();

            if (music == "A-Theme"){
                music = "atheme";
            }
            else {
                music = "katyusha";
            }

            Settings settings = Settings.getInstance();
            settings.set("effect_volume", Double.toString(effectVolumeSlider.getValue()));
            settings.set("music_track", music);
            settings.set("music_volume", Double.toString(musicVolumeSlider.getValue()));

            try {
                Settings.getInstance().write();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}