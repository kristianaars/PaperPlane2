package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Canvas;
import android.graphics.Point;

import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian Aars on 21.08.2015.
 */
public class Component2D {

    protected boolean isVisible = true;
    protected boolean isEnabled = true;

    public int x = 0;
    public int y = 0;

    public Rectangle bounds = new Rectangle(0, 0);
    public int width;
    public int height;

    //Meant to be overwritten!! START
    public boolean isHit(int x, int y, int type, int button){return false;}
    public void draw(Canvas c) {}
    public void update() {}
    //END

    public void setVisible(boolean b) {
        isVisible = b;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setEnabled(boolean b) {
        isEnabled = b;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(int width, int height) {
        bounds.setWidth(width);
        bounds.setHeight(height);

        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        setPosition(x, y);
    }

    public void setY(int y){
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        bounds.setPosition(x, y);
        this.x = x;
        this.y = y;
    }

    public boolean interacts(Rectangle r) {
        if(!isVisible) return false;
        return r.interacts(bounds);
    }

    public boolean interacts(Component2D c) {
        Rectangle cBounds = c.getBounds();
        return interacts(cBounds);
    }
}