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
                (int)(point.x*0.01), (int)(point.y*0.8),
                point.x/20,point.y/15)
        );
        uis.add(new UI("RightButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                (int)(point.x*0.08),(int)(point.y*0.8),
                point.x/20,point.y/15)
        );
        uis.add(new UI("UpButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                (int)(point.x*0.05), (int)(point.y*0.735),
                point.y/15,point.x/20)
        );
        uis.add(new UI("DownButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.down),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.down),
                (int)(point.x*0.05), (int)(point.y*0.85),
                point.y/15,point.x/20)
        );
        uis.add(new UI("JumpButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                (int)(point.x*0.85), (int)(point.y*0.83),
                point.y/15,point.x/20)
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

    // left button clicked
    public void left() {
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