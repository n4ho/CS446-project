package ca.uwaterloo.cs446.cs446project;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SelectionActivity extends Activity implements Observer{

    ArrayList<Button> levels;
    Button backButton;

    GameModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_selection);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        levels=new ArrayList<>();

        backButton=(Button)findViewById(R.id.back);

        levels.add((Button)findViewById(R.id.level1));
        levels.add((Button)findViewById(R.id.level2));
        levels.add((Button)findViewById(R.id.level3));

        GameModel.readModel();
        model= GameModel.getInstance();

        GameModel.setInstance(this, getWindowManager().getDefaultDisplay(), 60, false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, MainActivity.class));
            }
        });

        for(int i=0;i<levels.size();++i){
            if(model.max_frame<4){
                if(i>0){
                    levels.get(i).setEnabled(false);
                }
            }else if(model.max_frame<7){
                if(i>1){
                    levels.get(i).setEnabled(false);
                }
            }else{
                // all enabled
            }


        }

        levels.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, GameActivity.class));
                 //   model.cur_frame=0;
            }
        });

        levels.get(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, GameActivity.class));
               // model.cur_frame=4;
            }
        });

        levels.get(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, GameActivity.class));
                //model.cur_frame=7;
            }
        });
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
