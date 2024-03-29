package ch.vfl.jtris.game;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Block {

    private boolean[][] shape;
    private Color color;

    public Block(int size) {
        // generate the shape randomly
        //int id = generateShape(size);
        //centerShape(size);
        //this.color = generateColorFromID(id, size);

        // generate shape based on existing ones
        Random random = new Random();
        int shape = random.nextInt(7);
        this.shape = Shapes.SHAPE_LIST[shape];
        this.color = Shapes.SHAPE_COLORS[shape];
    }

    public Block(Block block) {
        // clone the shape
        boolean[][] oldShape = block.getShape();
        this.shape = new boolean[oldShape.length][oldShape[0].length];
        for (int i = 0; i < oldShape.length; i++) {
            this.shape[i] = oldShape[i].clone();
        }

        this.color = block.getColor();
    }

    // rotateShape rotates the shape 90 degrees.
    public void rotateShape(boolean clockwise) {

        int N = shape.length;

        for (int x = 0; x < N / 2; x++) {
            for (int y = x; y < N-x-1; y++) {
                boolean temp = shape[x][y];
                shape[x][y] = shape[y][N-1-x];
                shape[y][N-1-x] = shape[N-1-x][N-1-y];
                shape[N-1-x][N-1-y] = shape[N-1-y][x];
                shape[N-1-y][x] = temp;
            }
        }

    }

    public boolean[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    // generateShape generates a shape randomly based on a size and saves it in this.shape
    // It uses a similar approach as the simple recursive algorithm described
    // in https://en.wikipedia.org/wiki/Polyomino, except it chooses one path at random
    // (which makes it non-recursive).
    // It returns the ID of the generated shape as an int.
    private int generateShape(int size) throws IllegalArgumentException {
        Random random = new Random();
        int id = 0;

        if (size > 0) {
            shape = new boolean[size][size];

            // start at middle of the shape field
            int x = size/2;
            int y = size/2;

            shape[x][y] = true;

            for (int i = size-1; i > 0; i--) {

                List<Integer> possX = new ArrayList<>();
                List<Integer> possY = new ArrayList<>();

                // check all direct neighbors
                if (x+1 < size && !shape[x+1][y]) { possX.add(1);  possY.add(0);  }
                if (x-1 > 0    && !shape[x-1][y]) { possX.add(-1); possY.add(0);  }
                if (y+1 < size && !shape[x][y+1]) { possX.add(0);  possY.add(1);  }
                if (y-1 > 0    && !shape[x][y-1]) { possX.add(0);  possY.add(-1); }

                // choose one neighbor at random
                int neighbor = random.nextInt(possX.size());

                // update x and y
                x += possX.get(neighbor);
                y += possY.get(neighbor);
                shape[x][y] = true;

                // update id
                // neighbor can only be between 0 and 3, so it occupies exactly 2 bits.
                // We compare the current neighbor to the last neighbor. There can be 3 different values: 0, 1, -1
                // This allows us to have an id that is the same no matter the rotation.
                int last = id & 0b11;
                id = (id << 2) | (last == neighbor ? 0b00 : (last >= neighbor ? 0b01 : 0b11));
            }
        } else {
            throw new IllegalArgumentException("The size must be between 1 and 32.");
        }

        // cut id so that the first two bits are left out
        return (id >> ((size-2) * 2));
    }


    // generateColorFromID takes a hacky approach to generate a Color from a block id.
    private Color generateColorFromID(int id, int size) {
        double r = 0;
        double g = 0;
        double b = 0;

        for (int i = 0; i < size - 2; i++) {
            switch (id & 0b11) {
                case 0b00:
                    r++;
                    break;
                case 0b01:
                    g++;
                    break;
                case 0b11:
                    b++;
                    break;
            }

            id = (id >> 2);
        }

        return new Color(r/size, g/size, b/size, 1);
    }

    // centerShape centers the shape
    private void centerShape(int size) {
        double center = size/2d;
        double offsetX = 0;
        double offsetY = 0;

        // calculate the average offset
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (shape[x][y]) {
                    offsetX += x-center;
                    offsetY += y-center;
                }
            }
        }
        int intOffsetX = (int)(offsetX/size);
        int intOffsetY = (int)(offsetY/size);

        if (intOffsetX > 0 || intOffsetY > 0) {
            boolean[][] newShape = new boolean[size][size];

            int fromX = (intOffsetX > 0 ? intOffsetX : 0);
            int toX = (intOffsetX > 0 ? size : size + intOffsetX);
            int fromY = (intOffsetY > 0 ? intOffsetX : 0);
            int toY = (intOffsetY > 0 ? size : size + intOffsetY);

            int nx = 0;
            int ny = 0;

            // copy old to new with offset
            for (int x = fromX; x < toX; x++) {
                for (int y = fromY; y < toY; y++) {
                    newShape[nx][ny] = shape[x][y];
                    ny++;
                }
                nx++;
            }

            // overwrite old with new one
            shape = newShape;
        }
    }

}
