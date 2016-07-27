package paperplane.android.me.aars.paperplane.Game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.GUI.Line2D;
import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.R;
import paperplane.android.me.aars.paperplane.Utilities.Figure;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian Aars on 20.09.2015.
 */
public class Plane extends Component2D {

    private static final int HITBOX_DETAIL_LEVEL = 6;
    private GameView gameView;

    private Figure hitbox;

    private float rotation;
    private float buffer_rotation;

    private int x_pressed = -1;

    private Image2D plane_image;
    private Image2D shield_image;

    private float width = 300;
    private float height = 300;

    private int C_WIDTH;
    private int C_HEIGHT;

    private float xSpeed;
    private float ySpeed;
    private float speed;
    private float rotation_speed;

    private static final float MAX_ROTATION = 80;
    private static final float MIN_ROTATION = -80;

    private boolean shield;
    private long shieldDisabled = 0;
    private long shieldDisableDuration = 0;

    public Plane(GameView v) {
        gameView = v;
        
        loadImages();
        loadHitbox();

        width *= v.ratio;
        height *= v.ratio;

        C_WIDTH = v.width;
        C_HEIGHT = v.height;

        y = (int) (650 * v.ratio);

        centerPlane();
    }

    private void centerPlane() {
        x = (int) ((gameView.width - plane_image.width) / 2) + plane_image.width/2;
    }

    private void loadHitbox() {
        hitbox = new Figure(x, y, plane_image.width, plane_image.height);

        hitbox.setPointOfDetail(plane_image.width / HITBOX_DETAIL_LEVEL);
    }

    private void loadImages() {
        Resources res = gameView.getResources();

        plane_image = new Image2D(R.raw.plane_straight, res);
        shield_image = new Image2D(R.raw.shield, res);

        plane_image.scaleBitmap(gameView.ratio);
        shield_image.scaleBitmap(gameView.ratio);

        shield_image.setVisible(false);
    }

    @Override
    public void draw(Canvas c) {
        if(!isVisible) return;

        plane_image.draw(c);
        shield_image.draw(c);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    private void setSpeed(float speed, float rot) {
        xSpeed = speed * (rot/100F);
        ySpeed = speed - Math.abs(xSpeed);
        rotation_speed = speed/4 + (40/speed);
    }

    private void setRotation(float r) {
        if(r > MAX_ROTATION) r = MAX_ROTATION;
        if(r < MIN_ROTATION) r = MIN_ROTATION;
        else if(r == MAX_ROTATION || r == MIN_ROTATION) return;

        rotation = r;

        setSpeed(speed, r);

        plane_image.rotateImage(width / 2, height / 2, r * -1F);
        plane_image.setPosition(x - (plane_image.width / 2), y);
    }

    private void updateHitbox() {
        hitbox.setBounds(plane_image.width, plane_image.height);
        hitbox.setPosition(x - (plane_image.width / 2), y);

        int tmp_px;

        for(int y = 0; y < plane_image.height; y += hitbox.getPointOfDetail()) {

            for(int x = 0; x < plane_image.width; x += hitbox.getPointOfDetail()) {

                tmp_px = plane_image.getPixel(x, y);

                if(tmp_px != 0) {
                    hitbox.setBlock(x, y, Figure.BLOCK_FILLED);
                }
            }
        }
    }

    public float getXSpeed() {
        return xSpeed;
    }

    public float getYSpeed() {
        return ySpeed;
    }

    public void update() {
        if(x_pressed != -1) {

            if (x_pressed < C_WIDTH / 2) {
                buffer_rotation = MIN_ROTATION;
            } else {
                buffer_rotation = MAX_ROTATION;
            }
        } else {
           buffer_rotation = 0;
        }

        if(rotation != buffer_rotation) {
            if(rotation < buffer_rotation) {
                rotation += rotation_speed;
                if(rotation > buffer_rotation) rotation = buffer_rotation;
            } else if(rotation > buffer_rotation) {
                rotation -= rotation_speed;
                if(rotation < buffer_rotation) rotation = buffer_rotation;
            }

            setRotation(rotation);
        }

        updateHitbox();

        setX((int) (x + (xSpeed * gameView.ratio)));
    }

    public void setX(int x) {
        if(x < 0) x = 0;
        if(x > gameView.getWidth()) x = gameView.getWidth();
        this.x = x;

        plane_image.setPosition(x - (plane_image.width / 2), y);

        if(shield) {
            int dx = shield_image.width - plane_image.width;
            int dy = shield_image.height - plane_image.height;
            shield_image.setPosition(plane_image.x - (dx / 2), plane_image.y - (dy / 2));
        } else {
            shieldDisableDuration = (System.currentTimeMillis() - shieldDisabled);
            if(shieldDisableDuration < 500) {
                int dx = shield_image.width - plane_image.width;
                int dy = shield_image.height - plane_image.height;
                shield_image.setPosition(plane_image.x - (dx / 2), plane_image.y - (dy / 2));
                double f = Math.sin(System.currentTimeMillis()/4);
                if(f < 0) shield_image.setVisible(true);
                else shield_image.setVisible(false);
            } else {
                shield_image.setVisible(false);
            }
        }

        //hitbox1.setPosition(x, (int) (y + ((2 / width * width) / gameView.ratio)));
        //hitbox2.setPosition(x, (int) (y + ((width / 3) / gameView.ratio)));

        //updateHitbox();
    }

    public boolean interacts(Rectangle r) {
        if(hitbox.interacts(r)) return true;
        return false;
    }

    public void enableShield() {
        shield = true;
        shield_image.setVisible(true);
        System.out.println("ENABLELING SHIELD");
    }

    public void disableShield() {
        shield = false;
        shieldDisabled = System.currentTimeMillis();
        shield_image.setVisible(false);
        System.out.println("DISABLELING SHIELD");
    }

    public boolean hasShield() {
        return shield;
    }

    private boolean multiTouch = false;

    @Override
    public boolean isHit(int x, int y, int type, int button) {
        //Log.i("TouchEvent", "Button: " + button + " X: " + x + " X_PRESSED: " + x_pressed);

        /*
            ACTION_UP = Finger treffer skjerm
        */

        if(button == MotionEvent.ACTION_UP || button == MotionEvent.ACTION_POINTER_1_UP) {
            if(button == MotionEvent.ACTION_POINTER_1_UP) {
                multiTouch = true;
            }

            x_pressed = x;
        } else if(button == MotionEvent.ACTION_DOWN || button == MotionEvent.ACTION_POINTER_1_DOWN){
            if(button == MotionEvent.ACTION_POINTER_1_DOWN) {
                multiTouch = false;
            }

            x_pressed = -1;
        }
        return false;
    }
}