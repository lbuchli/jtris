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
        String StrScore = " ";
        StrScore = Integer.toString(score);

        display.setText(StrScore);
    }

    public void addScore(int score) {
        setScore(this.score + score);
    }
}

interface IScoreRecipient {
    void setScore(int score);
    void addScore(int score);
}
