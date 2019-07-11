package ch.vfl.jtris.game;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import ch.vfl.jtris.Main;
import ch.vfl.jtris.end.EndView;
import ch.vfl.jtris.util.Settings;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.IOException;

public class GameView implements IView {

    private Field field;
    private Next next;
    private Score score;

    public GameView() {}

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameView.fxml"));
        Scene scene = new Scene(root, 500, 500);

        score = new Score((Text) scene.lookup("#score"));
        next = new Next((Canvas) scene.lookup("#next"));
        field = new Field((Canvas) scene.lookup("#field"), next);

        field.setScoreRecipient(score);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> field.onKeyboardInput(key));


        return scene;
    }


    public void run(IViewController controller) {
        new Thread(() -> {
            field.run();
            Platform.runLater(() -> controller.setView(new EndView(score.getScore())));
        }).start();
       playMusic(
               "katyusha",
               (1f - Float.parseFloat(Settings.getInstance().get("music_volume"))) * (-60)
       );
    }

    private void playMusic(String musicname, float volume){
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        ClassLoader.getSystemClassLoader().getResourceAsStream("music/" + musicname + ".wav"));
                clip.open(inputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.loop(20000);
                clip.start();
            } catch (Exception e) {}
        }).start();
    }


}

