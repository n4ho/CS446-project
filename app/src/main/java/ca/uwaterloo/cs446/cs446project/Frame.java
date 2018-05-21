package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by julialiu on 2018-05-20.
 */

public class Frame {

    ArrayList <PhysicalModel> floors = new ArrayList<>();
    Point point;
    public static ArrayList <Bitmap> backgrounds = new ArrayList<>();
    public int frame_num;

    public Frame (int num, Point point, Context context) {

        frame_num = num;
        this.point = point;
        ArrayList <Rect> src = new ArrayList<>();
        ArrayList <Rect> dest = new ArrayList<>();
        Bitmap ground = BitmapFactory.decodeResource(context.getResources(),R.drawable.ground);
        Bitmap log = BitmapFactory.decodeResource(context.getResources(),R.drawable.log);
        Bitmap ladder = BitmapFactory.decodeResource(context.getResources(),R.drawable.ladder);

        if (num == 0) {
            //first frame
            backgrounds.add(ground);
            src.add(new Rect (0, ground.getHeight() - point.y, point.x, ground.getHeight()));
            dest.add( new Rect (0, 0, point.x, point.y));
            floors.add(new Floor(context, ground, src, dest));



        }
        else if (num == 1) {
            //second frame
            backgrounds.add(ground);
            src.add(new Rect (0, ground.getHeight() - point.y, 300, ground.getHeight()));
            dest.add( new Rect (0, 0, 250, point.y));
            src.add(new Rect (0, ground.getHeight() - 200, 200, ground.getHeight()-100));
            dest.add(new Rect (350, point.y - 350, 550, point.y - 250));
            src.add(new Rect (300, ground.getHeight() - point.y, 750, ground.getHeight()));
            dest.add(new Rect (600, 0, 1050, point.y));
            src.add(new Rect (400, ground.getHeight() - point.y, 800, ground.getHeight()));
            dest.add(new Rect (1300, -1100, point.x, point.y));
            floors.add(new Floor(context, ground, src, dest));



        }
        else if (num == 2) {
            //third frame
            backgrounds.add(ground);
            src.add(new Rect (0, ground.getHeight() - point.y, 750, ground.getHeight()));
            dest.add( new Rect (0, -1700, 750, point.y));
            src.add(new Rect (500, ground.getHeight() - point.y, 1000,
                   ground.getHeight()));
            dest.add(new Rect (1200, 0, point.x, point.y));

            ArrayList<Rect> log_src = new ArrayList<>();
            ArrayList<Rect> log_dest = new ArrayList<>();
            log_src.add(new Rect(0, 0, log.getWidth(), log.getHeight()));
            log_dest.add(new Rect(250, point.y/11*6, 300+log.getWidth()/2,
                    point.y/11*6+log.getHeight()/3));
            floors.add(new log(context, log, log_src, log_dest, 250, 300+log.getWidth()/2, 3));
            floors.add(new Floor(context, ground, src, dest));


        }

        else if (num == 3){
            //forth frame
            backgrounds.add(ground);
            src.add(new Rect (0, ground.getHeight() - point.y, 300, ground.getHeight()));
            dest.add( new Rect (0, 0, 300, point.y-350));
            src.add(new Rect (0, ground.getHeight() - point.y, point.x, ground.getHeight()));
            dest.add( new Rect (0, 0, point.x, point.y));
            src.add(new Rect (0, ground.getHeight() - point.y, 800, ground.getHeight()));
            dest.add( new Rect (point.x - 800, -200, point.x, point.y-600));
            src.add(new Rect (0, ground.getHeight() - point.y, 400, ground.getHeight()));
            dest.add( new Rect (point.x - 400, -100, point.x, point.y-800));


            ArrayList<Rect> ladder_src = new ArrayList<>();
            ArrayList<Rect> ladder_dest = new ArrayList<>();
            ladder_src.add(new Rect (0, 0, ladder.getWidth(), ladder.getHeight() ));
            ladder_dest.add(new Rect (point.x-750, 0, point.x - 600, point.y - 550 ));
            floors.add(new Floor(context, ground, src, dest));
            floors.add (new ladder(context, ladder, ladder_src, ladder_dest, 10, 0, point.y-550));
        }



    }

    public void draw (Canvas canvas) {

        for (PhysicalModel p : floors) {
            p.draw(canvas);
        }

    }


}
