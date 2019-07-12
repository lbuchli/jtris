package ch.vfl.jtris;

import ch.vfl.jtris.start.StartView;
import ch.vfl.jtris.util.Settings;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application implements IViewController {

    private Stage stage;

    private ArrayList<Pair<IView, Scene>> viewStack;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.viewStack = new ArrayList<>();

        stage.setHeight(539);
        stage.setWidth(516);
        stage.setTitle("Jtris");

        stage.show();

        setView(new StartView());
    }

    public static void main(String[] args) {
        Settings.getInstance(); // generate a Settings instance
        launch();
    }

    public void setView(IView view) {
        Scene scene = null;
        if (view == null) {
            viewStack.remove(viewStack.size()-1);
            Pair<IView, Scene> viewPair = viewStack.get(viewStack.size()-1);
            view = viewPair.getKey();
            scene = viewPair.getValue();
        } else {
            try {
                scene = view.start();
                viewStack.add(new Pair<>(view, scene));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        stage.setScene(scene);
        view.run(this);
    }
}