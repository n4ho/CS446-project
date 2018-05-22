package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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

        uis.add(new UI("RightButton",
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                140,point.y-230,
                75,150)
        );

        uis.add(new UI("LeftButton",
                FlipBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.right)),
                FlipBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.right)),
                30, point.y-230,
                75,150)
        );

        characters.add(new Protagonist(context,this,100,100));

        for (int i = 0; i < 10; i++) {
            structures.add(new Frame(i, point, context));
        }

    }

    public static Bitmap FlipBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, source.getWidth()/2f, source.getHeight()/2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
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
        characters.get(current_char).state=2;
        characters.get(current_char).thrustLeft();
    }

    // left button released
    public void left_release(){
        // if character in air, velocity in X should not stop
        //characters.get(current_char).state=0;
        characters.get(current_char).stopX();
    }

    // right button clicked
    public void right(){
        characters.get(current_char).state=1;
        characters.get(current_char).thrustRight();
    }

    // right button released
    public void right_release(){
        // if character in air, velocity in X should not stop
        //characters.get(current_char).state=0;
        characters.get(current_char).stopX();
    }

    // slide up: jump
    public void jump(){
        //characters.get(current_char).state=0;
        characters.get(current_char).jump();
    }
}
