package paperplane.android.me.aars.paperplane.OpenGLES;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Kristian on 28.07.2016.
 */
public class Triangle {

    private float vertices[] = {
            0f, 2f, //p0
            2f, -2f, //p1
            -2f, -2f //p2
    };
    private FloatBuffer vertBuff;

    private short[] pIndex = {0, 1, 2};
    private ShortBuffer pBuff;

    public Triangle() {
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());
        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);

        ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
        pbBuff.order(ByteOrder.nativeOrder());
        pBuff = pbBuff.asShortBuffer();
        pBuff.put(pIndex);
        pBuff.position(0);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuff);
        gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
