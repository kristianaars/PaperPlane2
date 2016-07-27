package paperplane.android.me.aars.paperplane.Game.Platforms;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.Game.Plane;
import paperplane.android.me.aars.paperplane.Game.Powerups.Coin;
import paperplane.android.me.aars.paperplane.Game.Powerups.Powerup;
import paperplane.android.me.aars.paperplane.Game.Powerups.Shield;
import paperplane.android.me.aars.paperplane.GameState.StateGame;

/**
 * Created by Kristian on 28.10.2015.
 */

public class Platforms extends Component2D {

    private paperplane.android.me.aars.paperplane.GameView gameView;
    private StateGame game;
    private Plane plane;

    private ArrayList<Platform> platforms;

    private Random random;

    private int spaceBetweenPlatforms = 550;

    private int START_Y_POS = 1800;

    private double game_y;
    private double last_y;

    int powerupCount = 0;
    int POWERUP_INTERVAL = 2;

    private int game_level;

    private int lastPlatform = -1;
    private double randomPlatformHeight;

    public Platforms(paperplane.android.me.aars.paperplane.GameView gv, StateGame g, Plane p) {
        gameView = gv;
        game = g;
        plane = p;

        Platform.RADIUS = (int) (Platform.TARGET_RADIUS * gv.ratio);
        Platform.STROKE = (int) (Platform.TARGET_STROKE * gv.ratio);
        Platform.OFFSET = (int) (Platform.TARGET_OFFSET * gv.ratio);
        Platform.MAX_HEIGHT = (int) (Platform.TARGET_MAX_HEIGHT * gv.ratio);
        Platform.MIN_HEIGHT = (int) (Platform.TARGET_MIN_HEIGHT * gv.ratio);

        spaceBetweenPlatforms *= gameView.ratio;
        START_Y_POS *= gameView.ratio;

        random = new Random();
        platforms = new ArrayList<Platform>();

        for(int i = 0; i<10; i++)
            spawnNewPlatform();
    }

    private void spawnNewPlatform() {
        if(platforms.size() >= 10)
            platforms.remove(0);

        int platformType = lastPlatform;

        while(platformType==lastPlatform)
            platformType = random.nextInt(Platform.NUMBER_OF_PLATFORMS);

        //platformType = Platform.PLATFORM_CRUSHER;
        //game_level = 10;

        Platform platform = null;

        switch(platformType) {
            case(Platform.PLATFORM_LEFT):
                if(game_level < Platform_Left.REQUIRED_LEVEL) {spawnNewPlatform(); return;}
                platform = new Platform_Left(gameView);
                break;
            case(Platform.PLATFORM_MIDDLE):
                if(game_level < Platform_Middle.REQUIRED_LEVEL) {spawnNewPlatform(); return;}
                platform = new Platform_Middle(gameView);
                break;
            case(Platform.PLATFORM_MOVING):
                if(game_level < Platform_Moving.REQUIRED_LEVEL) {spawnNewPlatform(); return;}
                platform = new Platform_Moving(gameView);
                break;
            case(Platform.PLATFORM_RIGHT):
                if(game_level < Platform_Right.REQUIRED_LEVEL) {spawnNewPlatform(); return;}
                platform = new Platform_Right(gameView);
                break;
            case(Platform.PLATFORM_CRUSHER):
                if(game_level < Platform_Crusher.REQUIRED_LEVEL) {spawnNewPlatform(); return;}
                platform = new Platform_Crusher(gameView);
                break;
        }

        lastPlatform = platformType;

        double y = getNewY();
        int height = getRandomPlatformHeight();

        platform.setPosition(0, y);
        platform.setHeight(height);
        platform.setPowerup(spawnNewPowerup());
        platforms.add(platform);
    }

    private Powerup spawnNewPowerup() {
        if(powerupCount < POWERUP_INTERVAL) {
            powerupCount++;
            return null;
        }

        powerupCount = 0;

        int rnd = random.nextInt(15);

        if(rnd == 5) return new Shield(gameView.getResources(), plane);
        else return new Coin(gameView.getResources(), game);
    }

    private double getNewY() {
        int bufferSize = platforms.size();

        if(bufferSize == 0)
            return START_Y_POS;
        else {
            int index = bufferSize - 1;
            double lastY = platforms.get(index).getY();
            return lastY + spaceBetweenPlatforms;
        }
    }

    @Override
    public void draw(Canvas c) {
        for(Platform p : platforms)
            p.draw(c);
    }

    public void setGameY(double i) {
        game_y = i;
    }

    public void update() {
        Platform firstPlatform = platforms.get(0);
        double y_diff = game_y - last_y;
        last_y = game_y;

        if(firstPlatform.getY() <= 0-firstPlatform.getHeight()) {
            spawnNewPlatform();
        }

        for(Platform p : platforms) {
            if(!p.isPassed()) {

                if (p.getY() <= plane.y) {
                    //Increasews score when platform is passed
                    p.setPassed(true);
                    game.setScore(game.getScore() + 1);
                }

                if (p.hitByPlane(plane)) {

                    if(plane.hasShield()) {
                        p.setPassed(true);
                        plane.disableShield();
                    } else {
                        game.setGameOver();
                    }


                }
            }

            p.setY(p.getY() - y_diff);
            p.update();

        }
    }

    public void setLevel(int level) {
        game_level = level;
    }

    public int getRandomPlatformHeight() {
        return random.nextInt(Platform.MAX_HEIGHT - Platform.MIN_HEIGHT) + Platform.MIN_HEIGHT;
    }
}