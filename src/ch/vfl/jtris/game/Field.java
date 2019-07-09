package ch.vfl.jtris.game;

import ch.vfl.jtris.util.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

class Field {
    private static final int FIELD_TILE_WIDTH = 10;

    private Color[][] field;
    private Block current;

    private int currentPosX;
    private int currentPosY;

    private int nextXOffset;
    private int nextYOffset;

    private Canvas canvas;

    private IBlockFeeder feeder;
    private IScoreRecipient score;

    Field(javafx.scene.canvas.Canvas field, IBlockFeeder feeder) {
        this.canvas = new Canvas(field);
        this.canvas.divideXToSquares(FIELD_TILE_WIDTH);

        this.feeder = feeder;

        // make the field
        this.field = new Color[canvas.getXSquares()][canvas.getYSquares()];
    }

    void run() {
        spawnNewBlock();

        boolean interrupted = false;

        while (!interrupted) {
            if (isPossibleMove(0, 1)) {
                currentPosY++;
            } else {
                cloneBlockToField();
                spawnNewBlock();
            }

            // we can do that because JavaFX runs our stuff in parallel
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                interrupted = true;
            }

            drawField();
        }
    }

    void onKeyboardInput(KeyEvent keyEvent){
        switch(keyEvent.getCode()) {
            case W:
                current.rotateShape(true);
                break;

            case A:
                if (isPossibleMove(-1, 0)) currentPosX--;
                break;

            case S:
                if (isPossibleMove(0, 1)) currentPosY++;
                break;

            case D:
                if (isPossibleMove(1, 0)) currentPosX++;
                break;
        }
        drawField();
    }

    private boolean isPossibleMove(int xOffset, int yOffset) {
        boolean[][] shape = current.getShape();

        for (int x = 0; x < shape.length; x++){
            for (int y = 0; y < shape[x].length; y++){
                if(shape[x][y]){
                    int playfieldposX = currentPosX + x + xOffset;
                    int playfieldposY = currentPosY + y + yOffset;

                    if(playfieldposX < field.length && playfieldposX >= 0 &&
                        playfieldposY < field[playfieldposX].length && playfieldposY >= 0 &&
                        field[playfieldposX][playfieldposY] == null){
                    }else {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isPossibleRotation(boolean clockwise) {
        // TODO check
        return true;
    }

    private void drawField() {
        boolean[][] shape = current.getShape();

        canvas.clear();

        // draw the field
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] != null) {
                    canvas.drawSquare(x, y, field[x][y]);
                }
            }
        }

        // draw the current block
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y]) {
                    canvas.drawSquare(x+currentPosX, y+currentPosY, current.getColor());
                }
            }
        }
    }

    private void spawnNewBlock() {
        current = feeder.generateNext();
        currentPosY = 0;
        currentPosX = (canvas.getXSquares() / 2) - (current.getShape().length / 2);
    }

    private void cloneBlockToField() {
        boolean[][] shape = current.getShape();
        Color color = current.getColor();

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y]) {
                    field[x+currentPosX][y+currentPosY] = color;
                }
            }
        }
    }

    public void setScoreRecipient(IScoreRecipient recipient) {
        this.score = recipient;
    }
}
