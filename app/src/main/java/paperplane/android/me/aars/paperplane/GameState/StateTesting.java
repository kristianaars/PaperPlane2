package paperplane.android.me.aars.paperplane.GameState;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.provider.Settings;

import paperplane.android.me.aars.paperplane.Managers.GameStateManager;

/**
 * Created by Kristian Aars on 19.07.2016.
 */
public class StateTesting extends GameState {

    private GradientDrawable t;
    private Paint p;

    public StateTesting(GameStateManager gameStateManager) {
        super(gameStateManager);

        LinearGradient r = new LinearGradient(0, 0, 0, gameView.height, 0x00ffffff, Color.BLUE, Shader.TileMode.CLAMP);

        p = new Paint();
        p.setDither(true);
        p.setShader(r);
    }

    @Override
    public void draw(Canvas c) {
        c.drawRect(0, 0, width, height, p);
    }
}
