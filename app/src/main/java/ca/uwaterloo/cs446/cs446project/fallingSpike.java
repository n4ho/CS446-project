package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.ViewStructure;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by julialiu on 2018-06-03.
 */

public class fallingSpike extends DynamicObject {


    int count = 0;
    int right_lim;
    int left_lim;

    public fallingSpike(Context context, Bitmap background, ArrayList<Rect> src, ArrayList<Rect> dest, int moving_velocity,
                        int left_lim, int right_lim) {
        super(context, background, src, dest, moving_velocity);
        this.right_lim = right_lim;
        this.left_lim = left_lim;
        type = HitType.FALLING_SPIKE;
    }


    void move(int i) {
        dest.get(i).offset(0, moving_velocity);

    }

    @Override
    public void draw (Canvas c) {

        for (int i = 0; i < dest.size(); i++) {
            if (dest.get(i).bottom > 1000) {
                dest.remove(i);
                continue;
            }
            move(i);
            super.draw(c);
            c.drawBitmap(background, src.get(0), dest.get(i), null);

        }
        count++;
        if (count%15 == 0) {
            Random rand = new Random();
            int x = rand.nextInt(right_lim - left_lim) + left_lim;
            dest.add(new Rect(x,0, x+40, 40 ));
        }
    }



    @Override
    public void move() {

    }

}
