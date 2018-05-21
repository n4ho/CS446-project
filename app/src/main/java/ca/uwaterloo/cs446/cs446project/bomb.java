package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by julialiu on 2018-05-20.
 */

public class bomb extends DynamicObject {


    public bomb(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest, int moving_velocity) {
        super(context, background, src, dest, moving_velocity);

    }

    @Override
    public void move() {

    }

    @Override
    public void draw(Canvas c) {

    }
}
