package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import java.util.ArrayList;
/**
 * Created by julialiu on 2018-05-16.
 */

public class Floor extends StaticObject {



    public Floor(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest) {
        super(context, background, src, dest);
        type = HitType.FLOOR;


        }

        @Override
        public void draw (Canvas c) {
                super.draw(c);

               for (int i = 0; i < src.size(); i++) {
                    c.drawBitmap(background, src.get(i), dest.get(i), null);

                }




        }

    @Override
    public HitType hitModel(Rect rect, HitType type) {
        for (int i = 0; i < dest.size(); i++) {
            Rect curDest = dest.get(i);
            if (type == HitType.UP && curDest.bottom >= rect.top || type == HitType.DOWN && rect.bottom >= curDest.top
        || type == HitType.LEFT && rect.left <= curDest.right || type == HitType.RIGHT && rect.right >= curDest.right) {
                return type;
            }
        }
        return HitType.NULL;
    }

    @Override
    public HitType hitModel(Rect rect) {
        return HitType.NULL;
    }
}
