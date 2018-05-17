package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);

        thread=new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        model=new GameModel(this.getContext());

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

        if(canvas!=null){
            // draw all the components here
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
                            model.characters.get(0).left-=10;
                        }else if(ui.name=="RightButton"){
                            System.out.println("right button clicked");
                            model.characters.get(0).left+=10;
                        }
                    }
                }

                break;

            case MotionEvent.ACTION_UP:

                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void update(){

    }
}
