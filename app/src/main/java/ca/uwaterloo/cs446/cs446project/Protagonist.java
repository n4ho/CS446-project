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

    public Protagonist(Context context, GameModel model, int width, int height,int type) {
        super(context, model, width, height);
        idle=new ArrayList<Bitmap>();
        runRight=new ArrayList<Bitmap>();
        runLeft=new ArrayList<Bitmap>();
        climb=new ArrayList<>();

        if(type == 0){
            idle.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackidle),width,height,false));

            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun1),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun2),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun3),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun4),width,height,false));

            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun1),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun2),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun3),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackrun4),width,height,false)));

            climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.jackclimb),width,height,false));

        }
        else if (type ==1){
            idle.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momidle),width,height,false));

            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun1),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun2),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun3),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun4),width,height,false));

            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun1),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun2),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun3),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momrun4),width,height,false)));

            climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.momclimb),width,height,false));
        }
        else if (type ==2){
            idle.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadidle),width,height,false));

            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun1),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun2),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun3),width,height,false));
            runRight.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun4),width,height,false));

            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun1),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun2),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun3),width,height,false)));
            runLeft.add(FlipBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadrun4),width,height,false)));

            climb.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.dadclimb),width,height,false));
        }


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
                canvas.drawBitmap(idle.get(drawIndex),left,top, null);
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
                break;

            case RIGHT: // runRight
                canvas.drawBitmap(runRight.get(drawIndex),left,top, null);

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

                break;

            case LEFT: //run left
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

            case JUMP: // jump
                break;

            case UP: //up

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

            case DOWN: //down
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
