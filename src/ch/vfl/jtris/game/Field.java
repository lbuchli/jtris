package ch.vfl.jtris.game;

import ch.vfl.jtris.Main;
import ch.vfl.jtris.util.Canvas;
import ch.vfl.jtris.util.Settings;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

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

    void run() {
        spawnNewBlock();

        boolean interrupted = false;
        int speed;

        while (!interrupted) {
            if (isPossibleMove(0, 1)) {
                currentPosY++;
            } else {
                cloneBlockToField();
                spawnNewBlock();

                // check if spawned block is spawned into another block
                if (!isPossibleMove(0, 0)) interrupted = true;
            }

            // speed up falling of the blocks
            speed = nextSpeed();

            // we can do that because JavaFX runs our stuff in parallel
            try {

                Thread.sleep(speed);
            } catch (InterruptedException e) {
                interrupted = true;
            }

            drawField();
        }
    }

    void onKeyboardInput(KeyEvent keyEvent){
        switch(keyEvent.getCode()) {
            case W:
                if (isPossibleRotation(true)) current.rotateShape(true);
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
        Block rotatedBlock = new Block(current);
        rotatedBlock.rotateShape(clockwise);
        boolean[][] shape = rotatedBlock.getShape();

        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y]) {
                    int fieldPosX = currentPosX + x;
                    int fieldPosY = currentPosY + y;

                    if (fieldPosX >= field.length || fieldPosX < 0 ||
                        fieldPosY >= field[fieldPosX].length || fieldPosY < 0 ||
                        field[fieldPosX][fieldPosY] != null) {

                        playSound("error");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void drawField() {
        boolean[][] shape = current.getShape();

        canvas.clear();

        // draw the field
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] != null) {
                    canvas.drawFancySquare(x, y, field[x][y]);
                }
            }
        }

        // draw the current block
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                if (shape[x][y]) {
                    canvas.drawFancySquare(x+currentPosX, y+currentPosY, current.getColor());
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

        // check for lines to delete
        boolean[] fullLines = getFullLines();
        for (boolean line : fullLines) {
            if (line) {
                score.addScore(100);
            }
        }
        deleteLines(fullLines);
    }

    private boolean[] getFullLines() {
        int ySquares = canvas.getYSquares();
        boolean[] fullLines = new boolean[ySquares];
        for (int i = 0; i < fullLines.length; i++) {
            fullLines[i] = true;
        }

        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] == null) {
                    fullLines[y] = false;
                }
            }
        }

        return fullLines;
    }

    private void deleteLines(boolean[] lines) {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (lines[y]) {
                    Color[] newLine = new Color[field[x].length];
                    newLine[0] = null;

                    // up to line deletion
                    for (int i = 0; i < y; i++) {
                        newLine[i+1] = field[x][i];
                    }

                    // down onwards from line deletion
                    for (int i = y+1; i < field[x].length; i++) {
                        newLine[i] = field[x][i];
                    }

                    // overwrite line
                    field[x] = newLine;
                }
            }
        }
    }

    public void setScoreRecipient(IScoreRecipient recipient) {
        this.score = recipient;
    }


    private void playSound(String filename) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream("/sounds/" + filename + ".wav"));
                clip.open(inputStream);
                FloatControl gainControl =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(Float.parseFloat(Settings.getInstance().get("effect_volume")) * 100);
                clip.start();
            } catch (Exception e) {}
        }).start();
    }

    private int nextSpeed() {
        return 1000 - score.getScore() / 5;
    }
}
