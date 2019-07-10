package ch.vfl.jtris;

import javafx.scene.Scene;

import java.io.IOException;

public interface IView {
    Scene start() throws IOException;
    void run(IViewController controller);
}
