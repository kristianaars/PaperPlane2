package paperplane.android.me.aars.paperplane.GameState;

import paperplane.android.me.aars.paperplane.GUI.*;
import paperplane.android.me.aars.paperplane.Game.Bricks;
import paperplane.android.me.aars.paperplane.Game.Plane;
import paperplane.android.me.aars.paperplane.Game.Sky;
import paperplane.android.me.aars.paperplane.Managers.GameStateManager;
import paperplane.android.me.aars.paperplane.R;

/**
 * Created by krist on 15.09.2015.
 */
public class StateMenu extends GameState {

    private Bricks bricks;
    private Sky sky;
    private Plane plane;

    private Image2D header;
    private Button2D helpButton;
    private Button2D shopButton;
    private Button2D playButton;
    private Button2D rateButton;
    private Button2D removeAdsButton;
    private Button2D levelButton;

    private int background_animation_y = 5;
    private static final int BACKGROUND_ANIMATION_SPEED = 3;

    public StateMenu(GameStateManager stateManager) {
        super(stateManager);

        loadHeader();
        loadBackground();
        loadButtons();

        addComponents();
    }

    private void loadHeader() {
        header = new Image2D(R.drawable.logo, gameView.getResources());
        header.setPosition(Position.center(gameView.width, header.width), (int) ((float) gameView.height * 0.05F));
    }

    private void loadBackground() {
        sky = new Sky(gameView);
        bricks = new Bricks(gameView);
        plane = new Plane(gameView);

        bricks.setLevel(1);
    }

    private void loadButtons() {
        helpButton = new Button2D(Button2D.FORM_TYPE_CIRCLE, Image2D.getBitmap(R.drawable.button_help_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        shopButton = new Button2D(Button2D.FORM_TYPE_CIRCLE, Image2D.getBitmap(R.drawable.button_shop_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        playButton = new Button2D(Button2D.FORM_TYPE_CIRCLE, Image2D.getBitmap(R.drawable.button_play_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        rateButton = new Button2D(Button2D.FORM_TYPE_CIRCLE, Image2D.getBitmap(R.drawable.button_rate_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        removeAdsButton = new Button2D(Button2D.FORM_TYPE_CIRCLE, Image2D.getBitmap(R.drawable.button_removeads_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);
        levelButton = new Button2D(Button2D.FORM_TYPE_ROUNDED_SQUARE, Image2D.getBitmap(R.drawable.button_level_image, gameView.getResources()), Button2D.DEFAULT_BUTTON_COLOR);

        findButtonPositions();
    }

    private void findButtonPositions() {
        int tempX = 0;
        int tempY = 0;

        //Play button
        int playBtnWidth = playButton.width;
        int playBtnHeight = playButton.height;
        int playBtnX = Position.center(gameView.width, playBtnWidth);
        int playBtnY = Position.center(gameView.height, playBtnHeight); //590;
        playButton.setPosition(playBtnX, playBtnY);

        playButton.setAction(new Runnable() {
            @Override
            public void run() {
                stateManager.gameStateGame();

            }
        });

        //Help button
        tempX = playBtnX - (helpButton.width/2);
        tempY = playBtnY - (helpButton.height/2);
        helpButton.setPosition(tempX, tempY);

        //Shop button
        tempX = (playBtnX + playBtnWidth) - (shopButton.width / 2);
        tempY = playBtnY - (shopButton.height / 2);
        shopButton.setPosition(tempX, tempY);

        //Rate Button
        tempX = playBtnX - (rateButton.width / 2);
        tempY = (playBtnY + playBtnHeight) - (rateButton.height/2);
        rateButton.setPosition(tempX, tempY);

        //RemoveAds button
        tempX = (playBtnX + playBtnWidth) - (removeAdsButton.width / 2);
        tempY = (playBtnY + playBtnHeight) - (removeAdsButton.height / 2);
        removeAdsButton.setPosition(tempX, tempY);

        //Level button
        tempX = Position.center(gameView.width, levelButton.width);
        tempY = Position.allignBottom(gameView.height, levelButton.height) - (int) ((float) gameView.height * 0.1F);
        levelButton.setPosition(tempX, tempY);
    }

    private void addComponents() {
        //Background
        add(sky);
        add(bricks);
        //add(plane);

        //Header
        add(header);

        add(helpButton);
        add(shopButton);
        add(rateButton);
        add(removeAdsButton);
        add(levelButton);
        add(playButton);
    }

    @Override
    public void update() {
        background_animation_y += BACKGROUND_ANIMATION_SPEED;
        bricks.setGameY(background_animation_y);
        sky.setGameY(background_animation_y / 2);

        super.update();
    }
}
