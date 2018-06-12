package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;



/**
 * Created by ethan on 2018-05-15.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public MainThread thread;
    public GameModel model;
    Display display;
    int fps = 60;
    boolean temp = true;

    public GameView(Context context, Display d, GameModel model){
        super(context);
        getHolder().addCallback(this);
        display = d;
        this.model=model;

        thread=new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        //model=new GameModel(this.getContext(), display,fps);

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
        
        if(canvas!=null){
            // draw all the components here

            canvas.drawBitmap(model.backgroud,0,0,null);

            //drawing current frame
            model.structures.get(model.cur_frame).draw(canvas);

            if (temp) {
                temp = false;
                model.structures.get(model.cur_frame).useMagnet(1300, p.y - 450);
            }

            for(Character c: model.characters){
                c.draw(canvas);
            }

            for(UI ui: model.uis){
                ui.draw(canvas);
            }
            //model.optionalDraw(0, canvas); draw ui
            //model.optionalDraw(1,canvas); draw characters

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = 0;
        Rect hitBox=new Rect(model.getCharacter().left,
                model.getCharacter().top,
                model.getCharacter().left+model.getCharacter().width,
                model.getCharacter().top+model.getCharacter().height);

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                //System.out.println("action down");
                for(UI ui: model.uis){
                    if(ui.hitTest(event.getX(), event.getY())){
                        ui.setSelected(true);

                        if(ui.name=="LeftButton"){
                            System.out.println("left button clicked");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT) == HitType.NULL &&
                                    model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN ) {
                                model.left();
                            }else{
                                System.out.println("hit left wall");
                                model.getCharacter().stopX();
                            }
                            return true;
                        }else if(ui.name=="RightButton"){
                            System.out.println("right button clicked");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT) == HitType.NULL &&
                                    model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN) {
                                model.right();

                            }else{
                                System.out.println("hit right wall");
                                model.getCharacter().stopX();
                            }
                            return true;
                        }else if(ui.name=="UpButton"){
                            System.out.println("up button clicked");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitTools(hitBox)==HitType.LADDER){
                                //&&model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN)
                                //model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.UP) == HitType.NULL

                                model.up();
                            }
                            else{
                                System.out.println("hit ceiling");
                                model.getCharacter().stopY();
                            }
                            return true;
                        }else if(ui.name=="DownButton"){
                            System.out.println("down button clicked");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitTools(hitBox)==HitType.LADDER) {
                                //model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.NULL
                                model.down();
                            }else{
                                System.out.println("hit floor");
                                model.getCharacter().stopY();
                            }

                            return true;
                        }else if(ui.name=="JumpButton"){
                            System.out.println("jump button clicked");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN) {
                                model.jump();
                                model.getCharacter().jump=true;
                            }

                            return true;
                        }else if(ui.name == "Backpack"){
                            System.out.println("backpack clicked");
                            ui.setSelected(true);
                            model.inventory.animation();
                            return true;

                        }else if(ui.name == "Inventory"){
                            model.inventory.clicked(event.getX());

                        }
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                pointerIndex ++;
                System.out.println("multi touch, index = " + pointerIndex);
                System.out.println("Event (X,Y) = " + event.getX(pointerIndex) + ", " + event.getY(pointerIndex));
                System.out.println("Jump  (X,Y) = " + model.getUI("JumpButton").x + ", " + model.getUI("JumpButton").y);

                for(UI ui: model.uis){
                    if(ui.hitTest(event.getX(pointerIndex), event.getY(pointerIndex))){
                        ui.setSelected(true);
                        if(ui.name=="JumpButton"){
                            System.out.println("jump button clicked in multi touch");
                            ui.setSelected(true);
                            if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN) {
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
                            model.characters.get(0).stopX();
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "RightButton") {
                            System.out.println("right button released");
                            ui.setSelected(false);
                            //model.right_release();
                            model.characters.get(0).stopX();
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "UpButton") {
                            System.out.println("up button released");
                            ui.setSelected(false);
                            model.characters.get(0).stopY();
                            //model.characters.get(0).state=0;
                            return true;
                        } else if (ui.name == "DownButton") {
                            System.out.println("down button released");
                            ui.setSelected(false);
                            model.characters.get(0).stopY();
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
        model.gravitySwitch(true);
        Rect hitBox=new Rect(model.getCharacter().left,
                model.getCharacter().top,
                model.getCharacter().left+model.getCharacter().width,
                model.getCharacter().top+model.getCharacter().height);

        if(model.getCharacter().top==model.point.y - model.getCharacter().height){
            model.characterReborn(100,50);
        }


        if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT) == HitType.LEFT)
            System.out.println("hit left");

        if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT) == HitType.RIGHT)
            System.out.println("hit right");

        if(model.structures.get(model.cur_frame).hitTools(hitBox)==HitType.LADDER)
            System.out.println("on ladder");

        // hit floor
        if((model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.DOWN) == HitType.DOWN
                ||model.structures.get(model.cur_frame).hitTools(hitBox)==HitType.LADDER)
                && !model.getCharacter().jump) {
            model.gravitySwitch(false);
            model.getCharacter().stopY();
            System.out.println("on floor");

            // in such case, height will be left/right floor
//          if(!(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT) == HitType.LEFT
//                    ||model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT) == HitType.RIGHT)){
//                System.out.println("On floor: Set Y");
//                model.getCharacter().setY(model.structures.get(model.cur_frame).floorHeight);
//            }

        }else{
            model.gravitySwitch(true);
            //model.getCharacter().stopY();
            System.out.println("in air");
        }

        //hit ceiling, stop Y
        if((model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.UP) == HitType.UP)){
            model.getCharacter().stopY();
            System.out.println("hit ceiling");
            model.gravitySwitch(true);

        }

        if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.LEFT)==HitType.LEFT
                &&model.getCharacter().state==MoveType.LEFT){
            model.getCharacter().stopX();
        }

        if(model.structures.get(model.cur_frame).hitFloor(hitBox, HitType.RIGHT)==HitType.RIGHT
                &&model.getCharacter().state==MoveType.RIGHT){
            model.getCharacter().stopX();
        }
        //System.out.println("Gravity Status: "+model.getCharacter().cur_gravity);

        model.update();
    }
}
