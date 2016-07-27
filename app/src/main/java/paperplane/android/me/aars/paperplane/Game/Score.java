package paperplane.android.me.aars.paperplane.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import paperplane.android.me.aars.paperplane.GUI.Component2D;
import paperplane.android.me.aars.paperplane.GUI.Position;
import paperplane.android.me.aars.paperplane.GameState.StateGame;
import paperplane.android.me.aars.paperplane.GameView;

/**
 * Created by Kristian on 20.03.2016.
 */
public class Score extends Component2D {

    private GameView gameView;

    private static final int FONT_SIZE = 130;
    private static final int FONT_COLOR = Color.WHITE;
    private Paint paint;

    private String text = "0";

    public Score(GameView gameView) {
        this.gameView = gameView;

        configureStyle();
    }

    private void configureStyle() {
        paint = new Paint();
        paint.setTextSize(FONT_SIZE);
        paint.setColor(FONT_COLOR);
    }

    public void setScore(int score) {
        text = "" + score;

        Rect textBounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), textBounds);

        x = Position.center(gameView.width, textBounds.width());
        y = (int) (150 * gameView.ratio);
    }

    public void draw(Canvas c) {
        c.drawText(text, x, y, paint);
    }
}
