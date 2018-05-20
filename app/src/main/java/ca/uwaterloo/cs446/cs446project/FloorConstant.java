package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by julialiu on 2018-05-16.
 */

public class FloorConstant {

    Point p;
    public static ArrayList <Bitmap> frames = new ArrayList<>();
    public static ArrayList <ArrayList <Rect>> srcs = new ArrayList<>();
    public static ArrayList <ArrayList<Rect>> dests = new ArrayList<>();
    public static Rect log3_src;
    public static Rect log3_dest;
    public static Bitmap log;


        public FloorConstant (Context context, Point point) {
            p = point;

            ArrayList <Rect>  src1 = new ArrayList<>();
            ArrayList <Rect> dest1 = new ArrayList<>();
            ArrayList <Rect>  src2 = new ArrayList<>();
            ArrayList <Rect> dest2 = new ArrayList<>();
            ArrayList <Rect>  src3 = new ArrayList<>();
            ArrayList <Rect> dest3 = new ArrayList<>();
            Bitmap levelone_ground = BitmapFactory.decodeResource(context.getResources(),R.drawable.ground);
            log = BitmapFactory.decodeResource(context.getResources(),R.drawable.log);


            //frame one
            /*frames.add(levelone_ground);
            srcs.add(src1);
            dests.add(dest1);
            src1.add(new Rect (0, levelone_ground.getHeight() - point.y, point.x, levelone_ground.getHeight()));
            dest1.add( new Rect (0, 0, point.x, point.y));*/


            //frame two
            /*frames.add(levelone_ground);
            srcs.add(src2);
            dests.add(dest2);
            src2.add(new Rect (0, levelone_ground.getHeight() - point.y, 300, levelone_ground.getHeight()));
            dest2.add( new Rect (0, 0, 250, point.y));
            src2.add(new Rect (0, levelone_ground.getHeight() - 200, 200, levelone_ground.getHeight()-100));
            dest2.add(new Rect (350, point.y - 350, 550, point.y - 250));
            src2.add(new Rect (300, levelone_ground.getHeight() - point.y, 750, levelone_ground.getHeight()));
            dest2.add(new Rect (600, 0, 1050, point.y));
            src2.add(new Rect (400, levelone_ground.getHeight() - point.y, 800, levelone_ground.getHeight()));
            dest2.add(new Rect (1300, -1100, point.x, point.y));*/


            //frame three
            frames.add(levelone_ground);
            srcs.add(src3);
            dests.add(dest3);
            src3.add(new Rect (0, levelone_ground.getHeight() - point.y, 750, levelone_ground.getHeight()));
            dest3.add( new Rect (0, -1700, 750, point.y));
            src3.add(new Rect (500, levelone_ground.getHeight() - point.y, 1000,
                    levelone_ground.getHeight()));
            dest3.add(new Rect (1200, 0, point.x, point.y));

            log3_src = new Rect(0, 0, log.getWidth(), log.getHeight());
            log3_dest = new Rect(250, point.y/11*6, 300+log.getWidth()/2,
                    point.y/11*6+log.getHeight()/3);






        }


}
