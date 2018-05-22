package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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



        }

        @Override
        public void draw (Canvas c) {
                super.draw(c);
                Paint p = new Paint();
                p.setStyle(Paint.Style.FILL);
                p.setColor(Color.BLACK);


               for (int i = 0; i < src.size(); i++) {
                   Rect temp = new Rect (dest.get(i).left,
                           dest.get(i).top - background.getHeight(), dest.get(i).right, dest.get(i).top);

                   c.drawBitmap(background, src.get(i), temp, null);

                   c.drawRect(dest.get(i), p);

                }




        }


}
