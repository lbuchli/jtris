package ch.vfl.jtris.game;

import javafx.scene.canvas.Canvas;

class Next {
    private static final int POLYOMINO_SIZE = 4;

    private Block next;

    private Canvas canvas;

    Next(Canvas next) {
        this.canvas = next;
    }

    Block generateNext() {
        next = new Block(POLYOMINO_SIZE);

        // TODO draw block on pane

        return next;
    }
}
