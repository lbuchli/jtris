package ch.vfl.jtris.game;

import javafx.scene.text.Text;

class Score {
    private int score;

    private Text display;

    Score(Text score) {
        this.display = score;
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
