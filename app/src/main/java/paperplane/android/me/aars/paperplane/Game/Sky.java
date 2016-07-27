package paperplane.android.me.aars.paperplane.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.GUI.Gradient2D;
import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian Aars on 03.09.2015.
 */
public class Sky extends Component2D {
    private double y;
    private double lastYReset = 0;
    private double offset;

    private GameView gameView;

    private int width = 0;
    private int height = 0;

    private int SKY_HEIGHT;
    private int SKY_COLOR =  0xfff79267; //A5D9D7;

    private static Bitmap SKY_BITMAP;
    private Gradient2D gradient;

    public Sky(GameView v) {
        gameView = v;

        loadBitmap();
        loadGradient();

        width = v.width;
        height = v.height;

        b = (height/SKY_HEIGHT) + 1;
    }

    Paint p = new Paint();

    private int tmpY = 0;
    private int b = 0;

    @Override
    public void draw(Canvas c) {
        p.setColor(SKY_COLOR);
        c.drawRect(0, 0, gameView.width, gameView.height, p);

        tmpY = (int) offset;

        for(int i = 0; i < b; i++) {
            c.drawBitmap(SKY_BITMAP, x, tmpY, p);
            c.drawBitmap(SKY_BITMAP, x - SKY_BITMAP.getWidth(), tmpY, p);
            tmpY += SKY_HEIGHT;
        }

        gradient.draw(c);
    }

    @Override
    public void update() {
        int yDiff = (int) (y - lastYReset);

        if(yDiff > SKY_HEIGHT) {
            lastYReset = y;
        }

        offset = (int) (y - lastYReset) * -1;

        x += 1;

        if(x >= SKY_BITMAP.getWidth()) {
            x = 0;
        }
    }

    private void loadBitmap() {
        SKY_BITMAP = Image2D.getBitmap(R.raw.sky, gameView.getResources());
        SKY_BITMAP = Image2D.scaleBitmap(SKY_BITMAP, gameView.ratio);

        SKY_HEIGHT = SKY_BITMAP.getHeight();
    }

    private void loadGradient() {
        gradient = new Gradient2D(gameView.width/2, (int) (gameView.height*0.60f), gameView.width/2, (int) 0, 0xffbb8cbf, 0x00f79266, Gradient2D.GRADIENT_LINEAR);
        gradient.setBounds(gameView.width, gameView.height);
    }

    public void setGameY(double y) {
        this.y = y;
    }
}
