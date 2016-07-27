package paperplane.android.me.aars.paperplane.Utilities;

/**
 * Created by Kristian Aars on 18.03.2015.
 */
public class Rectangle {

    public double x;
    public double y;
    public double width;
    public double height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(int width, int height) {
        this(0, 0, width, height);
    }

    public boolean interacts(Rectangle r) {
        double x2 = r.getX();
        double y2 = r.getY();
        double width2 = r.getWidth();
        double height2 = r.getHeight();

        if((width == 1 && height == 1) && (width2 == 1 && height2 == 1)) {
            if(x == x2 && y == y2) return true;
            else return false;
        }

        if((x <= x2 + width2) && (x2 <= x + width) && (y <= y2) && (y2 <= y + height)) {
            return true;
        } else if(x2<=x+width && x<=x2+width2 && y2<=y && y<=y2+height2) {
            return true;
        }

        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setY(double y) {
        setPosition(this.x, y);
    }

    public void setX(double x) {
        setPosition(x, this.y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("X:%s Y:%s Width:%s Height:%s", x, y, width, height);
    }
}

