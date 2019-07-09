package ch.vfl.jtris.game;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

class Field {
    private static final int FIELD_TILE_WIDTH = 10;


    private Color[][] field;
    private Block current;
    private int currentPosX;
    private int currentPosY;

    private Canvas canvas;
    private IBlockFeeder feeder;
    private IScoreRecipient score;

    Field(Canvas field, IBlockFeeder feeder) {
        this.canvas = field;
        this.feeder = feeder;

        // make the field
        double fieldSize = canvas.getWidth() / FIELD_TILE_WIDTH;
        int fieldTileHeight = (int) (canvas.getHeight() / fieldSize);
        this.field = new Color[FIELD_TILE_WIDTH][fieldTileHeight];

        // make a new block
        this.current = feeder.generateNext();
    }

    public void setScoreRecipient(IScoreRecipient recipient) {
        this.score = recipient;
    }

    void run() {
        feeder.generateNext();
        // TODO start main game loop
    }

    void onKeyboardInput(KeyEvent keyEvent){
        // TODO handle keyboard input


        switch(keyEvent.getCode()) {
            case W:
                current.rotateShape(true);
                break;

            case A:
                currentPosX--;
                break;

            case S:
                currentPosY--;
                break;

            case D:
                currentPosX++;
                break;
        }

    }

    private boolean isPossibleMove(int x, int y) {
        // TODO check
        return true;
    }

    private boolean isPossibleRotation(boolean clockwise) {
        // TODO check
        return true;
    }
}
