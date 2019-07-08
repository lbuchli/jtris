package ch.vfl.jtris.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class Next implements IBlockFeeder {
    private static final int POLYOMINO_SIZE = 4;

    private Block next;

    private Canvas canvas;
    private GraphicsContext graphicsContext;

    Next(Canvas next) {
        this.canvas = next;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
    }

    public Block generateNext() {
        next = new Block(POLYOMINO_SIZE);
        drawNextBlock();

        return next;
    }

    // drawNextBlock draws the next block onto the canvas via the graphicsContext.
    private void drawNextBlock() {
        boolean[][] shape = next.getShape();
        double width = canvas.getWidth() / shape.length;
        double height = canvas.getHeight() / shape.length;
        //TODO ALIGN THE SHAPE TO CENTER
        graphicsContext.fillRect(0, 0, width, height);


    }
}
