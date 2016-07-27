package paperplane.android.me.aars.paperplane.Managers;

import android.util.Log;

import paperplane.android.me.aars.paperplane.GUI.Panel2D;
import paperplane.android.me.aars.paperplane.GameState.GameState;
import paperplane.android.me.aars.paperplane.GameState.StateGame;
import paperplane.android.me.aars.paperplane.GameState.StateGameOver;
import paperplane.android.me.aars.paperplane.GameState.StateMenu;
import paperplane.android.me.aars.paperplane.GameState.StateMenu2;
import paperplane.android.me.aars.paperplane.GameState.StateTesting;

public class GameStateManager extends Panel2D {

    public static final int GAME_STATE_MENU = 0;
    public static final int GAME_STATE_GAME = 1;
    public static final int GAME_STATE_GAMEOVER = 2;
    public static final int GAME_STATE_HELP = 3;
    public static final int GAME_STATE_SHOP = 4;
    public static final int GAME_STATE_LEVELSELECT = 5;
    public static final int GAME_STATE_TESTING = 6;

    private int gameState;
    private paperplane.android.me.aars.paperplane.GameView gameView;

    public GameStateManager(paperplane.android.me.aars.paperplane.GameView gameView) {
        this.gameView = gameView;
        gameStateMenu();
    }

    private void setGameState(int gameState, GameState temp) {
        Log.i("GameStateManager", "Changing gameState to " + gameState);
        this.gameState = gameState;
        removeAllComponents();
        add(temp);
    }

    public void gameStateMenu() {
        setGameState(GAME_STATE_MENU, new StateMenu2(this));
    }

    public void gameStateGame() {
        setGameState(GAME_STATE_GAME, new StateGame(this));
    }

    public void gameStateGameOver(int score, int collectedCoins, int color) {
        setGameState(GAME_STATE_GAMEOVER, new StateGameOver(this, score, collectedCoins, color));
    }

    public void gameStateTesting() {
        setGameState(GAME_STATE_TESTING, new StateTesting(this));
    }

    public paperplane.android.me.aars.paperplane.GameView getGameView() {
        return this.gameView;
    }
}