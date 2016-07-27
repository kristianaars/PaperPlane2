package paperplane.android.me.aars.paperplane.GUI;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Movie;

/**
 * Created by Kristian on 18.06.2016.
 */
public class Gif2D extends Component2D {

    private Movie gif;
    private boolean repeat = true;

    private long tick;

    private long lastTime;
    private long elapsedTime;
    private long currentTime;

    public Gif2D(int gifID, Resources res) {
        this(loadGif(gifID, res));
    }

    public Gif2D(Movie gif) {
        x = 300;
        y = 300;
        setGif(gif);

        lastTime = System.currentTimeMillis();
    }

    public void setGif(Movie movie) {
        this.gif = movie;
        setBounds(gif.width(), gif.height());
    }

    public static Movie loadGif(int gifID, Resources res) {
        return Movie.decodeStream(res.openRawResource(gifID));
    }

    public void setRepeat(boolean b) {
        repeat = b;
    }

    @Override
    public void draw(Canvas c) {
        gif.draw(c, x, y);
    }

    @Override
    public void update() {
        currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - lastTime;

        tick += elapsedTime;

        gif.setTime((int) tick);

        if(tick >= gif.duration()) {
            tick = 0;
        }

        lastTime = currentTime;

        super.update();
    }
}
