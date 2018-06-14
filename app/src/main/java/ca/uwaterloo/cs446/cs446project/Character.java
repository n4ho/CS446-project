package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Created by ethan on 2018-05-15.
 */

public class Character {
    private Context context;
    protected GameModel model;

    // Bounding box for physical collision
    public int top=700;
    public int left=50;
    public int width;
    public int height;

    // Movement
    double scale;
    double thrust;
    double gravity;
    double cur_gravity;
    double velocityX;
    double velocityY;
    double maxVelocity;
    boolean jump;
    boolean climb;

    public MoveType state=MoveType.RIGHT;

    public Character(Context context, GameModel model, int width, int height){
        this.context=context;
        this.model=model;
        this.width=width;
        this.height=height;

        this.scale = 60 / model.fps;
        this.thrust = 10 * scale;
        this.gravity = 2 * scale;
        this.cur_gravity = gravity;
        this.velocityX = 0;
        this.velocityY = 0;
        this.maxVelocity = 15;
        this.jump = false;
        this.climb = false;

    }

    // factory method
    public Character CharacterFactory(){

        return null;
    }

    // movement method
    public void jump(){
        velocityY -= thrust * 4;
        jump = true;
    }

    public void thrustUp() {
        velocityY -= thrust;
        if(velocityY > maxVelocity) velocityY = maxVelocity;
        if(velocityY < -maxVelocity) velocityY = -maxVelocity;
    }

    public void thrustDown() {
        velocityY += thrust;
        if(velocityY > maxVelocity) velocityY = maxVelocity;
        if(velocityY < -maxVelocity) velocityY = -maxVelocity;
    }

    public void thrustLeft() {
        velocityX -= thrust;
        if(velocityX > maxVelocity) velocityX = maxVelocity;
        if(velocityX < -maxVelocity) velocityX = -maxVelocity;
    }

    public void thrustRight() {
        velocityX += thrust;
        if(velocityX > maxVelocity) velocityX = maxVelocity;
        if(velocityX < -maxVelocity) velocityX = -maxVelocity;
    }

    public void stopX(){
        velocityX = 0;
        System.out.println("*******************stoping x");
    }

    public void stopY(){
        velocityY = 0;
    }

    public void setY(int floorHeight){
        top=floorHeight;
    }

    public void stopGravity(){ cur_gravity = 0;}

    public void startGravity(){ cur_gravity = gravity;}

    public void update(){

        // apply gravity
        velocityY += cur_gravity;

        // update position
        top = top + (int) velocityY;
        left = left + (int) velocityX;

        // if character is standing on the bottom of the screen
        if(top >= model.point.y - height) {
            stopY();
            top = model.point.y - height;
        }

        // if character is jumping too high
        if(top < 0){
            top = 0;
            stopY();
        }

        // TO DO: if character is too far away from center of the screen, do transformation

        if (left > model.structures.get(model.cur_frame).length - 100) {
            if (model.cur_frame < 9) {
                ++model.cur_frame;
                model.characterReborn(model.structures.get(model.cur_frame).startx, model.structures.get(model.cur_frame).starty, true);
            } else {
                stopX();
            }
        } else if (left < 100 && velocityX < 0) {
            if (model.cur_frame > 0) {
                model.cur_frame--;
                System.out.println("-----------------got here");
                model.characterReborn(model.structures.get(model.cur_frame).endx, model.structures.get(model.cur_frame).endy, false);
            } else {
                stopX();
            }
        } else if (left > model.point.x /3 * 2 - model.trans_x) {
            //left = model.point.x /7 * 6 - model.trans_x;
            //stopX();
            if (left >= model.structures.get(model.cur_frame).length - model.point.x /3 && velocityX > 0){}
            else if (velocityX < 0) {}
            else {
                model.trans_x -= velocityX;
            }
        } else if (left < model.point.x /3 - model.trans_x) {
            //left = model.point.x /7 * 6 - model.trans_x;
            //stopX();
            if (left <=  model.point.x /3 && velocityX < 0){}
            else if (velocityX < 0) {
                model.trans_x -= velocityX;
            }
        }
        /*else if (left < model.point.x /7 - model.trans_x) {
            if(model.trans_x >= 0){
                left = model.point.x/7 - model.trans_x;
                stopX();
            }
            else if(model.trans_x <= 0) model.trans_x -= velocityX;
        }*/


    }


    public void draw(Canvas canvas){
    }
}
