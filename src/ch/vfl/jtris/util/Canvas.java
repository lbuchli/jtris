package ch.vfl.jtris.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas {

    // Adding padding to the squares fixes rounding errors when drawing to the screen.
    // These rounding errors caused small gaps between the squares.
    private static final double SQUARE_PADDING = 0.5;

    private javafx.scene.canvas.Canvas canvas;
    private GraphicsContext graphicsContext;

    private double squareSize;

    private double xOffset;
    private double yOffset;

    public Canvas(javafx.scene.canvas.Canvas from) {
        this.canvas = from;
        this.graphicsContext = this.canvas.getGraphicsContext2D();
    }

    public void drawRect(double x, double y, double w, double h, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, w, h);
    }

    // divideXToSquares divides the canvas into even squares, using
    // the whole width
    public void divideXToSquares(int x) {
        squareSize = canvas.getWidth() / x;
        xOffset = 0;
        yOffset = canvas.getHeight() % squareSize;
    }

    // divideYToSquares divides the canvas into even squares, using
    // the whole height
    public void divideYToSquares(int y) {
        squareSize = canvas.getHeight() / y;
        xOffset = 0;
        yOffset = canvas.getWidth() % squareSize;
    }

    // drawSquare draws a square onto the canvas.
    public void drawSquare(int x, int y, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x*squareSize - SQUARE_PADDING+xOffset, y*squareSize - SQUARE_PADDING+yOffset,
                squareSize + 2*SQUARE_PADDING, squareSize + 2*SQUARE_PADDING);
    }

    public void drawFancySquare(int x, int y, Color color) {
        // TODO stub
        drawSquare(x, y, color);
    }

    public void clear() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // getXSquares returns the amount of squares in the X direction.
    public int getXSquares() {
        return (int) Math.round((canvas.getWidth() - xOffset) / squareSize);
    }

    // getYSquares returns the amount of squares in the Y direction.
    public int getYSquares() {
        return (int) Math.round((canvas.getHeight() - yOffset) / squareSize);
    }
}
