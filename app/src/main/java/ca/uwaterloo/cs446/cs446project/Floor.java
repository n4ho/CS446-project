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

    Bitmap p;
    int frame_num;



    public Floor(Context context, Bitmap pp, int n) {
        super(context);

        p = pp;
        frame_num = n;

        }

        @Override
        public void draw (Canvas c) {

                ArrayList<Rect> src = FloorConstant.srcs.get(frame_num);
                ArrayList<Rect> dest = FloorConstant.dests.get(frame_num);

                for (int i = 0; i < src.size(); i++) {
                    c.drawBitmap(p, src.get(i), dest.get(i), null);

                }

           // }

        }


}
