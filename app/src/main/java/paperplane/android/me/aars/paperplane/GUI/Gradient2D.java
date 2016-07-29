package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;

/**
 * Created by Kristian Aars on 19.07.2016.
 */
public class Gradient2D extends Component2D {

    public static final int GRADIENT_LINEAR = 0;

    private int type;

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    private int startColor;
    private int endColor;

    private Paint paint;

    public Gradient2D(int startX, int startY, int endX, int endY, int startColor, int endColor, int type) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startColor = startColor;
        this.endColor = endColor;
        this.type = type;

        paint = new Paint();

        createGradient();
    }

    private void createGradient() {
        if(type == GRADIENT_LINEAR) createLinearGradient();
    }

    public void setColors(int startColor, int endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        createGradient();
    }

    private void createLinearGradient() {
        LinearGradient g = new LinearGradient(startX, startY, endX, endY, startColor, endColor, Shader.TileMode.CLAMP);
        paint.setShader(g);
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(x, y, x + width, y + height, paint);
    }
}
