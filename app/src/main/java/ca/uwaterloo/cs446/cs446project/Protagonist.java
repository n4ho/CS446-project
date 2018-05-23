package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import 	android.graphics.Matrix;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class Protagonist extends Character {
    ArrayList<Bitmap> idle;
    ArrayList<Bitmap> runRight;
    ArrayList<Bitmap> runLeft;
    ArrayList<Bitmap> climb;


    private int drawIndex;
    private int changeRate=0; // change image per changeRate FPSs

    public Protagonist(Context context, GameModel model, int width, int height) {
        super(context, model, width, height);
        idle=new ArrayList<Bitmap>();
        runRight=new ArrayList<Bitmap>();
        runLeft=new ArrayList<Bitmap>();
        climb=new ArrayList<>();

        idle.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle0));
        idle.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle1));

        runRight.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00001));
        runRight.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00002));
        runRight.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00003));
        runRight.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00004));
        runRight.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00005));

        runLeft.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00001)));
        runLeft.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00002)));
        runLeft.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00003)));
        runLeft.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00004)));
        runLeft.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00005)));

        climb.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00001)));
        climb.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00002)));
        climb.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00003)));
        climb.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00004)));
        climb.add(FlipBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00005)));


    }

    public static Bitmap FlipBitmap(Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1, source.getWidth()/2f, source.getHeight()/2f);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public void draw(Canvas canvas){
        //this.left++;

        super.draw(canvas);

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

            case 1: // runRight

                canvas.drawBitmap(runRight.get(drawIndex),left,top, null);
                if(changeRate==0) {
                    if (drawIndex >= runRight.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=15;
                }else{
                    changeRate--;
                }

                break;

            case 2: //run left
                canvas.drawBitmap(runLeft.get(drawIndex),left,top,null);
                if(changeRate==0) {
                    if (drawIndex >= runLeft.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=15;
                }else{
                    changeRate--;
                }

                break;

            case 3: // jump
                break;

            case 4: //up
                canvas.drawBitmap(climb.get(drawIndex),left,top,null);
                if(changeRate==0) {
                    if (drawIndex >= climb.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=15;
                }else{
                    changeRate--;
                }

                break;

            case 5: //down
                canvas.drawBitmap(climb.get(drawIndex),left,top,null);
                if(changeRate==0) {
                    if (drawIndex >= climb.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=15;
                }else{
                    changeRate--;
                }

                break;

            default:
                break;
        }
    }
}
