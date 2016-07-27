package paperplane.android.me.aars.paperplane;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by Kristian Aars on 18.08.2015.
 */
public class GameLoop extends Thread {

    private SurfaceHolder surfaceHolder;

    private GameView view;

    private boolean isRunning;

    private long period;

    public long tick = 0;
    public int fps = 0;

    public GameLoop(GameView v, SurfaceHolder s, int period) {
        this.period = period;
        view = v;
        surfaceHolder = s;
    }

    public void start() {
        if(isRunning) return;
        isRunning = true;
        super.start();
    }

    public void cancel() {
        if(!isRunning) return;
        isRunning = false;
    }

    @Override
    public void run() {
        int frames = 0;
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = period/1000F;
        int tickCount = 0;
        boolean ticked = false;
        Canvas c = null;

        while(isRunning) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime  = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;

            while(unprocessedSeconds > secondsPerTick) {
                view.update();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;
                tickCount++;

                if (tickCount % 60 == 0) {
                    fps = frames;
                    //previousTime += 1000;
                    frames = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            c = null;
            try {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {

                    if(c == null) {
                        cancel();
                        return;
                    }
                    view.render(c);
                    frames++;
                }
            } finally {
                if(c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
