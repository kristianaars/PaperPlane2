package paperplane.android.me.aars.paperplane.Utilities;

/**
 * Created by Kristian Aars on 14.10.2015.
 */
public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        setPosition(x, y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        setPosition(x, y);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("x: %s, y: %s", x, y);
    }
}
