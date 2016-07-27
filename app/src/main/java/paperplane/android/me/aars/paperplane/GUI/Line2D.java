package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import paperplane.android.me.aars.paperplane.Utilities.Line;
import paperplane.android.me.aars.paperplane.Utilities.Position;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

public class Line2D extends Line {

	public Line orgLine;

	private Paint paint = new Paint();

	private float length;

    private int x;
    private int y;

	private float rotation = 0;
    private int rotationType = 0;

	public Line2D(int length) {
        super();
        x = 0;
        y = 0;
        int x_1 = 0;
        int y_1 = 0;
        int x_2 = 0;
        int y_2 = length;

        this.length = length;

        setLine(x_1, y_1, x_2, y_2);
		orgLine = new Line(x_1, y_1, x_2, y_2);

        paint.setColor(Color.RED);
	}

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        if(rotationType == 0)
            rotateOrgLine(rotation);
        else if(rotationType == 1)
            rotateOrgLineRev(rotation);
    }

	public void rotateOrgLine(float d) {
        rotationType = 0;
        rotation = d;
        double radians = Math.toRadians(rotation);
        double x_1 = orgLine.getStartX();
        double y_1 = orgLine.getStartY();
        double x_2 = orgLine.getEndX();
        double y_2 = orgLine.getEndY();
        double length = orgLine.getLength();

        x_2 = x_1 + (length * Math.sin(radians));
        y_2 = y_1 + (length * Math.cos(radians));

        setLine((float) x_1 + x, (float) y_1 + y, (float) x_2 + x, (float) y_2 + y);
    }

    public void rotateOrgLineRev(float d) {
        rotationType = 1;
        rotation = d;
        double radians = Math.toRadians(rotation);
        double x_1 = orgLine.getStartX();
        double y_1 = orgLine.getStartY();
        double x_2 = orgLine.getEndX();
        double y_2 = orgLine.getEndY();
        double length = orgLine.getLength();

        x_1 = x_2 + (length * Math.sin(radians) * -1);
        y_1 = y_2 + (length * Math.cos(radians) * -1);

        setLine((float) x_1 + x, (float) y_1 + y, (float) x_2 + x, (float) y_2 + y);
    }

    public void draw(Canvas c) {
		drawLine(getPixelTable(), c);
	}

    public int getY() {
        return y;
    }


    public boolean interacts(Rectangle hb) {
        for(Position p : getPixelTable()) {
            Rectangle r = new Rectangle(p.getX(), p.getY(), 1, 1);
            if(r.interacts(hb)) return  true;
        }
        return false;
    }
}
