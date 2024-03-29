package paperplane.android.me.aars.paperplane.Game.Platforms;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 09.10.2015.
 */
public class Platform_Middle  extends Platform {

    private int WIDTH = 440;
    private int HEIGHT = 110;

    public Platform_Middle(GameView g) {
        super(g);

        WIDTH = (int) (WIDTH * gameView.ratio);
        HEIGHT = (int) (HEIGHT * gameView.ratio);

        POWERUP_X = (int) (490 * gameView.ratio);

        Rectangle platform0 = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);
        Rectangle platform1 = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);

        platforms.add(platform1);
        platforms.add(platform0);
    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        platforms.get(0).setPosition(x - OFFSET, y);
        platforms.get(1).setPosition(x + gameView.getWidth() - platforms.get(1).getWidth() + OFFSET, y);
    }
}
