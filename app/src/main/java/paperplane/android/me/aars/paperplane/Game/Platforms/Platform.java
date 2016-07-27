package paperplane.android.me.aars.paperplane.Game.Platforms;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.Game.Plane;
import paperplane.android.me.aars.paperplane.Game.Powerups.Powerup;
import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 09.10.2015.
 */
public class Platform extends Component2D {

    public static final int NUMBER_OF_PLATFORMS = 5;
    public static final int PLATFORM_LEFT = 0;
    public static final int PLATFORM_MIDDLE = 1;
    public static final int PLATFORM_MOVING = 2;
    public static final int PLATFORM_RIGHT = 3;
    public static final int PLATFORM_CRUSHER = 4;

    protected GameView gameView;

    protected static final int TARGET_RADIUS = 25;
    protected static final int TARGET_STROKE = 10;
    protected static final int TARGET_OFFSET = TARGET_RADIUS;
    protected static final int TARGET_MAX_HEIGHT = 121;
    protected static final int TARGET_MIN_HEIGHT = 120;

    protected static int RADIUS = -1;
    protected static int STROKE = -1;
    protected static int OFFSET = -1;
    protected static int MAX_HEIGHT = -1;
    protected static int MIN_HEIGHT = -1;


    public static final int REQUIRED_LEVEL = 0;

    protected ArrayList<Rectangle> platforms;
    private Paint paint;

    private Powerup powerup = null;
    protected int POWERUP_X;

    //If plane has passed the platform
    private boolean passed = false;

    public Platform(GameView g) {
        gameView = g;
        platforms = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE);
    }

    public void setPosition(double x, double y) {
        for(Rectangle r : platforms)
            r.setPosition(x, y);

        if(hasPowerup()) {
            powerup.setPosition((int) POWERUP_X, (int) y);
        }
    }

    @Override
    public void draw(Canvas c) {
        for(Rectangle r : platforms) {
            c.drawRoundRect((int) r.getX(), (int) r.getY(), (int) (r.getX()+ r.getWidth()), (int) (r.getY()+r.getHeight()), RADIUS, RADIUS, paint);
        }

        if(hasPowerup()) {
            powerup.draw(c);
        }
    }

    @Override
    public void update() {
        super.update();
        if(hasPowerup())
            powerup.update();
    }

    private boolean hasPowerup() {
        if(powerup != null) return true;
        else return false;
    }

    public boolean hitByPlane(Plane plane) {
        if(hasPowerup()) {
            if(plane.interacts(powerup) && powerup.isVisible()) {
                powerup.enablePowerup();
            }
        }

        for(Rectangle r:platforms) {
            if(plane.interacts(r)) return true;
        }

        return false;
    }

     public void setHeight(int h) {
         for(Rectangle r:platforms)
             r.setHeight(h);
     }

    public double getX() {
        return platforms.get(0).getX();
    }

    public double getY() {
        return platforms.get(0).getY();
    }

    public void setY(double y) {
        setPosition(x, y);
    }

    public void setX(int x) {
        setPosition(x - OFFSET, y);
    }

    public double getHeight() {
        return platforms.get(0).getHeight();
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean b) {
        passed = b;
    }

    public void setPowerup(Powerup power) {
        powerup = power;
    }
}