package ch.vfl.jtris.game;

import ch.vfl.jtris.util.Canvas;

class Next implements IBlockFeeder {
    private static final int POLYOMINO_SIZE = 4;

    private static final double NEXT_CANVAS_X = 315;
    private static final double NEXT_CANVAS_Y = 98;

    private Block next;

    private Canvas canvas;

    Next(javafx.scene.canvas.Canvas next) {
        this.canvas = new Canvas(next);
        this.canvas.divideXToSquares(POLYOMINO_SIZE);

        this.next = new Block(POLYOMINO_SIZE);
        drawNextBlock();
    }

    public Block generateNext() {
        Block old = next;
        next = new Block(POLYOMINO_SIZE);
        drawNextBlock();

        return old;
    }

    // drawNextBlock draws the next block onto the canvas via the graphicsContext.
    private void drawNextBlock() {
        canvas.clear();

        // draw new block
        boolean[][] shape = next.getShape();
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y]) {
                    canvas.drawFancySquare(x, y, next.getColor());
                }
            }
        }
    }
}

