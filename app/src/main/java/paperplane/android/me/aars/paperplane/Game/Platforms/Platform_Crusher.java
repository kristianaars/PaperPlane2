package paperplane.android.me.aars.paperplane.Game.Platforms;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 03.02.2016.
 */
public class Platform_Crusher  extends Platform {

    private int WIDTH = 540;
    private int HEIGHT = 110;

    private static final float ANIMATION_THRESHOLD = 0.85F;
    private static final float xSpeed = 0.1F;

    public static final int REQUIRED_LEVEL = 3;

    private double tick = 0;
    private int platform_width;

    public Platform_Crusher(GameView g) {
        super(g);

        WIDTH = (int) (WIDTH * gameView.ratio);
        HEIGHT = (int) (HEIGHT * gameView.ratio);

        POWERUP_X = (int) (490 * gameView.ratio);

        Rectangle platform0 = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);
        Rectangle platform1 = new Rectangle(0, 0, WIDTH+STROKE, HEIGHT);

        platforms.add(platform1);
        platforms.add(platform0);

        platform_width = WIDTH;

        tick = Math.random() * 10;
    }

    public void update() {
        super.update();
        tick += xSpeed;

        int x = (int) (((Math.sin(tick)-1)/2)*platform_width);

        int threshold_1 = (int) (ANIMATION_THRESHOLD * platform_width);
        int threshold_2 = (int) ((1F - ANIMATION_THRESHOLD ) * platform_width);

        if(x * -1 < threshold_1 && x * -1 > threshold_2) {
            this.x = x;
        }
    }

    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x, y);
        platforms.get(0).setPosition(x - OFFSET, y);
        platforms.get(1).setPosition(platform_width - x + OFFSET, y);
    }
}
