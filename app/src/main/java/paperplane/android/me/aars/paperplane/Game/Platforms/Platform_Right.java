package paperplane.android.me.aars.paperplane.Game.Platforms;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 09.10.2015.
 */
public class Platform_Right extends Platform {

    private int WIDTH = 690;
    private int HEIGHT = 110;

    public Platform_Right(GameView g) {
        super(g);

        WIDTH = (int) (WIDTH * gameView.ratio);
        HEIGHT = (int) (HEIGHT * gameView.ratio);

        POWERUP_X = (int) (180 * gameView.ratio);

        Rectangle platform = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);
        platforms.add(platform);
    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        Rectangle r = platforms.get(0);
        r.setPosition(gameView.getWidth() - r.getWidth() + OFFSET, y);
    }
}