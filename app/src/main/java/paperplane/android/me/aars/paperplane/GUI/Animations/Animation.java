package paperplane.android.me.aars.paperplane.GUI.Animations;

import paperplane.android.me.aars.paperplane.GUI.Component2D;

/**
 * Created by Kristian on 01.04.2016.
 */
public class Animation extends Component2D {

    boolean running = false;

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
