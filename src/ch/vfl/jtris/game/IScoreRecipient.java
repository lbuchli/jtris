package ch.vfl.jtris.game;

interface IScoreRecipient {
    void setScore(int score);
    void addScore(int score);
    int getScore();
}
