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
    public int top=100;
    public int left=100;
    public int width;
    public int height;

    // Movement
    double scale;
    double thrust;
    double gravity;
    double velocityX;
    double velocityY;
    double maxVelocity;



    public int state=1;
    // 0: idle
    // 1: run

    // drawable

    public Character(Context context, GameModel model, int width, int height){
        this.context=context;
        this.model=model;
        this.width=width;
        this.height=height;

        //double scale = 60.0 / model.fps;
        this.scale = 1;
        this.thrust = 0.25 * scale;
        this.gravity = 0.01 * scale;
        this.velocityX = 0;
        this.velocityY = 0;
        this.maxVelocity = 0.5;

    }

    // factory method
    public Character CharacterFactory(){

        return null;
    }

    // movement method
    public void thrustUp() {
        velocityY -= 3 * thrust;
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


    public void draw(Canvas canvas){
        velocityY += gravity;

        // finally update the position
        top = top + (int) velocityY;
        left = left + (int) velocityX;
    }
}
