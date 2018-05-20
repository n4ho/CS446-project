package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class Protagonist extends Character {
    ArrayList<Bitmap> idle;
    ArrayList<Bitmap> run;

    private int drawIndex;
    private int changeRate=0; // change image per changeRate FPSs

    public Protagonist(Context context, GameModel model, int width, int height) {
        super(context, model, width, height);
        idle=new ArrayList<Bitmap>();
        run=new ArrayList<Bitmap>();

        idle.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle0));
        idle.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle1));

        run.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00001));
        run.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00002));
        run.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00003));
        run.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00004));
        run.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00005));
    }

    public void draw(Canvas canvas){
        this.left++;
        
        switch (this.state){
            case 0: // idle
                canvas.drawBitmap(idle.get(drawIndex),left,top, null);
            if(changeRate==0) {
                if (drawIndex >= idle.size() - 1) {
                    drawIndex = 0;
                } else {
                    drawIndex++;
                }
                changeRate=30;
            }else{
                changeRate--;
            }
                break;

            case 1: // run

                canvas.drawBitmap(run.get(drawIndex),left,top, null);
                if(changeRate==0) {
                    if (drawIndex >= run.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=15;
                }else{
                    changeRate--;
                }

                break;

            case 2: // jump

        }
    }
}
