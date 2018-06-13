package ca.uwaterloo.cs446.cs446project;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Observable;
import java.util.Observer;

public class GameActivity extends Activity implements Observer{

    GameModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        model= GameModel.getInstance();
        setContentView(new GameView(this, getWindowManager().getDefaultDisplay(), model));
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        GameModel.setInstance(this, getWindowManager().getDefaultDisplay(), 60, true);
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
