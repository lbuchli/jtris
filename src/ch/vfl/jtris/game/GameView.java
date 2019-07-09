package ch.vfl.jtris.game;

import ch.vfl.jtris.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

public class GameView {

    private Field field;
    private Next next;
    private Score score;

    public GameView() {}

    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GameView.fxml"));
        Scene scene = new Scene(root, 240, 400);

        score = new Score((Text) scene.lookup("#score"));
        next = new Next((Canvas) scene.lookup("#next"));
        field = new Field((Canvas) scene.lookup("#field"), next);

        field.setScoreRecipient(score);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            field.onKeyboardInput(key);
        });


        return scene;
    }

    public void run() {
        new Thread(() -> field.run()).start();
            playmusic();
    }

    private void playmusic(/*String filename*/){
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/music/final_atheme.wav"));
                    clip.open(inputStream);
                    clip.loop(20000);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    System.out.println("Unable to play music!");
                }
            }
        }).start();
    }

    private void playsound(String soundname){
        String musicFile = "resources/sounds/" + soundname + ".wav";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}

