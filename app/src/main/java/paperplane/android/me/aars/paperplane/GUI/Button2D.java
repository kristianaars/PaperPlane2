package paperplane.android.me.aars.paperplane.GUI;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import paperplane.android.me.aars.paperplane.GameView;
import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

/**
 * Created by Kristian on 02.03.2016.
 */
public class Button2D extends Component2D {

    //Decleare the diffrent figure types
    //FORM_TYPE_CLEAR is a transparent background
    public final static int FORM_TYPE_SQUARE = 0;
    public final static int FORM_TYPE_ROUNDED_SQUARE = 1;
    public final static int FORM_TYPE_CIRCLE = 2;
    public final static int FORM_TYPE_CLEAR = 3;

    //Decleares the diffrent button types
    private final static int BUTTON_TYPE_IMAGE = 0;
    private final static int BUTTON_TYPE_TEXT = 1;

    //Decleares the diffrent button modes
    private final static int NUMBER_OF_BUTTON_MODES = 2;
    private final static int BUTTON_MODE_NORMAL = 0;
    private final static int BUTTON_MODE_PRESSED = 1;

    //Default button color
    public final static int DEFAULT_BUTTON_COLOR = 0xFF0277BD;

    //Creates variables used to define button design (Will be assigned in constructor)
    private int form_type;
    private int button_type;
    private int[] colors;

    //Will either use text or image, depends on constructor and button type
    private String text = null;
    private Bitmap image = null;
    private Paint paint;

    private int button_mode = BUTTON_MODE_NORMAL;
    private Runnable action;

    //Constructor should always be called from public constructors
    private Button2D(int form_type, int color) {
        this.form_type = form_type;
        colors = decleareColors(color);

        paint = new Paint();
        paint.setShadowLayer(6, 0, 3, 0x50000000);
        paint.setColor(color);
    }

    //Text will overlay the figure
    public Button2D(int form_type, String text, int color) {
        this(form_type, color);

        this.text = text;
        button_type = BUTTON_TYPE_TEXT;
    }

    //Image will overlay the figure
    public Button2D(int form_type, Bitmap image, int color) {
        this(form_type, color);

        this.image = image;
        button_type = BUTTON_TYPE_IMAGE;

        fitImage(image);
    }

    private void fitImage(Bitmap img) {
        setBounds(img.getWidth(), img.getHeight());
    }

    //Returns array with color for all button modes
    private int[] decleareColors(int default_color) {
        int[] colors = new int[NUMBER_OF_BUTTON_MODES];

        //Colors[1] is shifted for a darker color
        colors[BUTTON_MODE_NORMAL] = default_color;
        colors[BUTTON_MODE_PRESSED] = default_color - 30;

        return colors;
    }

    public void draw(Canvas c) {
        paint.setColor(colors[button_mode]);

        switch (form_type) {

            case(FORM_TYPE_CIRCLE):
                c.drawOval(x, y, x + width, y + height, paint);
                break;
            case(FORM_TYPE_ROUNDED_SQUARE):
                c.drawRoundRect(x, y, x + width, y + height, 8, 8, paint);
                break;
        }

        //Switch statement selects the correct Button Type (Text or Image)
        switch (button_type) {
            case(BUTTON_TYPE_IMAGE):
                c.drawBitmap(image, x, y, null);
                break;
        }
    }

    @Override
    public boolean isHit(int x, int y, int type, int button) {
        Rectangle mouse = new Rectangle(x, y, 1, 1);

        if(mouse.interacts(getBounds())) {
            if(button == GameView.TOUCH_PRESSED) {
                button_mode = BUTTON_MODE_PRESSED;
                return true;
            } else {
                button_mode = BUTTON_MODE_NORMAL;
                runAction();
                return true;
            }
        } else {
            button_mode = BUTTON_MODE_NORMAL;
            return  false;
        }
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    private void runAction() {
        if(action == null) return;
        action.run();
    }
}