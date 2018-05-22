package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.util.ArrayList;


public class GameModel {

    public ArrayList<Character> characters;
    public ArrayList<Frame> structures;

    Display display;

    Point point;
    public ArrayList<UI> uis;
    public int cur_frame = 9;

    public GameModel(Context context, Display d){
        characters=new ArrayList<Character>();
        structures=new ArrayList<Frame>();

        display = d;
        point = new Point();
        display.getSize(point);


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

        for (int i = 0; i < 10; i++) {
            structures.add(new Frame(i, point, context));
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
            for (Frame pm: structures){
                // add draw method
            }
        }
    }

    public void update(){
        for (Character c: characters) {
            c.update();
        }
    }

    public UI getUI(String name){
        for(UI ui: uis){
            if(ui.name==name) return ui;
        }
        return null;
    }

    public void hitTest(Rect rect) {
        //add hitTest here.
    }
}
