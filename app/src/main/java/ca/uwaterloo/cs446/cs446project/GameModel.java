package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
<<<<<<< HEAD
import android.graphics.Point;
import android.view.Display;
=======
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
>>>>>>> 031528b9577050e39f932771547f240bb60edfea

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class GameModel {
    public ArrayList<Character> characters;
    public ArrayList<PhysicalModel> structures;

    Display display;
    FloorConstant allFloors;
    Point point;
    public ArrayList<UI> uis;

    public GameModel(Context context, Display d){
        characters=new ArrayList<Character>();
        structures=new ArrayList<PhysicalModel>();

        display = d;
        point = new Point();
        display.getSize(point);
        allFloors = new FloorConstant(context, point);

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

        structures.add(new log(context, FloorConstant.log3_src, FloorConstant.log3_dest, FloorConstant.log));
        for (int i = 0; i < FloorConstant.frames.size(); i++) {
            structures.add(new Floor(context, FloorConstant.frames.get(i), i));
        }

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
