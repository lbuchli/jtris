package ch.vfl.jtris.game;

import javafx.scene.layout.Pane;

class Score {
    private int score;

    private Pane pane;

    Score(Pane score) {
        this.pane = score;
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
