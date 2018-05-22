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

    // Boundbox for physical collision
    public int top=400;
    public int left=800;
    public int width;
    public int height;

    // Movement
    double scale;
    double thrust;
    double gravity;
    double velocityX;
    double velocityY;
    double maxVelocity;



    public int state=0;
    // 0: idle
    // 1: run
    // 2: run left
    // 3: jump

    // drawable

    public Character(Context context, GameModel model, int width, int height){
        this.context=context;
        this.model=model;
        this.width=width;
        this.height=height;

        this.scale = 60.0 / model.fps;
        this.thrust = 10 * scale;
        this.gravity = 2 * scale;
        this.velocityX = 0;
        this.velocityY = 0;
        this.maxVelocity = 15;

    }

    // factory method
    public Character CharacterFactory(){

        return null;
    }

    // movement method
    public void thrustUp() {
        // TO DO: thrust up only when character is standing on something
        velocityY -= thrust * 2.5;
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
    }

    public void update(){

        // apply gravity
        velocityY += gravity;

        // update position
        top = top + (int) velocityY;
        left = left + (int) velocityX;

        // TO DO: if character is standing on a surface set velocityY to 0
        if(top >= 800) velocityY = 0;

        // TO DO: if character is too far away from center of the screen, do transformation

        // these bounds is only for testing, should be updated later on
        if (top > 800) {
            top = 800;
            velocityY = 0;
        }
        if (top < 0){
            top = 0;
            velocityY= 0;
        }
        if (left > 1900) {
            left = 1900;
            velocityX = 0;
        }
        if (left < 0){
            left = 0;
            velocityX = 0;
        }

    }


    public void draw(Canvas canvas){
    }
}
