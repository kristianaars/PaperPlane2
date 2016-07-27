package paperplane.android.me.aars.paperplane.GameState;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import paperplane.android.me.aars.paperplane.BuildConfig;
import paperplane.android.me.aars.paperplane.GUI.Animations.FadeoutBox;
import paperplane.android.me.aars.paperplane.GUI.Button2D;
import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.GUI.Position;
import paperplane.android.me.aars.paperplane.GUI.Text2D;
import paperplane.android.me.aars.paperplane.Managers.GameStateManager;
import paperplane.android.me.aars.paperplane.Managers.MoneyManager;
import paperplane.android.me.aars.paperplane.R;
import paperplane.android.me.aars.paperplane.Managers.GameSaveFile;

/**
 * Created by Kristian on 06.04.2016.
 */
public class StateGameOver extends GameState {


    private FadeoutBox fadeInAnimation;
    private Paint paint;

    private GameSaveFile saveFile;
    private MoneyManager moneyManager;

    private int backgroundColor;
    private int game_score;
    private int coins;
    private int high_score;

    private Button2D replay;
    private Button2D online_scores;
    private Button2D home;

    private Text2D header;
    private Text2D score;
    //Score_text is for the text undernieth score
    private Text2D score_text;
    private Text2D best_header;
    private Text2D best;

    public StateGameOver(GameStateManager stateManager, int score, int collectedCoins, int color) {
        super(stateManager);

        this.game_score = score;
        this.coins = collectedCoins;
        this.backgroundColor = color;

        paint = new Paint();
        paint.setColor(backgroundColor);

        fadeInAnimation = new FadeoutBox(backgroundColor, width, height);
        fadeInAnimation.start();

        saveFile = new GameSaveFile(this.gameView.getContext().getString(R.string.default_game_save_file), this.gameView.getContext());
        moneyManager = new MoneyManager(saveFile, gameView);

        submitScore();
        depositCoins();

        configureButtons();
        configureText();

        addComponents();
    }

    private void depositCoins() {
        if(!moneyManager.deposit(coins)) {
            Log.e("MoneyError", "Error depositing " + coins + " Coins to account after game...");
        }
    }

    private void configureButtons() {
        replay = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.game_over_play, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        online_scores = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.game_over_highscores, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        home = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.game_over_home, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);

        replay.setAction(new Runnable() {
            @Override
            public void run() {
                stateManager.gameStateGame();
            }
        });

        online_scores.setAction(new Runnable() {
            @Override
            public void run() {
                gameView.getGameActivity().showLeaderboard();
            }
        });

        home.setAction(new Runnable() {
            @Override
            public void run() {
                stateManager.gameStateMenu();
            }
        });

        int btn_w = online_scores.width;
        int btn_h = online_scores.height;

        int x = 0;
        int y = (int) (1400 * gameView.ratio);

        int center_x = Position.center(width, 1);

        //Online Scores - Middle
        x = center_x - (btn_w/2);
        online_scores.setPosition(x, y);

        //Replay - Left of online scores
        x = (int) ((center_x - (btn_w/2)) - 350 * gameView.ratio);
        replay.setPosition(x, y);

        //Home - Right of online scores
        x = (int) ((center_x + (btn_w/2) - btn_w) + 350 * gameView.ratio);
        home.setPosition(x, y);
    }

    private void configureText() {
        Typeface laneTypeFace = paint.setTypeface(Typeface.createFromAsset(gameView.getResources().getAssets(), "fonts/LANENAR.ttf"));

        header = new Text2D("GAME OVER");
        score = new Text2D(""+game_score);
        best_header = new Text2D("Best");
        best = new Text2D(""+high_score);

        header.setFont(laneTypeFace);
        score.setFont(laneTypeFace);
        best_header.setFont(laneTypeFace);
        best.setFont(laneTypeFace);

        header.setFontSize((int) (190 * gameView.ratio));
        score.setFontSize((int) (280 * gameView.ratio));
        best_header.setFontSize((int) (140 * gameView.ratio));
        best.setFontSize((int) (120 * gameView.ratio));

        header.setPosition(Position.center(width, header.width), (int) (240 * gameView.ratio));
        score.setPosition(Position.center(width, score.width), (int) (700 * gameView.ratio));
        best_header.setPosition(Position.center(width, best_header.width), (int) (1070 * gameView.ratio));
        best.setPosition(Position.center(width, best.width), (int) (1200 * gameView.ratio));

        header.setColor(Color.WHITE);
        score.setColor(Color.WHITE);
        best_header.setColor(Color.WHITE);
        best.setColor(Color.WHITE);
    }

    private void addComponents() {
        add(replay);
        add(online_scores);
        add(home);

        add(header);
        add(score);
        add(best_header);
        add(best);

        add(fadeInAnimation);
    }

    public void draw(Canvas c) {
        c.drawRect(0, 0, width, height, paint);

        super.draw(c);
    }

    private void submitScore() {
        this.high_score = getSavedHighscore();
        if (this.high_score < this.game_score) {
            this.high_score = this.game_score;
            setHighscore(this.high_score);
        }

        gameView.getGameActivity().updateLeaderBoard(this.gameView.getResources().getString(R.string.leaderboard_freeplay), this.game_score);
    }

    private int getSavedHighscore() {
        String temp = saveFile.getData(this.gameView.getContext().getString(R.string.saveid_highscore));
        if (temp == null) {
            temp = game_score + BuildConfig.FLAVOR;
            setHighscore(game_score);
        }

        return Integer.parseInt(temp);
    }

    private void setHighscore(int highscore) {
        saveFile.setData(this.gameView.getContext().getString(R.string.saveid_highscore), BuildConfig.FLAVOR + highscore);
    }
}