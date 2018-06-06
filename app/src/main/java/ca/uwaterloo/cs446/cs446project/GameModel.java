package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


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
            ourInstance.uis.add(new UI("LeftButton",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.left),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.left),
                    (int) (point.x * 0.02), (int) (point.y * 0.8),
                    point.x / 20, point.y / 15)
            );
            ourInstance.uis.add(new UI("RightButton",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.right),
                    (int) (point.x * 0.09), (int) (point.y * 0.8),
                    point.x / 20, point.y / 15)
            );

            ourInstance.uis.add(new UI("UpButton",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                    (int) (point.x * 0.06), (int) (point.y * 0.735),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("DownButton",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.down),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.down),
                    (int) (point.x * 0.06), (int) (point.y * 0.85),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("JumpButton",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.up),
                    (int) (point.x * 0.93), (int) (point.y * 0.83),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("Backpack",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.backpack),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.backpack),
                    (int) (point.x * 0.91), (int) (point.y * 0.1),
                    point.y / 10, point.x / 15)
            );

            ourInstance.inventory = new Inventory("Inventory",
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.inventory),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.inventory),
                    (int) (point.x * 0.4), (int) (point.y * 0.1),
                    (int) (point.x * 0.51), point.x / 12,
                    ourInstance.fps, context);

            ourInstance.uis.add(ourInstance.inventory);

            ourInstance.characters.add(new Protagonist(context, ourInstance, 60, 70));

            for (int i = 0; i < 10; i++) {
                ourInstance.structures.add(new Frame(i, point, context));
            }

            ourInstance.backgroud = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroud00003);
            ourInstance.backgroud = Bitmap.createScaledBitmap(ourInstance.backgroud, point.x, point.y, false);
        }
    }

    static GameModel getInstance()
    {
        return ourInstance;
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
    
    public int cur_frame = 7;
    public int current_char = 0;

    public int trans_x = 0;
    public int trans_y = 0;
    public Inventory inventory;

    // just for test purpose! move it into frame
    public Bitmap backgroud;

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
