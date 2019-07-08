package ch.vfl.jtris.game;

import javafx.scene.text.Text;

class Score implements IScoreRecipient {
    private int score;

    private Text display;

    Score(Text score) {
        this.display = score;
        this.score = 0;
    }

    public void setScore(int score) {
        this.score = score;

        // TODO draw score
    }
}

@FunctionalInterface
interface IScoreRecipient {
    void setScore(int score);
}
