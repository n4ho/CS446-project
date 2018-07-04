package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
        //readModel();

        if(isGameView) {
            System.out.println("LOAD MODEL BITMAP!");
            ourInstance.uis.add(new UI("LeftButton",
                    compress(context,R.drawable.left),
                    compress(context,R.drawable.left),
                    (int) (point.x * 0.015), (int) (point.y * 0.8),
                    point.x / 20 , point.y / 15)
            );
            ourInstance.uis.add(new UI("RightButton",
                    compress(context,R.drawable.right),
                    compress(context,R.drawable.right),
                    (int) (point.x * 0.125), (int) (point.y * 0.8),
                    point.x / 20, point.y / 15)
            );

            ourInstance.uis.add(new UI("UpButton",
                    compress(context,R.drawable.up),
                    compress(context,R.drawable.up),
                    (int) (point.x * 0.075), (int) (point.y * 0.71),
                    point.y / 15, point.x / 20)
            );
            ourInstance.uis.add(new UI("DownButton",
                    compress(context,R.drawable.down),
                    compress(context,R.drawable.down),
                    (int) (point.x * 0.075), (int) (point.y * 0.88),
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

            ourInstance.characters.add(new Protagonist(context, ourInstance, 70, 100,0));
            ourInstance.characters.add(new Protagonist(context, ourInstance, 70, 100,1));
            ourInstance.characters.add(new Protagonist(context, ourInstance, 70, 100,2));
            ourInstance.current_char.add(0);
            ourInstance.current_char.add(1);
            ourInstance.current_char.add(2);



            for (int i = 0; i < 10; i++) {
                ourInstance.structures.add(new Frame(i, point, context));
            }


        }
    }

    static GameModel getInstance()
    {
        return ourInstance;
    }

    static void saveModel(){
        try {
            PrintWriter pw = new PrintWriter("game.txt");
            pw.close();
        }catch (Exception e){

        }

            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        ourInstance.context.openFileOutput("game.txt", Context.MODE_PRIVATE));
                ourInstance.max_frame=Math.max(ourInstance.max_frame,ourInstance.cur_frame);
                outputStreamWriter.write(Integer.toString(ourInstance.max_frame));
                outputStreamWriter.close();
            }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
    }

    static void readModel(){

        String ret = "";

        try {
            InputStream inputStream = ourInstance.context.openFileInput("game.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("read model", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("read model", "Can not read file: " + e.toString());
        }

        if(ret==""){
            ourInstance.max_frame=0;
            File directory = ourInstance.context.getFilesDir();
            File new_file =
                    new File(directory.getAbsolutePath() + File.separator +  "game.txt");
            try
            {
                new_file.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.d("Create File", "File exists?"+new_file.exists());

        }else {
            ourInstance.max_frame = Integer.parseInt(ret);
        }

    }

    static public Bitmap compress(Context context, int image){
        InputStream is = context.getResources().openRawResource(+ image);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = 1;
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

    public int cur_frame = 5;

    public int curlevel = 0;
    public int max_frame=0;

    public ArrayList<Integer> current_char;

    public int trans_x = 0;
    public int trans_y = 0;
    public Inventory inventory;
    public int bomb = 1;
    public int magnet = 2;
    public int key = 10 ;
    public boolean useBomb = false;
    public boolean useMagnet = false;
    public boolean go_back = false;


    public GameModel(){

        characters=new ArrayList<Character>();
        structures=new ArrayList<Frame>();
        uis=new ArrayList<UI>();
        current_char = new ArrayList<>();

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
        getCharacter().update();

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
        getCharacter().state=MoveType.LEFT;
        getCharacter().thrustLeft();
    }

    // left button released
    public void left_release(){
        // if character in air, velocity in X should not stop
        //characters.get(current_char).state=0;
        getCharacter().stopX();
    }

    // right button clicked
    public void right(){
        getCharacter().state=MoveType.RIGHT;
        getCharacter().thrustRight();
    }

    // right button released
    public void right_release(){
        // if character in air, velocity in X should not stop
        //characters.get(current_char).state=0;
        getCharacter().stopX();
    }


    // jump button: jump
    public void jump(){
        //characters.get(current_char).state=MoveType.JUMP;
        getCharacter().jump();
    }

    // up button: move up(when there is a ladder)
    public void up(){
        getCharacter().thrustUp();
        getCharacter().state=MoveType.UP;
        System.out.println("ladder up");
    }

    // down button: move up(when there is a ladder)
    public void down(){
        getCharacter().thrustDown();
        getCharacter().state=MoveType.DOWN;
    }

    public void gravitySwitch(boolean b){
        if(b){
            getCharacter().startGravity();
        }else{
            getCharacter().stopGravity();
        }
    }

    public Character getCharacter() {
        return characters.get(current_char.get(0));
    }

    public boolean haveSelectedCharacter(){
        return current_char.size() > 0;
    }

    public void unlockCharacter() {
        //to be implemented;

    }
}
