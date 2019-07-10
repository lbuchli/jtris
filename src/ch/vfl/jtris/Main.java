package ch.vfl.jtris;

import ch.vfl.jtris.start.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application implements IViewController {

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        stage.setHeight(440);
        stage.setWidth(500);
        stage.setTitle("Jtris");

        stage.show();

        setView(new StartView());
    }

    public static void main(String[] args) {
        launch();
    }

    public void setView(IView view) {
        try {
            stage.setScene(view.start());
            view.run(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}