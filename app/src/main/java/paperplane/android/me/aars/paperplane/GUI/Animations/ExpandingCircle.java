package paperplane.android.me.aars.paperplane.GUI.Animations;

import android.graphics.Canvas;
import android.graphics.Paint;

import paperplane.android.me.aars.paperplane.GUI.Position;

/**
 * Created by Kristian on 05.04.2016.
 */

public class ExpandingCircle extends Animation {

    private static final float SPEED = 0.18F;

    private int target_width;
    private int target_height;
    private int color;
    private Paint paint;

    private float precentage;

    public ExpandingCircle(int color, int width, int height) {
        this.color = color;
        target_width = width;
        target_height = height;

        paint = new Paint();
        paint.setColor(color);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public void start() {
        super.start();

        precentage = 0;
    }

    public void update() {
        if(!running) return;

        precentage += SPEED;

        width = (int) (target_width * precentage);
        height = (int) (target_width * precentage);

        x = Position.center(target_width, width);
        y = Position.center(target_height, height);

        if(precentage >= 1.5F) {
            stop();
        }
    }

    public void draw(Canvas c) {
        c.drawOval(x, y, x + width, y + height, paint);
    }
}
