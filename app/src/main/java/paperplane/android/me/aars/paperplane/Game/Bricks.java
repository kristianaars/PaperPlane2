package paperplane.android.me.aars.paperplane.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Stack;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.GUI.Gradient2D;
import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian Aars on 03.09.2015.
 */
public class Bricks extends Component2D {

    private int color;
    private Resources res;
    private GameView gameView;
    private double y;
    private double last_y = 0;
    private double offset;

    private int width = 0;
    private int height = 0;

    private Bitmap[] brickTypes;
    private Stack<Integer> bricks;

    private static final int BRICK_NO_WINDOW = 0;
    private static final int BRICK_LEFT_WINDOW = 1;
    private static final int BRICK_RIGHT_WINDOW = 2;

    private int[] BRICK_LEFT_WINDOW_VALUES;
    private int[] BRICK_RIGHT_WINDOW_VALUES;

    private int BRICK_HEIGHT;
    private static final int BRICK_TYPES = 3;
    private final static int[] STUTTER = {BRICK_NO_WINDOW, BRICK_NO_WINDOW, BRICK_NO_WINDOW, BRICK_LEFT_WINDOW, BRICK_NO_WINDOW, BRICK_NO_WINDOW, BRICK_NO_WINDOW, BRICK_RIGHT_WINDOW};
    private final int BRICK_BUFFER_SIZE;

    private final static int[] clrs = new int[]{ 0xffef9a9a, 0xffff8a80, 0xffff80ab, 0xffe1bee7};
    private static ArrayList<Integer> colors;
    private Gradient2D gradient;
    private Bitmap gradient_img;

    public Bricks(GameView v) {
        this.res = v.getResources();
        gameView = v;

        width = v.width;
        height = v.height;

        loadWindowValues();
        loadArrays();
        loadBitmaps();
        loadColors();

        BRICK_BUFFER_SIZE = STUTTER.length;
        setLevel(1);
    }

    private void loadWindowValues() {
        BRICK_LEFT_WINDOW_VALUES = new int[2];
        BRICK_RIGHT_WINDOW_VALUES = new int[2];

        BRICK_LEFT_WINDOW_VALUES[0] = (int) (width * 0.1157407407407407);
        BRICK_LEFT_WINDOW_VALUES[1] = (int) (width * 0.4370370370370370);

        BRICK_RIGHT_WINDOW_VALUES[0] = (int) (width * 0.5787037037037037);
        BRICK_RIGHT_WINDOW_VALUES[1] = (int) (width * 0.8842592592592593);
    }

    private void loadColors() {
        colors = new ArrayList<Integer>();

        for(int c : clrs) {
            colors.add(c);
        }

        color = colors.get(0);
    }

    private void loadArrays() {
        brickTypes = new Bitmap[BRICK_TYPES];

        bricks = new Stack<Integer>();
        for (int aSTUTTER : STUTTER) bricks.add(aSTUTTER);
    }

    Paint p = new Paint();

    @Override
    public void draw(Canvas c) {
        /*for(int i = 0; i < BRICK_BUFFER_SIZE; i++) {
            drawBrick(i , c);
        }*/

        //p.setColor(0xffbb8bbf);
        //c.drawRect(0, 0, gameView.width, gameView.height, p);
        //gradient.draw(c);
        //c.drawBitmap(gradient_img, 0, 0, null);
    }

    private int tick;

    @Override
    public void update() {
        setColor(color);
        double yDiff = y - last_y;
        last_y = y;

        offset += yDiff;

        if(offset >= BRICK_HEIGHT) {
            offset = offset-BRICK_HEIGHT;
            shiftBricks();
        }
    }

    private void shiftBricks() {
        int topBrick = bricks.get(0);
        bricks.remove(0);
        bricks.add(topBrick);
    }

    private void loadBitmaps() {
        brickTypes[BRICK_NO_WINDOW] = Image2D.scaleBitmap(Image2D.getBitmap(R.raw.brick_0, res, false), gameView.ratio);
        brickTypes[BRICK_LEFT_WINDOW] = Image2D.scaleBitmap(Image2D.getBitmap(R.raw.brick_2, res, false), gameView.ratio);
        brickTypes[BRICK_RIGHT_WINDOW] = Image2D.scaleBitmap(Image2D.getBitmap(R.raw.brick_1, res, false), gameView.ratio);

        gradient_img = Image2D.scaleBitmap(Image2D.getBitmap(R.raw.transitionimg, res, false), gameView.ratio);

        gradient = new Gradient2D(gameView.width/2, (int) (gameView.height*0.60f), gameView.width/2, (int) 0, 0xffbb8cbf, 0x00f79266, Gradient2D.GRADIENT_LINEAR);
        gradient.setBounds(gameView.width, gameView.height);

        BRICK_HEIGHT = brickTypes[BRICK_NO_WINDOW].getHeight();
    }

    public void setColor(int c) {
        color = c;
        p.setColor(color);
    }

    public void setGameY(double y) {
        this.y = y;
    }

    private void drawBrick(int index, Canvas c) {
        int type = bricks.get(index);
        Bitmap brick = brickTypes[type];
        double x = 0;
        double y = (index * BRICK_HEIGHT) - offset;

        if(y > height) return;

        drawBrickBackground(type, (int) x, (int) y, c);
        c.drawBitmap(brick, (int) x, (int) y, null);
    }

    private void drawBrickBackground(int type, int x, int y, Canvas c) {

        switch(type) {
            case(BRICK_NO_WINDOW):
                c.drawRect(x, y, width, y + BRICK_HEIGHT, p);
                break;
            case(BRICK_LEFT_WINDOW):
                c.drawRect(0, y, BRICK_LEFT_WINDOW_VALUES[0], y + BRICK_HEIGHT, p);
                c.drawRect(BRICK_LEFT_WINDOW_VALUES[1], y, width, y + BRICK_HEIGHT, p);
                break;
            case(BRICK_RIGHT_WINDOW):
                c.drawRect(0, y, BRICK_RIGHT_WINDOW_VALUES[0], y + BRICK_HEIGHT, p);
                c.drawRect(BRICK_RIGHT_WINDOW_VALUES[1], y, width, y + BRICK_HEIGHT, p);
                break;
        }
    }

    public void setLevel(int level) {
        if(level > colors.size()) {
            level = (int) (Math.random() * colors.size()) + 1;
        }

        setColor( colors.get(level - 1) );
    }

    public int getColor() {
        return color;
    }
    
}
