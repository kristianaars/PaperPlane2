package paperplane.android.me.aars.paperplane;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

public class GameActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
    private static int RC_SIGN_IN = 0;
    private static final int RC_UNUSED = 5001;
    private GameView g;
    private boolean mAutoStartSignInFlow;
    private boolean mAutoStartSignInflow;
    private GoogleApiClient mGoogleApiClient;
    private boolean mResolvingConnectionFailure;
    private boolean mSignInClicked;

    public GameActivity() {
        this.mResolvingConnectionFailure = false;
        this.mAutoStartSignInflow = true;
        this.mSignInClicked = false;
        this.mAutoStartSignInFlow = true;
    }

    static {
        RC_SIGN_IN = 9001;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DITHER, WindowManager.LayoutParams.FLAG_DITHER);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Games.API).addScope(Games.SCOPE_GAMES).build();

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        g = new GameView(this, width, height);

        setContentView(g);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_game_activty, menu);
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 3) {
            this.g.stop();
        }
        return true;
    }

    protected void onStart() {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        this.mGoogleApiClient.disconnect();
    }

    public void onConnected(Bundle bundle) {
        System.out.println("Sucsesfully connected!!");
    }

    public void onConnectionSuspended(int i) {
        System.out.println("onConnectionSuspended: ATTEMTING TO LOGON");
        this.mGoogleApiClient.connect();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("onConnectionFailed: Starting DEBUGGING CONNECTION: " + connectionResult);
        if (!this.mResolvingConnectionFailure) {
            if (this.mSignInClicked || this.mAutoStartSignInFlow) {
                this.mAutoStartSignInFlow = false;
                this.mSignInClicked = false;
                this.mResolvingConnectionFailure = true;
                if (!BaseGameUtils.resolveConnectionFailure(this, this.mGoogleApiClient, connectionResult, RC_SIGN_IN, "Error signing in")) {
                    this.mResolvingConnectionFailure = false;
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            this.mSignInClicked = false;
            this.mResolvingConnectionFailure = false;
            if (resultCode == -1) {
                this.mGoogleApiClient.connect();
            } else {
                BaseGameUtils.showActivityResultError(this, requestCode, resultCode, RC_SIGN_IN);
            }
        }
    }

    public GoogleApiClient getApiClient() {
        return this.mGoogleApiClient;
    }

    public void attemptLogin() {
        System.out.println("Connection status: " + this.mGoogleApiClient.isConnected());
        this.mGoogleApiClient.connect();
    }

    public void showLeaderboard() {
        if (this.mGoogleApiClient.isConnected()) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(this.mGoogleApiClient), RC_UNUSED);
        } else {
            BaseGameUtils.makeSimpleDialog(this, "Please sign in to view leaderboards").show();
        }
    }

    public void updateLeaderBoard(String id, int value) {
        if (this.mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(this.mGoogleApiClient, id, (long) value);
        }
    }
}
