package ca.uwaterloo.cs446.cs446project;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.ViewStructure;
import android.widget.Button;


/**
 * Created by ethan on 2018-05-15.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    public ConnectedThread connectedThread;
    public GameModel model;
    Display display;
    int fps = 60;
    boolean temp = true;
    public boolean drawbegin = true;
    public boolean drawend = false;
    public boolean drawconver = false;
    public boolean cageonelock = true;
    public boolean cagetwolock = true;

    private BluetoothAdapter BA;
    BluetoothDevice pairedDevices;

    public GameView(Context context, Display d, GameModel model){
        super(context);
        getHolder().addCallback(this);
        display = d;
        this.model=model;

        thread=new MainThread(getHolder(), this);
        BA = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = BA.();

        connectedThread = new ConnectedThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        //model=new GameModel(this.getContext(), display,fps);
        connectedThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry=true;
        while (retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        canvas.translate(model.trans_x,model.trans_y);
        super.draw(canvas);

        Point p = new Point ();
        display.getSize(p);
        //canvas.scale((float)((float)p.x/(float)1600), (float)((float)p.y/(float)1000));

        Rect r = new Rect();
        r.set(0,0,p.x, p.y);
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        if(canvas!=null){


            if (drawbegin) {
                drawbegin = model.structures.get(model.cur_frame).drawBegin(canvas, p.x, p.y);
            }
            else if (drawend) {
                drawend = model.structures.get(model.cur_frame - 1).drawEnd(canvas, p.x, p.y);
                if (drawend == false && model.cur_frame == 9) {
                    model.go_back = true;
                }
            } else if (drawconver) {
                drawconver = model.structures.get(model.cur_frame).drawConver(canvas, p.x, p.y);
            } else {
                if (model.structures.size() > model.cur_frame) {
                    Frame temp = model.structures.get(model.cur_frame);
                    temp.draw(canvas);
                }
                for(Character c: model.characters){
                    if(c.char_frame == model.cur_frame) c.draw(canvas);
                }

                for(UI ui: model.uis){
                    ui.draw(canvas);
                }
            }
            //model.optionalDraw(0, canvas); draw ui
            //model.optionalDraw(1,canvas); draw characters

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = 0;
        Rect hitBox = new Rect(0,0,0,0);
        if(model.haveSelectedCharacter()){
            hitBox=new Rect(model.getCharacter().left,
                    model.getCharacter().top,
                    model.getCharacter().left+model.getCharacter().width,
                    model.getCharacter().top+model.getCharacter().height);
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:

                if (drawbegin || drawend || drawconver) {
                    if (drawend) {
                        model.structures.get(model.cur_frame-1).d.skipOne();
                    } else {
                        model.structures.get(model.cur_frame).d.skipOne();
                    }
                }

                else {
                    Boolean handled = false;
                    for (UI ui : model.uis) {
                        if (ui.hitTest(event.getX(), event.getY(), 45)) {
                            ui.setSelected(true);
                            handled = true;

                            if (ui.name == "LeftButton") {
                                //System.out.println("left button clicked");
                                ui.setSelected(true);
                                if ((model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT) == HitType.NULL &&
                                        (model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN || model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_DOWN) == HitType.LOG_DOWN)) ||
                                        model.structures.get(model.cur_frame).hitTools(hitBox) == HitType.LADDER) {
                                    model.left();
                                } else {
                                    model.getCharacter().stopX();
                                }
                                return true;
                            } else if (ui.name == "RightButton") {
                                System.out.println("right button clicked");
                                ui.setSelected(true);
                                //check hit log first;
                                if (model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_LEFT) != HitType.NULL) {
                                    model.getCharacter().pushinglog = true;
                                } else if ((model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT) == HitType.NULL &&
                                        (model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN || model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_DOWN) != HitType.NULL)) ||
                                        model.structures.get(model.cur_frame).hitTools(hitBox) == HitType.LADDER) {
                                    model.right();

                                } else {
                                    model.getCharacter().stopX();
                                }
                                return true;
                            } else if (ui.name == "UpButton") {
                                //System.out.println("up button clicked");
                                ui.setSelected(true);
                                if (model.structures.get(model.cur_frame).hitTools(hitBox) == HitType.LADDER) {
                                    //&&model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN)
                                    //model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.UP) == HitType.NULL

                                    model.up();
                                    model.getCharacter().climb = true;
                                } else {
                                    //System.out.println("hit ceiling");
                                    model.getCharacter().stopY();
                                }
                                return true;
                            } else if (ui.name == "DownButton") {
                                //System.out.println("down button clicked");
                                ui.setSelected(true);
                                if (model.structures.get(model.cur_frame).hitTools(hitBox) == HitType.LADDER) {
                                    //model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.NULL
                                    model.down();
                                    model.getCharacter().climb = true;
                                } else {
                                    //System.out.println("hit floor");
                                    model.getCharacter().stopY();
                                }

                                return true;
                            } else if (ui.name == "JumpButton") {
                                System.out.println("jump button clicked");
                                ui.setSelected(true);
                                if (model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN ||
                                        model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_DOWN) == HitType.LOG_DOWN) {
                                    model.jump();
                                    model.getCharacter().jump = true;
                                }

                                return true;
                            } else if (ui.name == "Backpack") {
                                //System.out.println("backpack clicked");
                                ui.setSelected(true);
                                model.inventory.animation();
                                return true;

                            } else if (ui.name == "Inventory") {
                                model.inventory.clicked(event.getX());
                            }

                        }

                        if (handled) break;
                        if (model.useBomb) {
                            model.structures.get(model.cur_frame).useBomb((int) event.getX() - model.trans_x, (int) event.getY());
                            model.useBomb = false;
                            model.bomb--;
                            break;
                        }
                        if (model.useMagnet) {
                            model.structures.get(model.cur_frame).useMagnet((int) event.getX() - model.trans_x, (int) event.getY());
                            model.useMagnet = false;
                            model.magnet--;
                            break;
                        }
                    }
                    for (int i = 0; i < model.characters.size(); i++) {
                        if (model.characters.get(i).hitTest(event.getX() - model.trans_x, event.getY() - model.trans_y, 0) == true &&
                                model.characters.get(i).char_frame == model.cur_frame) {
                            if (model.current_char.contains(i)) {
                                model.current_char.remove((Integer) i);
                            } else {
                                model.current_char.add(i);
                            }
                            break;
                        }
                    }

                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIndex ++;
                /*System.out.println("multi touch, index = " + pointerIndex);
                System.out.println("Event (X,Y) = " + event.getX(pointerIndex) + ", " + event.getY(pointerIndex));
                System.out.println("Jump  (X,Y) = " + model.getUI("JumpButton").x + ", " + model.getUI("JumpButton").y);
*/
                for(UI ui: model.uis){
                    if(ui.hitTest(event.getX(pointerIndex), event.getY(pointerIndex),90)){
                        ui.setSelected(true);
                        if(ui.name=="JumpButton"){
                            System.out.println("jump button clicked in multi touch");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN ||
                                    model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_DOWN) == HitType.LOG_DOWN) {
                                model.jump();
                                model.getCharacter().jump=true;
                            }

                            return true;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                //System.out.println("multi touch release");
                for(UI ui: model.uis) {
                    if (ui.selected) {
                        if (ui.name == "JumpButton") {
                            System.out.println("jump button released");
                            ui.setSelected(false);
                            model.getCharacter().jump = false;
                            return true;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("action up");
                for(UI ui: model.uis) {
                    if (ui.selected) {

                        if (ui.name == "LeftButton") {
                            System.out.println("left button released");
                            ui.setSelected(false);
                            // if character in air, dont stop
                            System.out.println("stop from 4");
                            model.getCharacter().stopX();
                            //model.characters.get(0).state=0;
                            model.getCharacter().pushinglog = false;
                            return true;
                        } else if (ui.name == "RightButton") {
                            System.out.println("right button released");
                            ui.setSelected(false);
                            //model.right_release();
                            System.out.println("stop from 3");
                            model.getCharacter().stopX();
                            // stop pushing log
                            model.getCharacter().pushinglog = false;
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "UpButton") {
                            System.out.println("up button released");
                            ui.setSelected(false);
                            model.getCharacter().stopY();
                            model.getCharacter().climb = false;
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "DownButton") {
                            System.out.println("down button released");
                            ui.setSelected(false);
                            model.getCharacter().stopY();
                            model.getCharacter().climb = false;
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "JumpButton") {
                            System.out.println("jump button released");
                            ui.setSelected(false);
                            model.getCharacter().jump = false;

                            return true;
                        }else if(ui.name == "Backpack"){
                            System.out.println("backpack released");
                            ui.setSelected(false);
                            // do something
                            return true;
                        }
                    }

                }
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        if (model.haveSelectedCharacter()){
            //model.gravitySwitch(true);
            Rect hitBox=new Rect(model.getCharacter().left,
                    model.getCharacter().top,
                    model.getCharacter().left+model.getCharacter().width,
                    model.getCharacter().top+model.getCharacter().height);

            if(model.getCharacter().top==model.point.y - model.getCharacter().height){
                model.characterReborn(100,50, true);
            }

            HitType hittool  = model.structures.get(model.cur_frame).hitTools(hitBox);
            if (hittool == HitType.MAGNET) {
                model.magnet ++;
            }
            if (hittool == HitType.KEY) {
                model.key ++;
            }
            if (hittool == HitType.BOMB) {
                model.bomb ++;
            }
            if (hittool == HitType.SPIKE || hittool == HitType.WRAITH) {
                model.characterReborn(100, 50, true);
            }

            if (hittool == HitType.CAGE) {
                if (model.cur_frame == 4 && cageonelock == true || model.cur_frame == 9 && cagetwolock == true) {
                    if (model.key > 0) {
                        model.key--;
                        model.unlockCharacter();
                        if (model.cur_frame == 4) {
                            cageonelock = false;
                        } else {
                            cagetwolock = false;
                        }
                    }
                }
            }

            if (hittool == HitType.DOOR) {
                if (model.key > 0) {
                    model.curlevel ++; model.key --;

                    if (model.cur_frame < 9) {
                        model.cur_frame++;
                        model.characterReborn(model.structures.get(model.cur_frame).startx, model.structures.get(model.cur_frame).starty, true);
                    }
                    else {
                        model.characterReborn(model.structures.get(model.cur_frame).startx, model.structures.get(model.cur_frame).starty, true);
                    }
                    this.drawend = true;
                }
            }

            if (hittool == HitType.FALLING_SPIKE) {
                model.characterReborn(100, 50, true);
            }

            model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.SPIKESENSOR);
            model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEVER);
            model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.SENSOR);
            boolean ladder = model.structures.get(model.cur_frame).hitTools(hitBox)==HitType.LADDER;
            boolean down = model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN;
            boolean up = model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.UP) == HitType.UP;
            boolean logdown = model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_DOWN) == HitType.LOG_DOWN;
            boolean logleft = model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LOG_LEFT) == HitType.LOG_LEFT;

            // down or on log, not jump, not ladder
            if ((down || logdown) && model.getCharacter().jump && model.getCharacter().velocityY > 0) {
                model.getCharacter().jump = false;
                model.gravitySwitch(false);
                model.getCharacter().stopY();
            }

            if((down || logdown) && !model.getCharacter().jump && !ladder) {
                model.gravitySwitch(false);
                model.getCharacter().stopY();
                if (down) model.getCharacter().setY(model.structures.get(model.cur_frame).floorHeight - model.getCharacter().height + 5);


            }else if (!ladder){
                model.gravitySwitch(true);
            }

            if(ladder){
                model.gravitySwitch(false);
                if(!model.getCharacter().climb) model.getCharacter().stopY();
            }


            // ceiling
            if(up && !ladder){
                if (model.getCharacter().velocityY < 0 )model.getCharacter().stopY();
                System.out.println("hit ceiling");
                model.gravitySwitch(true);
            }

            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT)==HitType.LEFT
                    &&model.getCharacter().state==MoveType.LEFT){
                model.getCharacter().stopX();
            }

            if (logleft && model.getCharacter().velocityX > 0) {
                model.getCharacter().pushinglog = true;
            }

            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT)==HitType.RIGHT
                    &&model.getCharacter().state==MoveType.RIGHT){
                model.getCharacter().stopX();
            }
        }
        model.update();
    }
}
