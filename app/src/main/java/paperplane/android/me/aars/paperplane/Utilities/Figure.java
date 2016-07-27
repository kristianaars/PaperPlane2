package paperplane.android.me.aars.paperplane.Utilities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Kristian on 07.04.2016.
 */
public class Figure extends Rectangle {

    public static final int BLOCK_CLEAR = 0;
    public static final int BLOCK_FILLED = 1;

    private int[] blocks;

    private Paint paint;

    private int pointOfDetail = 1;

    public Figure(double x, double y, double width, double height) {
        super(x, y, width, height);

        configureArray();

        paint = new Paint();
        paint.setColor(Color.BLUE);

        //System.out.println(getXAndY(90000));
    }

    public void setBounds(int width, int height) {
        setWidth(width);
        setHeight(height);
        configureArray();
    }

    private void configureArray() {
        blocks = new int[(int) width * (int) height];
    }

    public void setBlock(int pos, int type) {
        //Type is either BLOCK_CLEAR or BLOCK_FILLED
        //BLOCK_CLEAR

        if(pos >= blocks.length) {
            return;
        }

        if(type != BLOCK_CLEAR && type != BLOCK_FILLED) type = BLOCK_CLEAR;

        blocks[pos] = type;
    }

    public void clear() {
        for(int i = 0; i < blocks.length; i++) {
            blocks[i] = BLOCK_CLEAR;
        }
    }

    public void setBlock(int x, int y, int type) {
        int pos = ((int) width * (y)) + x;
        setBlock(pos, type);
    }

    private Position getXAndY(int pos) {
        int y = pos/(int) width;
        int x = pos%(int) width;

        return new Position(x, y);
    }

    @Override
    public boolean interacts(Rectangle r) {
        int blockType;

        Position tmp_pos;
        Rectangle tmp_rct;

        int tmp_x;
        int tmp_y;

        for(int i = 0; i < blocks.length; i+=pointOfDetail) {
            blockType = blocks[i];

            if(blockType == BLOCK_FILLED) {
                tmp_pos = getXAndY(i);

                tmp_x = (int) this.x + tmp_pos.getX();
                tmp_y = (int) this.y + tmp_pos.getY();

                tmp_rct = new Rectangle(tmp_x, tmp_y, 1, 1);

                if(r.interacts(tmp_rct)) return true;
            }
        }

        return false;
    }

    public void draw(Canvas c) {
        if(paint == null) {
            System.out.println("PAINT = NULL in FIGURE");
            return;
        }


        int blockType;

        Position tmp_pos;

        int tmp_x;
        int tmp_y;

        for(int i = 0; i < blocks.length; i++) {
            blockType = blocks[i];

            if(blockType == BLOCK_FILLED) {
                tmp_pos = getXAndY(i);

                tmp_x = (int) this.x + tmp_pos.getX();
                tmp_y = (int) this.y + tmp_pos.getY();

                c.drawRect(tmp_x, tmp_y, tmp_x + 2, tmp_y + 2, paint);

            }
        }
    }

    public int getPointOfDetail() {
        return pointOfDetail;
    }

    public void setPointOfDetail(int v) {
        pointOfDetail = v;
    }
}
