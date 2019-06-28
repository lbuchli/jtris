package ch.vfl.jtris.game;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

class Field {
    private static final int FIELD_TILE_HEIGHT = 20;


    private Color[][] field;
    private Block current;

    private Pane pane;
    private IBlockFeeder feeder;

    Field(Pane field, IBlockFeeder feeder) {
        this.pane = field;
        this.feeder = feeder;

        // make the field
        double fieldSize = pane.getHeight() / FIELD_TILE_HEIGHT;
        int fieldTileWidth = (int) (pane.getWidth() / fieldSize);
        this.field = new Color[fieldTileWidth][FIELD_TILE_HEIGHT];

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
