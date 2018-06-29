package ca.uwaterloo.cs446.cs446project;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.Observable;
import java.util.Observer;

public class GameActivity extends Activity implements Observer{

    GameModel model;

    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class), Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }

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

        // bind music service to activity
        doBindService();
        // start service
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);



    }

    @Override
    protected void onPause() {
        super.onPause();

        mServ.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //mServ.resumeMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        doUnbindService();
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
