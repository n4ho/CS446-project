package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by julialiu on 2018-05-17.
 */

public class ladder extends DynamicObject {

    Bitmap ladder;


    public ladder(Context context) {
        super(context);
        ladder = BitmapFactory.decodeResource(context.getResources(),R.drawable.ladder);
    }

    @Override
    public void draw(Canvas c) {

       // c.drawBitmap(ladder, 500, 800, null);

    }

}
