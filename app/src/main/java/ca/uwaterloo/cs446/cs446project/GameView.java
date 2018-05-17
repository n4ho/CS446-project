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
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-15.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    public GameModel model;
    Display display;


    public GameView(Context context, Display d){
        super(context);
        getHolder().addCallback(this);
        display = d;


        thread=new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        model=new GameModel(this.getContext(), display);

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
        super.draw(canvas);

        Point p = new Point ();
        display.getSize(p);
        canvas.scale((float)((float)p.x/(float)1600), (float)((float)p.y/(float)1000));

        Rect r = new Rect();
        r.set(0,0,p.x, p.y);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(r, paint);


        if(canvas!=null){
            // draw all the components here
            for(Character c: model.characters){
                c.draw(canvas);
            }

           for(PhysicalModel pp : model.structures) {

               pp.draw(canvas);
            }
            model.optionalDraw(0, canvas);
            model.optionalDraw(1,canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("action down");
                for(UI ui: model.uis){
                    if(ui.hitTest(event.getX(), event.getY())){
                        ui.setSelected(true);
                        if(ui.name=="LeftButton"){
                            System.out.println("left button clicked");
                            //model.characters.get(0).left-=10;
                            model.characters.get(0).thrustLeft();
                            model.characters.get(0).state=2;
                            return true;
                        }else if(ui.name=="RightButton"){
                            System.out.println("right button clicked");
                            //model.characters.get(0).left+=10;
                            model.characters.get(0).thrustRight();
                            model.characters.get(0).state=1;
                            return true;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("action up");
                for(UI ui: model.uis){
                    if(ui.name=="LeftButton"){
                        System.out.println("left button released");
                        model.characters.get(0).stopX();
                        //model.characters.get(0).state=0;
                        return true;
                    }else if(ui.name=="RightButton"){
                        System.out.println("right button released");
                        model.characters.get(0).stopX();
                        //model.characters.get(0).state=0;
                        return true;
                    }
                }

                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        model.update();
    }
}
