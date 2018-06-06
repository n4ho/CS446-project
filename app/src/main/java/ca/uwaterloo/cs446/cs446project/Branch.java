package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by julialiu on 2018-06-03.
 */

public class Branch extends StaticObject{


    public Branch(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest) {
        super(context, background, src, dest);

    }

    @Override
    public HitType hitModel(Rect rect) {
        return HitType.NULL;
    }

    @Override
    public HitType hitModel(Rect rect, HitType type) {
        return HitType.NULL;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(background, src.get(0), dest.get(0), null);
    }
}
