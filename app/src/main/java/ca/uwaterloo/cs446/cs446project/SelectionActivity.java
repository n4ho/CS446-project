package ca.uwaterloo.cs446.cs446project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

public class SelectionActivity extends Activity {

    ArrayList<Button> levels;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_selection);

        levels=new ArrayList<>();

        backButton=(Button)findViewById(R.id.back);

        levels.add((Button)findViewById(R.id.level1));
        levels.add((Button)findViewById(R.id.level2));
        levels.add((Button)findViewById(R.id.level3));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectionActivity.this, MainActivity.class));
            }
        });

        for(int i=0;i<levels.size();++i){
            levels.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SelectionActivity.this, GameActivity.class));
                }
            });
        }
    }
}
