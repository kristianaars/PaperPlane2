package paperplane.android.me.aars.paperplane.Game.Platforms;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 09.10.2015.
 */
public class Platform_Moving extends Platform {

    public int WIDTH = 300;
    public int HEIGHT = 110;

    private static final double xSpeed = 0.05;

    private double tick;
    private double x = 0;

    public static final int REQUIRED_LEVEL = 2;

    public Platform_Moving(GameView g) {
        super(g);

        WIDTH = (int) (WIDTH * gameView.ratio);
        HEIGHT = (int) (HEIGHT * gameView.ratio);

        POWERUP_X = (int) (490 * gameView.ratio);

        Rectangle platform = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);
        platforms.add(platform);

        tick = Math.random() * 10;
    }

    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        platforms.get(0).setPosition(this.x, y);
    }

    @Override
    public void update() {
        super.update();
        tick += xSpeed;

        x = ((Math.sin(tick)+1)/2) * (gameView.getWidth() - platforms.get(0).getWidth());
    }
}
