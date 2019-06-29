package ch.vfl.jtris.game;

import javafx.scene.canvas.Canvas;

class Score {
    private int score;

    private Canvas canvas;

    Score(Canvas score) {
        this.canvas = score;
        this.score = 0;
    }

    void setScore(int score) {
        this.score = score;

        // TODO draw score
    }
}

@FunctionalInterface
interface IBlockFeeder {
    Block generateNext();
}
