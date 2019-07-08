package ch.vfl.jtris.game;

import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

class Field {
    private static final int FIELD_TILE_WIDTH = 10;


    private Color[][] field;
    private Block current;

    private Canvas canvas;
    private IBlockFeeder feeder;

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

    void start() {
        // TODO start main game loop
    }

    void onKeyboardInput(KeyEvent key) {
        // TODO handle keyboard input
    }
}
