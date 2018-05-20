package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class GameModel {
    public ArrayList<Character> characters;
    public ArrayList<PhysicalModel> structures;
    Display display;
    FloorConstant allFloors;
    Point point;

    public GameModel(Context context, Display d){
        characters=new ArrayList<Character>();
        structures=new ArrayList<PhysicalModel>();
        display = d;
        point = new Point();
        display.getSize(point);
        allFloors = new FloorConstant(context, point);

        characters.add(new Protagonist(context,this,100,100));

        structures.add(new log(context, FloorConstant.log3_src, FloorConstant.log3_dest, FloorConstant.log));
        for (int i = 0; i < FloorConstant.frames.size(); i++) {
            structures.add(new Floor(context, FloorConstant.frames.get(i), i));
        }

    }
}
