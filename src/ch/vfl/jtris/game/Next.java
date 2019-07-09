package ch.vfl.jtris.game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

class Next implements IBlockFeeder {
    private static final int POLYOMINO_SIZE = 4;

    // Adding padding to the squares fixes rounding errors when drawing to the screen.
    // These rounding errors caused small gaps between the squares.
    private static final double SQUARE_PADDING = 0.2;

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
        // clear canvas
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // draw new block
        boolean[][] shape = next.getShape();
        double width = canvas.getWidth() / shape.length;
        double height = canvas.getHeight() / shape.length;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                //TODO ALIGN THE SHAPE TO CENTER
                if (shape[i][j]) {
                    graphicsContext.setFill(next.getColor());
                    graphicsContext.fillRect(i*width - SQUARE_PADDING, j*height - SQUARE_PADDING,
                            width + 2*SQUARE_PADDING, height + 2*SQUARE_PADDING);
                }
            }
        }
    }
}

@FunctionalInterface
interface IBlockFeeder {
    Block generateNext();
}
