package paperplane.android.me.aars.paperplane;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import paperplane.android.me.aars.paperplane.Managers.GameStateManager;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop thread;
    private GameStateManager gameStateManager;
    private GameActivity gameActivity;

    public static int TOUCH_RELEASD = 0;
    public static int TOUCH_PRESSED = 1;

    public final int width;
    public final int height;

    public final float ratio;

    public GameView(GameActivity gameActivity, int width, int height) {
        super(gameActivity);

        this. gameActivity = gameActivity;

        this.width = width;
        this.height = height;

        ratio = (float) width / (float) 1080;

        getHolder().addCallback(this);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setFocusable(true);

        p.setColor(Color.YELLOW);
        p.setTextSize(60 * ratio);

        gameStateManager = new GameStateManager(this);

        System.out.println("StateGame: Ratio = " + ratio);
    }

    Paint p = new Paint();

    public void render(Canvas c) {
        gameStateManager.draw(c);

        //c.drawText("PAPER PLANE ALPHA V 5    Fps: " + thread.fps, 0, 60, p);
    }
    public void update() {
        gameStateManager.update();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameLoop(this, getHolder(), 16);
        thread.start();

    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.cancel();
    }

    public void stop() {
        thread.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX(event.getPointerCount() - 1);
        int touchY = (int) event.getY(event.getPointerCount() - 1);
        int mode = event.getAction();

        if(mode == MotionEvent.ACTION_DOWN || mode == MotionEvent.ACTION_MOVE) mode = TOUCH_PRESSED;
        else if(mode == 1) mode = TOUCH_RELEASD;

        gameStateManager.isHit(touchX, touchY, 0, mode);
        return true;
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

}
