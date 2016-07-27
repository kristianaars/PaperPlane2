package paperplane.android.me.aars.paperplane.Game.Powerups;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.Game.Plane;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian on 18.06.2016.
 */
public class Shield extends Powerup {

    private static Bitmap sourceImg = null;

    private Plane plane;
    private Image2D img;

    public Shield(Resources res, Plane plane) {
        this.plane = plane;

        if(sourceImg == null) {
            sourceImg = Image2D.getBitmap(R.drawable.shieldicon, res);
        }

        img = new Image2D(sourceImg);

        setBounds(img.width, img.height);
    }

    @Override
    public void enablePowerup() {
        super.enablePowerup();
        plane.enableShield();
    }

    @Override
    public void draw(Canvas c) {
        if(!isVisible) return;
        img.draw(c);
    }

    @Override
    public void update() {
        if(!isVisible) return;
        img.update();
    }

    @Override
    public void setX(int x) {
        super.setX(x);
        img.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        img.setY(y);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        img.setPosition(x, y);
    }
}