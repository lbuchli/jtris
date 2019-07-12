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
import java.sql.Time;

class Field {
    private static final int FIELD_TILE_WIDTH = 10;

    private static final long LINE_DELETE_INTERVAL_DURATION = 150;
    private static final int  LINE_DELETE_INTERVALS = 3;

    private static final Color BACKGROUND_COLOR = new Color(105/256d, 146/256d, 185/256d, 1);

    private Color[][] field;
    private Block current;

    private int currentPosX;
    private int currentPosY;
    private int level = 0;

    private int speed;

    private boolean isGameOver;

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
        // current will already be set when resuming after pause
        if (current == null) spawnNewBlock();

        isGameOver = false;

        boolean interrupted = false;
        while (!interrupted && !isGameOver) {
            if (isPossibleMove(0, 1)) {
                currentPosY++;
            } else {
                cloneBlockToField();
                spawnNewBlock();

                // check if spawned block is spawned into another block
                if (!isPossibleMove(0, 0)) isGameOver = true;
            }

            // speed up falling of the blocks
            speed = nextSpeed(level);

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
        canvas.clear();

        // draw the field
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (field[x][y] != null) {
                    canvas.drawFancySquare(x, y, field[x][y]);
                }
            }
        }

        // draw current block
        if (current != null) {
            boolean[][] shape = current.getShape();
            for (int x = 0; x < shape.length; x++) {
                for (int y = 0; y < shape[x].length; y++) {
                    if (shape[x][y]) {
                        canvas.drawFancySquare(x+currentPosX, y+currentPosY, current.getColor());
                    }
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

        // clear the block
        current = null;

        // check for lines to delete
        boolean hasLinesToDelete = false;
        boolean[] fullLines = getFullLines();
        for (boolean line : fullLines) {
            if (line) {
                score.addScore(100);
                hasLinesToDelete = true;
            }
        }

        // delete full lines
        if (hasLinesToDelete) deleteLines(fullLines);
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
        // blink
        try {
            blinkLines(lines, LINE_DELETE_INTERVAL_DURATION, LINE_DELETE_INTERVALS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // delete and fall down
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

        drawField();
    }

    private void blinkLines(boolean[] lines, long intervalDuration, int intervals) throws InterruptedException {
        for (int i = 0; i < intervals; i++) {
            for (int y = 0; y < lines.length; y++) {
                if (lines[y]) {
                    for (int x = 0; x < field.length; x++) {
                        canvas.drawSquare(x, y, BACKGROUND_COLOR);
                    }
                }
            }
            Thread.sleep(intervalDuration);
            drawField();
            Thread.sleep(intervalDuration);
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
                gainControl.setValue((1f - Float.parseFloat(Settings.getInstance().get("music_volume"))) * (-60));
                clip.start();
            } catch (Exception e) {}
        }).start();
    }

    private int nextSpeed(int level) {
        int levelSpeed;
        double d;
        if (level == 0){
            level++;
        }
        while(level * 500 <= score.getScore()){
            level++;
        }

        d = ((11 - level) * 0.05) * 1000;
        levelSpeed = (int) d;
        return levelSpeed;
    }

    public boolean getIsGameOver() { return isGameOver; }
}
