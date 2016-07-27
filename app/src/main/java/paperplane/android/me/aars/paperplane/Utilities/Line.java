package paperplane.android.me.aars.paperplane.Utilities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Kristian Aars on 27.09.2015.
 */
public class Line {

    private float startX;
    private float startY;
    private float endX;
    private float endY;

    private float length;

    private ArrayList<Position> pixels;

    public Line(float startX, float startY, float endX, float endY) {
        pixels = new ArrayList<Position>();
        setLine(startX, startY, endX, endY);
    }

    public Line() {
        this(0, 0, 0, 0);
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setStartX(float startX) {
        setLine(startX, startY, endX, endY);
    }

    public void setStartY(float startY) {
        setLine(startX, startY, endX, endY);
    }

    public void setEndX(float endX) {
        setLine(startX, startY, endX, endY);
    }

    public void setEndY(float endY) {
        setLine(startX, startY, endX, endY);
    }

    public void setLine(float sX, float sY, float eX, float eY) {

        startX = sX;
        startY = sY;
        endX = eX;
        endY = eY;

        generateLine();
        calculateLength();
    }

    public Position getPixelPos(int index) {
        if (index > pixelCount() || index < 0) return null;
        Position p = pixels.get(index);
        return p;
    }

    public int pixelCount() {
        return pixels.size();
    }

    public void generateLine() {
        ArrayList<Position> pixels = new ArrayList<Position>();

        float lineWidth = startX - endX;
        float lineHeight = startY - endY;
        if (lineWidth == 0)
            lineWidth = 1;
        if (lineHeight == 0)
            lineHeight = 1;

        float yvector = lineHeight / lineWidth;
        float xvector = lineWidth / lineHeight;

        float x = startX;
        float y = startY;

        if (xvector > 1) {
            if(lineWidth > 0) {
                for (int c = 0; c < lineWidth; c++) {
                    x -= 1;
                    y -= yvector;
                    pixels.add(new Position((int) x, (int) y));
                }
            } else if(lineWidth < 0){
                for (int c = (int) lineWidth; c < 0; c++) {
                    x += 1;
                    y += yvector;
                    pixels.add(new Position((int) x, (int) y));
                }
            }
        } else {
            if (lineHeight > 0) {
                for (int c = 0; c < lineHeight; c++) {
                    x -= xvector;
                    y -= 1;
                    pixels.add(new Position((int) x, (int) y));
                }
            } else if (lineHeight < 0) {
                for (int c = (int) lineHeight; c < 0; c++) {
                    x += xvector;
                    y += 1;
                    pixels.add(new Position((int) x, (int) y));
                }
            }
        }

        this.pixels = pixels;
    }

    public static void drawLine(ArrayList<Position> pixels, Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        for(int i = 0; i < pixels.size(); i++) {
            Position p = pixels.get(i);
            c.drawRect(p.getX(), p.getY(), p.getX() + 5, p.getY() + 5, paint);
            //c.println("X: "+p.getX() + " Y: "+p.getY());
        }
    }

    protected void calculateLength() {
        float deltaX = Math.abs(startX - endX);
        float deltaY = Math.abs(startX - endY);
        length = (float) Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
    }

    public ArrayList<Position> getPixelTable() {
        return pixels;
    }

    public String toString() {
        return String.format("Line startX: %s startY: %s endX: %s endY: %s length: %s PixelBuffer: %s", startX, startY, endX, endY, length, pixels.size());
    }

    public float getLength() {
        return length;
    }
}
