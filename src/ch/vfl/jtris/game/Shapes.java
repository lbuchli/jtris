package ch.vfl.jtris.game;

import javafx.scene.paint.Color;

public class Shapes {
    public static final boolean[][][] SHAPE_LIST = {
         {
              {false, false, false, false},
              {false, true,  true,  false},
              {false, true,  true,  false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {true,  true,  false, false},
              {false, true,  true,  false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {false, true,  true,  false},
              {true,  true,  false, false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {true,  true,  true,  false},
              {true,  false, false, false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {false, true,  false, false},
              {true,  true,  true,  false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {true,  true,  true,  false},
              {false, false, true,  false},
              {false, false, false, false},
         },
         {
              {false, false, false, false},
              {true,  true,  true,  true },
              {false, false, false, false},
              {false, false, false, false},
         },
    };

    final static Color[] SHAPE_COLORS = {
         new Color(1, 1, 0, 1),
         new Color(1, 0, 0, 1),
         new Color(0, 1, 0, 1),
         new Color(1, 0.6, 0, 1),
         new Color(1, 0, 1, 1),
         new Color(0, 0, 1, 1),
         new Color(0, 1, 1, 1)
    };
}
