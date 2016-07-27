package paperplane.android.me.aars.paperplane.Game.Platforms;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 09.10.2015.
 */
public class Platform_Left  extends Platform {

    private int WIDTH = 690;
    private int HEIGHT = 110;

    public Platform_Left(GameView g) {
        super(g);

        WIDTH = (int) (WIDTH * gameView.ratio);
        HEIGHT = (int) (HEIGHT * gameView.ratio);

        POWERUP_X = (int) (800 * gameView.ratio);

        Rectangle platform = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);
        platforms.add(platform);

    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        platforms.get(0).setPosition(x - OFFSET, y);
    }
}