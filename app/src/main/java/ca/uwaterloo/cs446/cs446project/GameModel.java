package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;


public class GameModel extends Observable{


    private static final GameModel ourInstance = new GameModel();

    static void setInstance(Context context, Display d, int _fps, boolean isGameView){
        ourInstance.fps=_fps;
        ourInstance.display=d;
        ourInstance.context=context;

        Point point=ourInstance.point;
        ourInstance.display.getSize(point);

        if(isGameView) {
            System.out.println("LOAD MODEL BITMAP!");
            ourInstance.uis.add(new UI("LeftButton",
                    compress(context,R.drawable.left),
                    compress(context,R.drawable.left),
                    (int) (point.x * 0.02), (int) (point.y * 0.8),
                    point.x / 20 , point.y / 15)
            );
            ourInstance.uis.add(new UI("RightButton",
                    compress(context,R.drawable.right),
                    compress(context,R.drawable.right),
                    (int) (point.x * 0.09), (int) (point.y * 0.8),
                    point.x / 20, point.y / 15)
            );

            ourInstance.uis.add(new UI("UpButton",
                    compress(context,R.drawable.up),
                    compress(context,R.drawable.up),
                    (int) (point.x * 0.06), (int) (point.y * 0.735),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("DownButton",
                    compress(context,R.drawable.down),
                    compress(context,R.drawable.down),
                    (int) (point.x * 0.06), (int) (point.y * 0.85),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("JumpButton",
                    compress(context,R.drawable.up),
                    compress(context,R.drawable.up),
                    (int) (point.x * 0.93), (int) (point.y * 0.83),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("Backpack",
                    compress(context,R.drawable.backpack),
                    compress(context,R.drawable.backpack),
                    (int) (point.x * 0.91), (int) (point.y * 0.1),
                    point.y / 10, point.x / 15)
            );

            ourInstance.inventory = new Inventory("Inventory",
                    compress(context,R.drawable.inventory),
                    compress(context,R.drawable.inventory),
                    (int) (point.x * 0.4), (int) (point.y * 0.1),
                    (int) (point.x * 0.51), point.x / 12,
                    ourInstance.fps, context);

            ourInstance.uis.add(ourInstance.inventory);

            ourInstance.characters.add(new Protagonist(context, ourInstance, 90, 100));

            for (int i = 0; i < 4; i++) {
                ourInstance.structures.add(new Frame(i, point, context));
            }


        }
    }

    static GameModel getInstance()
    {
        return ourInstance;
    }

    static public Bitmap compress(Context context, int image){
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(context.getResources(), image, options);
//        options.inSampleSize = 15;
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(context.getResources(), image, options);
        InputStream is = context.getResources().openRawResource(+ image);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = 4;
        return BitmapFactory.decodeStream(is,null,opt);

    }

    // for scale purpose
    Display display;
    Context context;
    Point point;
    int fps;

    // game objs
    public ArrayList<Character> characters;
    public ArrayList<Frame> structures;
    public ArrayList<UI> uis;

    public int cur_frame = 0;

    public int current_char = 0;

    public int trans_x = 0;
    public int trans_y = 0;
    public Inventory inventory;


    public GameModel(){

        characters=new ArrayList<Character>();
        structures=new ArrayList<Frame>();
        uis=new ArrayList<UI>();

        point = new Point();
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

    public void characterReborn(int x, int y){
        this.getCharacter().top=y;
        this.getCharacter().left=x;
        trans_x = 0;
        trans_y = 0;
    }

    public void update(){
        for (Character c: characters) {
            c.update();
        }
        for(UI u: uis){
            u.translate(trans_x,trans_y);
        }
        if (inventory.animation){
            inventory.update();
        }
    }

    public UI getUI(String name){
        for(UI ui: uis){
            if(ui.name==name) return ui;
        }
        return null;
    }

    // left button clicked
    public void left() {
        // only thrust left/ right if character on ground
        characters.get(current_char).state=MoveType.LEFT;
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
        characters.get(current_char).state=MoveType.RIGHT;
        characters.get(current_char).thrustRight();
    }

    // right button released
    public void right_release(){
        // if character in air, velocity in X should not stop
        //characters.get(current_char).state=0;
        characters.get(current_char).stopX();
    }


    // jump button: jump
    public void jump(){
        //characters.get(current_char).state=MoveType.JUMP;
        characters.get(current_char).jump();
    }

    // up button: move up(when there is a ladder)
    public void up(){
        characters.get(current_char).thrustUp();
        characters.get(current_char).state=MoveType.UP;
    }

    // down button: move up(when there is a ladder)
    public void down(){
        characters.get(current_char).thrustDown();
        characters.get(current_char).state=MoveType.DOWN;
    }

    public void gravitySwitch(boolean b){
        if(b){
            characters.get(current_char).startGravity();
        }else{
            characters.get(current_char).stopGravity();
        }
    }

    public Character getCharacter(){
        return characters.get(current_char);
    }
}
