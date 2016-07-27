package paperplane.android.me.aars.paperplane.GUI.Animations;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kristian on 06.04.2016.
 */
public class FadeoutBox  extends Animation {

    private static final int SPEED = 8;

    private int red;
    private int green;
    private int blue;
    private int alpha;

    private int color;

    private Paint paint;

    public FadeoutBox(int color, int width, int height) {
        setBounds(width, height);

        paint = new Paint();
        setColor(color);
    }

    public void start() {
        super.start();

        alpha = 255;
    }

    public void update() {
        if(!running) return;

        alpha = paint.getAlpha() - SPEED;

        if(alpha < 0) {
            stop();
            alpha = 0;
        }

        paint.setAlpha(alpha);
    }


    public void draw(Canvas c) {
        if(!running) return;

        c.drawRect(x, y, x + width, y + height, paint);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public int getColor() {
        return color;
    }
}
