package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

        // read max_frame from file
        readModel();
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
                    (int) (point.x * 0.58), (int) (point.y * 0.1),
                    (int) (point.x * 0.3), point.x / 12,
                    ourInstance.fps, context, ourInstance);

            ourInstance.uis.add(ourInstance.inventory);

            ourInstance.characters.add(new Protagonist(context, ourInstance, 70, 100));

            for (int i = 0; i < 4; i++) {
                ourInstance.structures.add(new Frame(i, point, context));
            }


        }
    }

    static GameModel getInstance()
    {
        return ourInstance;
    }

    static void saveModel(){
        String filename="ferrymangame.log";
        File list[]=ourInstance.context.getFilesDir().listFiles();
        File log=list[0];
        boolean exist=false;
        for(File f: list){
            if(f.getName()==filename){
                exist=true;
                log=f;
            }
        }
        if(!exist){
            log=new File(ourInstance.context.getFilesDir(), filename);

        }

        try {
            // clear log data
            PrintWriter writer = new PrintWriter(log);
            writer.println(ourInstance.max_frame);
            writer.close();
        }catch(Exception e){

        }
    }

    static void readModel(){
        String filename="ferrymangame.log";
        File list[]=ourInstance.context.getFilesDir().listFiles();
        File log=ourInstance.context.getFilesDir();
        boolean exist=false;
        for(File f: list){
            if(f.getName()==filename){
                exist=true;
                log=f;
            }
        }
        if(!exist){
            ourInstance.max_frame=0;
        }else{
            //StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(log));
                String line=br.readLine();
                ourInstance.max_frame = Integer.parseInt(line);
//                while ((line = br.readLine()) != null) {
//                    text.append(line);
//                    text.append('\n');
//                }
                br.close();
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }
        }
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

    public int cur_frame = 1;
    public int curlevel = 0;
    public int max_frame=0;

    public int current_char = 0;

    public int trans_x = 0;
    public int trans_y = 0;
    public Inventory inventory;
    public int bomb = 100;
    public int magnet = 100;
    public int key = 0;
    public boolean useBomb = false;
    public boolean useMagnet = false;


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

    public void characterReborn(int x, int y, boolean reset){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<reborn");
        this.getCharacter().top= y - this.getCharacter().height;
        this.getCharacter().left=x;
        if (reset) {
            trans_x = 0;
            trans_y = 0;
        } else {
            trans_x = -(structures.get(cur_frame).length - point.x);

        }
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
        System.out.println("ladder up");
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
