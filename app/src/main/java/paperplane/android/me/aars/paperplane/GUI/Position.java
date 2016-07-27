package paperplane.android.me.aars.paperplane.GUI;

/**
 * Created by Kristian on 02.03.2016.
 */
public class Position {

    public static int center(int parent, int child) {
        return (parent - child)/2;
    }

    public static int allignBottom(int parentHeight, int childHeight) {
        return parentHeight - childHeight;
    }
}
