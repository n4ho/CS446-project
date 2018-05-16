package ca.uwaterloo.cs446.cs446project;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ethan on 2018-05-16.
 */

public class GameModel {
    public ArrayList<Character> characters;
    public ArrayList<PhysicalModel> structures;

    public GameModel(Context context){
        characters=new ArrayList<Character>();
        structures=new ArrayList<PhysicalModel>();

        characters.add(new Protagonist(context,this,100,100));
    }
}
