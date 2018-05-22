package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by julialiu on 2018-05-21.
 */

abstract public class Tool extends PhysicalModel {

    int top;
    int left;

    public Tool(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest, int top, int left) {
        super(context, background, src, dest);
        this.top = top;
        this.left = left;
    }

    abstract void collect (Canvas c);
    abstract void use ();



}
