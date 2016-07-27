package paperplane.android.me.aars.paperplane.Game.Powerups;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Movie;

import paperplane.android.me.aars.paperplane.GUI.Gif2D;
import paperplane.android.me.aars.paperplane.GameState.StateGame;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian on 18.06.2016.
 */
public class Coin extends Powerup {

    private static Movie coinAnimation;

    private StateGame game;
    private Gif2D gif;

    public Coin(Resources res, StateGame game) {
        this.game = game;

        if(coinAnimation == null) {
            coinAnimation = Gif2D.loadGif(R.drawable.coin, res);
        }

        gif = new Gif2D(coinAnimation);

        setBounds(gif.width, gif.height);
    }

    @Override
    public void enablePowerup() {
        super.enablePowerup();

        game.addCoins(1);
    }

    @Override
    public void draw(Canvas c) {
        if(!isVisible) return;
        gif.draw(c);
    }

    @Override
    public void update() {
        if(!isVisible) return;
        gif.update();

    }

    @Override
    public void setX(int x) {
        super.setX(x);
        gif.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        gif.setY(y);
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        gif.setPosition(x, y);
    }
}