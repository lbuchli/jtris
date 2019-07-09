package ch.vfl.jtris.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;

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
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                //TODO ALIGN THE SHAPE TO CENTER0
                if (shape[i][j]) {
                    graphicsContext.setFill(next.getColor());
                    graphicsContext.fillRect(i*width, j*height, width, height);
                }
            }
        }
    }
}

@FunctionalInterface
interface IBlockFeeder {
    Block generateNext();
}
