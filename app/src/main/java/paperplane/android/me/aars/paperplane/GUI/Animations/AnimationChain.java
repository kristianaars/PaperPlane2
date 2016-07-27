package paperplane.android.me.aars.paperplane.GUI.Animations;

import android.graphics.Canvas;

/**
 * Created by Kristian on 06.04.2016.
 */
public class AnimationChain extends Animation {

    //Will run the animations recieved in order

    private Animation[] animations;

    private Animation animation;
    private int index;

    public AnimationChain(Animation[] animations) {
        this.animations = animations;
    }

    public void start() {
        super.start();

        index = 0;
        animation = animations[index];
        animation.start();
    }

    public void update() {
        if(!running) return;

        for(Animation anim: animations) {
            anim.update();
        }


        if(!animation.isRunning()) {
            if(index + 1 == animations.length) {
                stop();
            } else {
                index += 1;
                animation = animations[index];
                animation.start();
            }
        }
    }

    public void draw(Canvas c) {
        for(Animation anim: animations) {
            anim.draw(c);
        }
    }
}
