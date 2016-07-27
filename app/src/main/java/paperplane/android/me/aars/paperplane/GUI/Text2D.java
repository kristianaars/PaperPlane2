package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by Kristian on 06.04.2016.
 */
public class Text2D extends Component2D {

    private String text;
    private int color;

    private Paint paint;
    private int font_size = 15;

    public Text2D(String text) {
        paint = new Paint();

        setText(text);
        setFontSize(font_size);
    }

    public void setText(String text) {
        this.text = text;
        updateVariables();
    }

    public void setFontSize(int f) {
        this.font_size = f;
        paint.setTextSize(f);
        updateVariables();
    }

    private void updateVariables() {
        int w = (int) paint.measureText(text);
        int h = font_size;

        setBounds(w, h);
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public void draw(Canvas c) {
        c.drawText(text, x, y, paint);
    }

    public void setFont(Typeface typeFace) {
        paint.setTypeface(typeFace);
    }
}
