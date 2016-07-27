package paperplane.android.me.aars.paperplane.GameState;

        import paperplane.android.me.aars.paperplane.GUI.Panel2D;
        import paperplane.android.me.aars.paperplane.GameView;
        import paperplane.android.me.aars.paperplane.Managers.GameStateManager;

/**
 * Created by krist on 15.09.2015.
*/

public class GameState extends Panel2D {

    protected GameStateManager stateManager;
    protected GameView gameView;

    public GameState(GameStateManager stateManager) {
        this.stateManager = stateManager;
        gameView = stateManager.getGameView();

        setBounds(gameView.width, gameView.height);
    }
}