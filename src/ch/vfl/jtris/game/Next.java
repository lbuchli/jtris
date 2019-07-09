package ch.vfl.jtris.game;

import ch.vfl.jtris.util.Canvas;

class Next implements IBlockFeeder {
    private static final int POLYOMINO_SIZE = 4;

    // Adding padding to the squares fixes rounding errors when drawing to the screen.
    // These rounding errors caused small gaps between the squares.
    private static final double SQUARE_PADDING = 0.2;

    private Block next;

    private Canvas canvas;

    Next(javafx.scene.canvas.Canvas next) {
        this.canvas = new Canvas(next);
        this.canvas.divideXToSquares(POLYOMINO_SIZE);
    }

    public Block generateNext() {
        next = new Block(POLYOMINO_SIZE);
        drawNextBlock();

        return next;
    }

    // drawNextBlock draws the next block onto the canvas via the graphicsContext.
    private void drawNextBlock() {
        canvas.clear();

        // draw new block
        boolean[][] shape = next.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                //TODO ALIGN THE SHAPE TO CENTER
                if (shape[i][j]) {
                    canvas.drawFancySquare(i, j, next.getColor());
                }
            }
        }
    }
}

@FunctionalInterface
interface IBlockFeeder {
    Block generateNext();
}
