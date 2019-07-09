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

    public void setScoreRecipient(IScoreRecipient recipient) {
        this.score = recipient;
    }

    void run() {
        spawnNewBlock();

        boolean interrupted = false;

        while (!interrupted) {
            /*
            if (isPossibleMove(0, 1)) {
                currentPosY++;
            } else {
                spawnNewBlock();
            }*/
            currentPosY++;

            // we can do that because JavaFX runs our stuff in parallel
            try {
                Thread.sleep(200);
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
        boolean[][] shape = current.getShape();

        for (int i = 0; i < shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                if(shape[i][j]){
                    int playfieldposY = currentPosY + j;
                    int playfieldposX = currentPosX + i;

                    if(field[playfieldposX + x][playfieldposY + y] == null){
                        return true;

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
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != null) {
                    canvas.drawSquare(i, j, field[i][j]);
                }
            }
        }

        // draw the current block
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    canvas.drawSquare(i+currentPosX, j+currentPosY, current.getColor());
                }
            }
        }
    }

    private void spawnNewBlock() {
        current = feeder.generateNext();
        currentPosY = 0;
        currentPosX = (canvas.getXSquares() / 2) - (current.getShape().length / 2);
    }
}
