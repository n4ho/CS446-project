package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by julialiu on 2018-05-17.
 */

public class log extends DynamicObject {


    Bitmap log;
    Rect src;
    Rect dest;
    public log (Context context, Rect src, Rect dest, Bitmap l) {
        super(context);
        this.src = src;
        this.dest = dest;
        log = l;
    }
    @Override
    public void draw(Canvas c) {

        c.drawBitmap(log, src,dest, null);

    }

    public void set_x (int i) {
       // x = i;
    }
    public void set_y (int i) {
        //y = i;
    }
}
