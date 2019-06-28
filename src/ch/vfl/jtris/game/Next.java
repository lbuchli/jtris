package ch.vfl.jtris.game;

import javafx.scene.layout.Pane;

class Next {
    private static final int POLYOMINO_SIZE = 4;

    private Block next;

    private Pane pane;

    Next(Pane next) {
        this.pane = pane;
    }

    Block generateNext() {
        next = new Block(POLYOMINO_SIZE);

        // TODO draw block on pane

        return next;
    }
}
