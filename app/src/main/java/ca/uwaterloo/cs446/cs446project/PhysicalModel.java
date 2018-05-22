package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

abstract public class PhysicalModel extends SurfaceView{
    public Bitmap background;
    protected Context context;

    public ArrayList<Rect> src = new ArrayList<>();
    public ArrayList <Rect> dest = new ArrayList<>();

    public PhysicalModel(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest){
        super(context);

        this.context=context;
        this.background = background;
        this.src = src;
        this.dest = dest;
    }


   // abstract public void draw (Canvas c);

}
