package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by ethan on 2018-05-16.
 */

abstract public class PhysicalModel {
    protected Bitmap texture;
    protected Context context;

    public int worldLength = 10000;

    public PhysicalModel(Context context){

        this.context=context;
    }


    abstract public void draw (Canvas c);

}
