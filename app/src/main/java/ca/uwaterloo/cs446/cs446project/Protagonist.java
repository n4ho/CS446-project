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

        idle.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle0),width,height,false));
        idle.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.idle1),width,height,false));

        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00001),width,height,false));
        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00002),width,height,false));
        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00003),width,height,false));
        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00004),width,height,false));
        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00005),width,height,false));
        runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00006),width,height,false));

        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00001),width,height,false)));
        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00002),width,height,false)));
        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00003),width,height,false)));
        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00004),width,height,false)));
        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00005),width,height,false)));
        runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.run00006),width,height,false)));

        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00001),width,height,false));
        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00002),width,height,false));
        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00003),width,height,false));
        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00004),width,height,false));
        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00005),width,height,false));
        climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.climb00001),width,height,false));


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
            case IDLE: // idle

            if(changeRate==0) {
                if (drawIndex >= idle.size() - 1) {
                    drawIndex = 0;
                } else {
                    drawIndex++;
                }
                changeRate=10;
            }else{
                changeRate--;
            }

                canvas.drawBitmap(idle.get(drawIndex),left,top, null);
                break;

            case RIGHT: // runRight

                if(changeRate==0) {
                    if (drawIndex >= runRight.size() - 1) {
                        drawIndex = 0;
                    } else {
                        drawIndex++;
                    }
                    changeRate=6;
                }else{
                    changeRate--;
                }

                canvas.drawBitmap(runRight.get(drawIndex),left,top, null);


                break;

            case LEFT: //run left

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

                canvas.drawBitmap(runLeft.get(drawIndex),left,top,null);

                break;

            case JUMP: // jump
                break;

            case UP: //up

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

                canvas.drawBitmap(climb.get(drawIndex),left,top,null);

                break;

            case DOWN: //down

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

                canvas.drawBitmap(climb.get(drawIndex),left,top,null);

                break;

            default:
                break;
        }
    }
}
