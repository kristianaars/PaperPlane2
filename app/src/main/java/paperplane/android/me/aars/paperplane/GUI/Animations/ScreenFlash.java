package paperplane.android.me.aars.paperplane.GUI.Animations;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kristian on 01.04.2016.
 */
public class ScreenFlash extends Animation {

    private int color;
    private Paint paint;
    private int alpha;

    private float tick = 0;

    private int speed;

    public ScreenFlash(int color) {
        this.color = color;

        paint = new Paint();
        paint.setColor(color);
    }

    public void start() {
        alpha = 255;
        tick = 0;
        speed = 6;

        paint.setColor(color);
        paint.setAlpha(alpha);

        super.start();
    }

    public void draw(Canvas c) {
        if(!running) return;

        c.drawRect(0, 0, c.getWidth(), c.getHeight(), paint);
    }

    public void update() {
        /*  if(!running) return;
        *
        *   speed -= 0.005F;
        *   tick += speed;
        *
        *   alpha = (float) (Math.sin(tick/10) * 255F);
        *
        *   c.println(alpha);
        *
        *   if(alpha <= -250) {
        *       stop();
        *   }
        *   paint.setAlpha((int) alpha);
        */

        if(!running) return;
        
        alpha -= speed;

        if(alpha <= 0) stop();

        paint.setAlpha(alpha);
    }
}
