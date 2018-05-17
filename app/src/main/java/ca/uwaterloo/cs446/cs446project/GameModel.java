package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class GameModel {
    public ArrayList<Character> characters;
    public ArrayList<PhysicalModel> structures;
    public ArrayList<UI> uis;

    public GameModel(Context context){
        characters=new ArrayList<Character>();
        structures=new ArrayList<PhysicalModel>();
        uis=new ArrayList<UI>();

        uis.add(new UI("LeftButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.left),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.left),
                30, 650,
                280,260)
        );
        uis.add(new UI("RightButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                1500,650,
                280,260)
        );
        characters.add(new Protagonist(context,this,100,100));
    }

    public void optionalDraw(int option, Canvas canvas){
        if(option==0){
            for(UI ui: uis){
                ui.draw(canvas);
            }
        }else if(option==1){
            for (Character c: characters){
                c.draw(canvas);
            }
        }else if(option==2){
            for (PhysicalModel pm: structures){
                // add draw method
            }
        }
    }

    public UI getUI(String name){
        for(UI ui: uis){
            if(ui.name==name) return ui;
        }
        return null;
    }
}
