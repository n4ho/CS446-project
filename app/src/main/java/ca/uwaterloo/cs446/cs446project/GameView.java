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

public class GameView extends SurfaceView implements SurfaceHolder.Callback, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    public MainThread thread;
    public GameModel model;
    Display display;
    private GestureDetectorCompat mDetector;
    int fps = 15;


    public GameView(Context context, Display d){
        super(context);
        getHolder().addCallback(this);
        display = d;


        thread=new MainThread(getHolder(), this);

        mDetector = new GestureDetectorCompat(context,this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        model=new GameModel(this.getContext(), display,fps);

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

            //drawing current frame
            model.structures.get(model.cur_frame).draw(canvas);

           /* for (int i = 0; i < 200; i++) {
                ((ladder) model.structures.get(model.cur_frame).floors.get(1)).move();
                try {
                    wait(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                model.structures.get(model.cur_frame).floors.get(1).draw(canvas);
            }*/

            //((wraith) model.structures.get(model.cur_frame).floors.get(1)).whenBombed();

            model.optionalDraw(0, canvas);
            model.optionalDraw(1,canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("action down");
                for(UI ui: model.uis){
                    if(ui.hitTest(event.getX(), event.getY())){
                        ui.setSelected(true);
                        if(ui.name=="LeftButton"){
                            System.out.println("left button clicked");
                            model.left();
                            return true;
                        }else if(ui.name=="RightButton"){
                            System.out.println("right button clicked");
                            model.right();
                            return true;
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                System.out.println("action up");
                for(UI ui: model.uis){
                    if (ui.name == "LeftButton") {
                            System.out.println("left button released");
                            // if character in air, dont stop
                            model.characters.get(0).stopX();
                            //model.characters.get(0).state=0;
                            return true;
                    } else if (ui.name == "RightButton") {
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
        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        System.out.println("On DOwn");
        return false;
    }

    private static final int SWIPE_MIN_DISTANCE = 20;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 20;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,float velocityX, float velocityY) {
        System.out.println("On Fling");
        if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH){
                return false;
        }
        // swipe up
        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            System.out.println("swipe up");
            model.jump();
            return true;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        //System.out.println("On Long Press");
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        //System.out.println("On Scroll");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        //System.out.println("On Show Press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        //Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }

    public void update(){
        model.update();
    }
}
