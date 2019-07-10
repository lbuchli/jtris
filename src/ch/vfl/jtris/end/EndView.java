package ch.vfl.jtris.end;

import ch.vfl.jtris.IView;
import ch.vfl.jtris.IViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class EndView implements IView {
    @Override
    public Scene start() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EndScreen.fxml"));
        Scene scene = new Scene(root, 240, 400);

        return scene;
    }

    @Override
    public void run(IViewController controller) throws InterruptedException {}
}
