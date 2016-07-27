package paperplane.android.me.aars.paperplane.GameState;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import paperplane.android.me.aars.paperplane.GUI.Animations.Animation;
import paperplane.android.me.aars.paperplane.GUI.Animations.AnimationChain;
import paperplane.android.me.aars.paperplane.GUI.Animations.ExpandingCircle;
import paperplane.android.me.aars.paperplane.GUI.Animations.ScreenFlash;
import paperplane.android.me.aars.paperplane.Game.*;
import paperplane.android.me.aars.paperplane.Game.Platforms.Platforms;
import paperplane.android.me.aars.paperplane.Managers.GameStateManager;

/**
 * Created by Kristian on 15.09.2015.
 */

public class StateGame extends GameState {
    private Sky sky;
    private Bricks bricks;
    private Plane plane;
    private Platforms platforms;
    private Score score_view;

    private AnimationChain traansitionAnimation;
    private ScreenFlash flashAnimation;
    private ExpandingCircle circleAnimation;

    private static final int BASE_LEVEL_SIZE = 15000;
    private final int LEVEL_SIZE;
    private int level;
    private int score;
    private int collectedCoins;
    private double y = 0;

    private static final int BASE_SPEED = 12;
    private static final int MAX_LEVEL = 6;

    private boolean gameOver = false;

    private int[] values = new int[8];

    public StateGame(GameStateManager stateManager) {
        super(stateManager);
        setBounds(gameView.width, gameView.height);

        LEVEL_SIZE = (int) (BASE_LEVEL_SIZE * stateManager.getGameView().ratio);

        restart();
    }

    public void update() {
        long start = 0;
        float ySpeed = plane.getYSpeed() * gameView.ratio;

        if(gameOver) {
            if(!traansitionAnimation.isRunning()) stateManager.gameStateGameOver(score, collectedCoins, bricks.getColor());

            ySpeed = 0;
        }

        y += (double) ySpeed;

        bricks.setGameY(y);
        platforms.setGameY(y);
        sky.setGameY(y / 4);

        int at_lvl_up = LEVEL_SIZE * level;
        if(y >= at_lvl_up) {
            setLevel(level + 1);
        }

        super.update();

    }

    private void loadComponents() {
        sky = new Sky(gameView);
        bricks = new Bricks(gameView);
        plane = new Plane(gameView);
        platforms = new Platforms(gameView, this, plane);
        score_view = new Score(gameView);

        flashAnimation = new ScreenFlash(Color.WHITE);
        circleAnimation = new ExpandingCircle(0, width, height);

        traansitionAnimation = new AnimationChain(new Animation[]{flashAnimation, circleAnimation});
    }

    private void addComponents() {
        add(sky);
        //add(bricks);

        add(platforms);
        add(plane);
        add(score_view);

        add(traansitionAnimation);
    }

    Paint p = new Paint();

    public void draw(Canvas c) {
        if(components.size()<=0) return;
        if(!isVisible) return;
        for(int i = 0; i<components.size(); i++) {
            //start = System.currentTimeMillis();
            components.get(i).draw(c);
            //values[i] = (int) (System.currentTimeMillis() - start);
        }

        p.setColor(Color.BLUE);
        c.drawText(level+"", 100, 100, p);
    }

    public void restart() {
        removeAllComponents();
        loadComponents();
        addComponents();
        setLevel(1);
        y = 0;
        setScore(0);
        gameOver = false;
    }

    public void setLevel(int l) {
        level = l;

        if(level < MAX_LEVEL) {
            plane.setSpeed(BASE_SPEED + (level * 1.7F));
        }

        bricks.setLevel(level);
        platforms.setLevel(level);
    }

    public int getScore() { return score; }

    public void setScore(int score) {
        score_view.setScore(score);
        this.score = score;
    }

    //Temporary method
    public void returnToMainMenu() {
        stateManager.gameStateMenu();
    }

    public void setGameOver() {
        if(gameOver) return;

        plane.setVisible(false);

        circleAnimation.setColor(bricks.getColor());
        traansitionAnimation.start();

        gameOver = true;
    }

    public void addCoins(int i) {
        collectedCoins += i;
    }
}
