package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class GameModel {
    public ArrayList<Character> characters;
    public ArrayList<Frame> structures;

    Display display;

    Point point;
    public ArrayList<UI> uis;
    public int cur_frame = 5;
    public int fps;
    public int current_char = 0;

    public GameModel(Context context, Display d, int _fps){

        this.fps = _fps;
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

    // left button clicked
    public void left(){
        // only thrust left/ right if character on ground
        characters.get(current_char).thrustLeft();
        characters.get(current_char).state=2;
    }

    // right button clicked
    public void right(){
        //model.characters.get(0).left+=10;
        characters.get(current_char).thrustRight();
        characters.get(current_char).state=1;
    }

    // slide up: jump
    public void jump(){
        characters.get(current_char).jump();
        characters.get(current_char).state=0;
    }
}
