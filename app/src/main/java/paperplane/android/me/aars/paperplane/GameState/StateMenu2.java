package paperplane.android.me.aars.paperplane.GameState;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;

import paperplane.android.me.aars.paperplane.GUI.Button2D;
import paperplane.android.me.aars.paperplane.GUI.Image2D;
import paperplane.android.me.aars.paperplane.GUI.Panel2D;
import paperplane.android.me.aars.paperplane.GUI.Position;
import paperplane.android.me.aars.paperplane.GUI.Text2D;
import paperplane.android.me.aars.paperplane.Game.Bricks;
import paperplane.android.me.aars.paperplane.Game.Plane;
import paperplane.android.me.aars.paperplane.Game.Sky;
import paperplane.android.me.aars.paperplane.Managers.GameStateManager;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by Kristian Aars on 18.07.2016.
 */
public class StateMenu2 extends GameState {

    private static final float BUTTON_BOTTOM_OFFSET_RATIO = 0.93F;

    private Text2D logoTop;
    private Text2D logoBot;

    private Button2D play;
    private Button2D shop;
    private Button2D rate;
    private Button2D help;
    private Button2D ads;

    private Bricks background;
    private Sky sky;

    public StateMenu2(GameStateManager stateManager) {
        super(stateManager);

        loadLogo();
        loadButtons();
        loadGameItems();

        addComponents();
    }

    private void addComponents() {
        add(sky);
        add(background);

        add(logoTop);
        add(logoBot);

        add(play);
        add(shop);
        add(rate);
        add(help);
        add(ads);
    }

    private void loadGameItems() {
        background = new Bricks(gameView);
        sky = new Sky(gameView);
    }

    private void loadButtons() {
        play = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.button_play_image, gameView.getResources()), 0);

        shop = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.button_shop_image, gameView.getResources()), 0);
        rate = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.button_rate_image, gameView.getResources()), 0);
        help = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.button_help_image, gameView.getResources()), 0);
        ads = new Button2D(Button2D.FORM_TYPE_CLEAR, Image2D.getBitmap(R.drawable.button_removeads_image, gameView.getResources()), 0);

        addButtonActions();
        findButtonPositions();
    }

    private void findButtonPositions() {
        int x;
        int y;

        //PLAY BUTTON
        x = Position.center(gameView.width, play.width);
        y = Position.center(gameView.height, play.height);
        play.setPosition(x, y);

        //SHOP BUTTON
        x = Position.center(gameView.width, (int) (shop.width * 4.4));
        y = Position.allignBottom((int) (gameView.height * BUTTON_BOTTOM_OFFSET_RATIO), shop.height);
        shop.setPosition(x, y);

        //RATE BUTTON
        x += (shop.width * 1.1);
        rate.setPosition(x, y);

        //HELP BUTTON
        x += (shop.width * 1.1);
        help.setPosition(x, y);

        //ADS BUTTON
        x += (shop.width * 1.1);
        ads.setPosition(x, y);
    }

    private void addButtonActions() {
        play.setAction(new Runnable() {
            @Override
            public void run() {
                stateManager.gameStateGame();
            }
        });

        shop.setAction(new Runnable() {
            @Override
            public void run() {
            }
        });

        rate.setAction(new Runnable() {
            @Override
            public void run() {
            }
        });

        help.setAction(new Runnable() {
            @Override
            public void run() {
            }
        });

        ads.setAction(new Runnable() {
            @Override
            public void run() {
            }
        });
    }

    private void loadLogo() {
        Typeface laneTypeFace = Typeface.createFromAsset(gameView.getResources().getAssets(), "fonts/LANENAR.ttf");

        logoTop = new Text2D("P A P E R");
        logoBot = new Text2D("P L A N E");

        logoTop.setColor(Color.WHITE);
        logoBot.setColor(Color.WHITE);

        logoTop.setFont(laneTypeFace);
        logoBot.setFont(laneTypeFace);

        int fontSize = 220;
        logoTop.setFontSize(fontSize);
        logoBot.setFontSize(fontSize);

        int x = Position.center(gameView.width, logoTop.width);
        int y = (int) (fontSize * 1.2F);

        logoTop.setPosition(x, y);

        x = Position.center(gameView.width, logoBot.width);
        y = (int) (y + (fontSize * 0.9F));
        logoBot.setPosition(x, y);
    }
}