package paperplane.android.me.aars.paperplane.GUI;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import paperplane.android.me.aars.paperplane.Utilities.Rectangle;

public class Image2D extends Component2D {

	private Bitmap drawImage;
	private Bitmap orgImage;

	private Rectangle bounds;
	private Rectangle imageDrawArea;

	public Image2D(int id, Resources res) {
		bounds = new Rectangle(0, 0, 0, 0);
		loadImage(id, res);
	}

	public Image2D(Bitmap b) {
		setBitmap(b);
	}

	@Override
	public void draw(Canvas c) {
        if(!isVisible) return;
		c.drawBitmap(drawImage, x, y, null);
	}

	private void loadImage(int id, Resources res) {
		setBitmap(getBitmap(id, res));
	}

	public void setBitmap(Bitmap b) {
		drawImage = orgImage = b;
		setBounds(drawImage.getWidth(), drawImage.getHeight());
	}

	public void rotateImage(float rotX, float rotY, float degrees) {
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, rotX, rotY);
		drawImage = Bitmap.createBitmap(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), matrix, false);

		setBounds(drawImage.getWidth(), drawImage.getHeight());
	}

	public boolean isHit(int x, int y, int type, int button) {
		return false;
	}

	public void setAlpha(float alpha) {}

	private static BitmapFactory.Options options = new BitmapFactory.Options();

	public static Bitmap getBitmap(int id, Resources res, boolean scale) {
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		options.inScaled = scale;
		return BitmapFactory.decodeResource(res, id, options);
	}

    public static Bitmap getBitmap(int id, Resources res) {
        return getBitmap(id, res, true);
    }

	public void scaleBitmap(float ratio) {
		setBitmap(scaleBitmap(orgImage, ratio));
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, float ratio) {
		float bitmapRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
		int newWidth = (int) ((float) bitmap.getWidth() * ratio);
		int newHeight = (int) ((float) newWidth / bitmapRatio);

		//newWidth = bitmap.getWidth();
		//newHeight = bitmap.getHeight();
		//c.println(newWidth + " " + newHeight + " " + ratio + " " + bitmapRatio);

		return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
	}

	public void getPixels(int[] pixels) {
		drawImage.getPixels(pixels, 0, width, 0, 0, width, height);
	}

	public int getPixel(int x, int y) {
		return drawImage.getPixel(x, y);
	}
}