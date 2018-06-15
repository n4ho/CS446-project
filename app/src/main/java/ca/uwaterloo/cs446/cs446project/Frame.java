
package ca.uwaterloo.cs446.cs446project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.WindowDecorActionBar;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by julialiu on 2018-05-20.
 */

public class Frame {

    ArrayList <PhysicalModel> floors = new ArrayList<>();
    Point point;
    public int frame_num;
    int floorHeight;
    Context context;
    public int length;
    public int startx;
    public int starty;
    public int endx;
    public int endy;
    Bitmap ground;
    Bitmap spike_up;
    Bitmap spike_left;
    Bitmap spike_down;
    Bitmap ladder;
    Bitmap wraith1;
    ArrayList <Bitmap> wraith;
 //   Bitmap rock;
    Bitmap key;
    Bitmap door;
    Bitmap background;
    Bitmap magnet;
    Bitmap bomb;
    Bitmap fallingSpike;
    Bitmap water;
  //  Bitmap cage;
    Bitmap branch;
    Bitmap log;
    imageSource is;



    public Bitmap compress(Context context, int image, int compressRate){

        InputStream is = context.getResources().openRawResource(+ image);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = compressRate;
        return BitmapFactory.decodeStream(is,null,opt);
    }


    public Frame (int num, Point point, Context context) {

        this.context = context;
        frame_num = num;
        this.point = point;
        ArrayList <Rect> src = new ArrayList<>();
        ArrayList <Rect> dest = new ArrayList<>();
        is = imageSource.getInstance(context, point.x*3, point.y);
        ground = is.ground;
        log = is.log;
        spike_up = is.spike_up;
        spike_left = is.spike_left;
        spike_down = is.spike_down;
        ladder = is.ladder;
        wraith1 = is.wraith1;
        wraith = is.wraith;
        //rock = is.rock;
        key = is.key;
        door = is.door;
        background = is.background;
        magnet = is.magnet;
        bomb = is.bomb;
        fallingSpike = is.fallingSpike;
        water =is.water;
       // cage = is.cage;
        branch = is.branch;

        if (num == 0) {
            background = is.frame1background;
            length = point.x*3;
            startx = 110;
            starty = point.y - 200;
            endx = length - 110;
            endy = point.y - 400;
            //first frame
            src.add(new Rect (0, 0, point.x, ground.getHeight()));
            dest.add( new Rect (0, point.y - 200, point.x+250, point.y));

            //second frame

            src.add(new Rect (0, 0, 200, ground.getHeight()));
            dest.add(new Rect (point.x+350, point.y - 350, point.x+550, point.y - 250));

            src.add(new Rect (300, 0, 750, ground.getHeight()));
            dest.add(new Rect (point.x+600, point.y-200, point.x+1050, point.y));

            src.add(new Rect (400, 0, 800, ground.getHeight()));
            dest.add(new Rect (point.x+1300, point.y-330, 2*point.x+500, point.y));

            //third frame

            src.add(new Rect (200, 0, 1000, ground.getHeight()));
            dest.add(new Rect (2*point.x+950, point.y - 300, 3*point.x, point.y));

            ArrayList<Rect> log_src = new ArrayList<>();
            ArrayList<Rect> log_dest = new ArrayList<>();
            log_src.add(new Rect(0, 0, log.getWidth(), log.getHeight()));
            log_dest.add(new Rect(2*point.x-100, point.y - 450, 2*point.x+500,point.y - 330));
            floors.add(new log(context, log, log_src, log_dest, 2*point.x-100, 2*point.x+500, 2));
            floors.add(new Floor(context, ground, src, dest));

        }

        else if (num == 1) {
            background = is.frame1background;
            length = point.x*3;
            startx = 110;
            starty = point.y - 250;
            endx = length - 110;
            endy = point.y - 220;
            //forth frame
            src.add(new Rect (0, 0, 400, ground.getHeight()));
            dest.add( new Rect (0, point.y-150, 400, point.y));

            src.add(new Rect (800, 0, 950, ground.getHeight()));
            dest.add( new Rect (475, point.y-250, 625, point.y));


            src.add(new Rect (0, 0, 250, ground.getHeight()));
            dest.add( new Rect (500, point.y - 520, 700, point.y - 450));

            src.add(new Rect (0, 0, 300, ground.getHeight()));
            dest.add( new Rect (50, point.y - 780, 350, point.y -700));

            src.add(new Rect (100, 0, 670, ground.getHeight()));
            dest.add( new Rect (550, point.y - 880, 970, point.y -800));

            src.add(new Rect (0, 0, point.x - 625, ground.getHeight()));
            dest.add( new Rect (625, point.y - 150, point.x+300, point.y ));

            src.add(new Rect (0, 0, 75, ground.getHeight()));
            dest.add( new Rect (850, point.y - 400, 1180, point.y ));

            src.add(new Rect (1400, 0, point.x, ground.getHeight()));
            dest.add( new Rect (1400, point.y - 580, point.x, point.y - 500 ));

            ArrayList<Rect> spike_src = new ArrayList<>();
            ArrayList<Rect> spike_dest = new ArrayList<>();
            spike_src.add(new Rect(0, 0, spike_up.getWidth(), spike_up.getHeight()));
            spike_dest.add(new Rect(1225, point.y - 280, 1600,point.y-130));

            //magnet
            ArrayList<Rect> magnet_src = new ArrayList<>();
            ArrayList<Rect> magnet_dest = new ArrayList<>();
            magnet_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight() ));
            magnet_dest.add(new Rect (800, point.y-1030 ,  950, point.y - 890 ));

            ArrayList<Rect> spike_src_left = new ArrayList<>();
            ArrayList<Rect> spike_dest_left = new ArrayList<>();
            spike_src_left.add(new Rect(0, 0, spike_left.getWidth(), spike_left.getHeight()));
            spike_dest_left.add(new Rect(800, point.y - 400, 850,point.y - 100));


            floors.add(new spike(context, spike_up, spike_src, spike_dest));
            floors.add(new spike(context, spike_left, spike_src_left, spike_dest_left));
            floors.add(new magnet(context, magnet, magnet_src, magnet_dest, point.y-950, 800));

            //fifth framef

            src.add(new Rect (500, 0, 1030, ground.getHeight()));
            dest.add( new Rect (point.x+430, point.y - 270, point.x+900, point.y));

            src.add(new Rect (1000, 0, 1400, ground.getHeight()));
            dest.add( new Rect (point.x+1000, point.y - 200, point.x+1400, point.y));

            src.add(new Rect (1000, 0, 1400, ground.getHeight()));
            dest.add( new Rect (point.x+1300, point.y - 350, 2*point.x+300, point.y));

            src.add(new Rect (550, 0, 700, ground.getHeight()));
            dest.add( new Rect (point.x+550, point.y - 780, point.x+700, point.y-700));

            src.add(new Rect (850, 0, 1200, ground.getHeight()));
            dest.add( new Rect (point.x+850, point.y - 830, point.x+1200, point.y-750));

            src.add(new Rect (200, 0, 400, ground.getHeight()));
            dest.add( new Rect (point.x+1350, point.y - 680, point.x+1700, point.y-600));

            ArrayList<Rect> magnettwo_src = new ArrayList<>();
            ArrayList<Rect> magnettwo_dest = new ArrayList<>();
            magnettwo_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight() ));
            magnettwo_dest.add(new Rect (point.x+550, point.y-920 ,  point.x+700, point.y - 790 ));
            floors.add(new magnet(context, magnet, magnettwo_src, magnettwo_dest, point.y-920, point.x+550));

            ArrayList<Rect> bomb_src = new ArrayList<>();
            ArrayList<Rect> bomb_dest = new ArrayList<>();
            bomb_src.add(new Rect (0, 0, bomb.getWidth(), bomb.getHeight() ));
            bomb_dest.add(new Rect (point.x+900, point.y - 950, point.x+1000, point.y - 850 ));
            floors.add(new bomb(context, bomb, bomb_src, bomb_dest, point.y - 980, point.x+900));

            //ladder
            ArrayList<Rect> ladder_src = new ArrayList<>();
            ArrayList<Rect> ladder_dest = new ArrayList<>();
            ladder_src.add(new Rect (0, 0, ladder.getWidth(), ladder.getHeight() ));
            ladder_dest.add(new Rect (point.x+1550, point.y-1000, point.x+1650, point.y - 650 ));

            floors.add (new ladder(context, ladder, ladder_src, ladder_dest, 10, 0, point.y-650));

            //sixth frame

            src.add(new Rect (0, 0, 700, ground.getHeight()));
            dest.add( new Rect (2*point.x+400, point.y - 400, 2*point.x+850, point.y));

            src.add(new Rect (0, 0, 600, ground.getHeight()));
            dest.add( new Rect (2*point.x+1000, point.y - 220, 3*point.x, point.y));

            src.add(new Rect (150, 0, 400, ground.getHeight()));
            dest.add( new Rect (2*point.x+150, point.y - 670, 2*point.x+400, point.y-600));

            src.add(new Rect (500, 0, 600, ground.getHeight()));
            dest.add( new Rect (2*point.x+500, point.y - 720, 2*point.x+600, point.y-650));

            src.add(new Rect (300, 0, 700, ground.getHeight()));
            dest.add( new Rect (2*point.x+800, point.y - 780, 2*point.x+1100, point.y-700));

            src.add(new Rect (0, 0, point.x-1350, ground.getHeight()));
            dest.add( new Rect (2*point.x+1200, point.y - 620, 3*point.x, point.y-550));

            ArrayList<Rect> magnetthree_src = new ArrayList<>();
            ArrayList<Rect> magnetthree_dest = new ArrayList<>();
            magnetthree_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight() ));
            magnetthree_dest.add(new Rect (2*point.x+200, point.y-800,  2*point.x+320, point.y - 680 ));
            floors.add(new magnet(context, magnet, magnetthree_src, magnetthree_dest, point.y-800, 2*point.x+200));

            ArrayList<Rect> bomb_srctwo = new ArrayList<>();
            ArrayList<Rect> bomb_desttwo = new ArrayList<>();
            bomb_srctwo.add(new Rect (0, 0, bomb.getWidth(), bomb.getHeight() ));
            bomb_desttwo.add(new Rect (3*point.x-230, point.y - 700, point.x*3 - 140, point.y - 620 ));
            floors.add(new bomb(context, bomb, bomb_srctwo, bomb_desttwo, point.y - 700, 3*point.x-230));

            //ladder
            ArrayList<Rect> ladder_src1 = new ArrayList<>();
            ArrayList<Rect> ladder_dest1 = new ArrayList<>();
            ladder_src1.add(new Rect (0, 0, ladder.getWidth(), ladder.getHeight() ));
            ladder_dest1.add(new Rect (2*point.x+1250, point.y - 1200, 2*point.x+1350, point.y - 550 ));

            //wraith
            ArrayList<Rect> wraith_src = new ArrayList<>();
            ArrayList<Rect> wraith_dest = new ArrayList<>();
            wraith_src.add(new Rect (0, 0, wraith1.getWidth(), wraith1.getHeight() ));
            wraith_dest.add(new Rect (2*point.x+1300, point.y - 450, 2*point.x+1500, point.y - 200 ));

            floors.add(new wraith(context, wraith1, wraith_src, wraith_dest, 20, wraith, 2*point.x+1300, point.y-450));
            floors.add (new ladder(context, ladder, ladder_src1, ladder_dest1, 10, point.y - 1200, point.y-550));
            floors.add(new Floor(context, ground, src, dest));
        }

        else if (num == 2) {
            length = point.x*3;
            startx = 110;
            starty = point.y - 420;
            endx = length - 110;
            endy = point.y - 420;
            background = is.frame1background;
            //forth frame
            src.add(new Rect (0, 0, point.x, ground.getHeight()));
            dest.add( new Rect (point.x-800, point.y - 150, point.x*3, point.y));

            src.add(new Rect (0, 0, point.x, ground.getHeight()));
            dest.add( new Rect (0, point.y - 420, point.x+300, point.y-350));

            //magnet
            ArrayList<Rect> magnet_src = new ArrayList<>();
            ArrayList<Rect> magnet_dest = new ArrayList<>();
            magnet_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight() ));
            magnet_dest.add(new Rect (point.x-700, point.y-350 ,  point.x - 500, point.y - 150 ));

            //tumbler
          //  ArrayList<Rect> tumbler_src = new ArrayList<>();
           // ArrayList<Rect> tumbler_dest = new ArrayList<>();
            //tumbler_src.add(new Rect (0, 0, rock.getHeight()+500, rock.getHeight()+300));
            //tumbler_dest.add(new Rect (550, point.y-750 ,  900, point.y - 370 ));


            floors.add(new magnet(context, magnet, magnet_src, magnet_dest, point.y-200, point.x-500));


            //fifth frame

            src.add(new Rect (0,0, 800, ground.getHeight()));
            dest.add( new Rect (2*point.x - 800, point.y - 650, 2*point.x, point.y-600));

            src.add(new Rect (0, 0, 400, ground.getHeight()));
            dest.add( new Rect (2*point.x - 300, point.y-850, 2*point.x, point.y-800));

            //ladder
            ArrayList<Rect> ladder_src = new ArrayList<>();
            ArrayList<Rect> ladder_dest = new ArrayList<>();
            ladder_src.add(new Rect (0, 0, ladder.getWidth(), ladder.getHeight() ));
            ladder_dest.add(new Rect (2*point.x-750, 0, 2*point.x - 600, point.y - 550 ));

            //bomb
            ArrayList<Rect> bomb_src = new ArrayList<>();
            ArrayList<Rect> bomb_dest = new ArrayList<>();
            bomb_src.add(new Rect (0, 0, bomb.getWidth(), bomb.getHeight() ));
            bomb_dest.add(new Rect (2*point.x-200, 120, 2*point.x-100, 220 ));

            floors.add (new ladder(context, ladder, ladder_src, ladder_dest, 10, 0, point.y-550));

            //sixth frame

            ArrayList<Rect> wraith_src = new ArrayList<>();
            ArrayList<Rect> wraith_dest = new ArrayList<>();
            wraith_src.add(new Rect (0, 0, wraith1.getWidth(), wraith1.getHeight() ));
            wraith_dest.add(new Rect (2*point.x+point.x/2, point.y - 400, 2*point.x+point.x/2 + 200, point.y - 150 ));

            floors.add(new Floor(context, ground, src, dest));
            floors.add(new wraith(context, wraith1, wraith_src, wraith_dest, 20, wraith, 2*point.x+point.x/2, point.y-400));
            floors.add(new bomb(context, bomb, bomb_src, bomb_dest, 120 , 2*point.x - 200 ));

        }

        else if (num == 3) {

            background = is.frame1background;
            length = point.x*3;
            startx = 110;
            starty = point.y-250;
            endx = length - 110;
            endy = point.y - 830;
            //frame one
            src.add(new Rect (0, 0, point.x, ground.getHeight()));
            dest.add( new Rect (0, point.y - 250, point.x/3*2, point.y));

            ArrayList<Rect> wraith_src = new ArrayList<>();
            ArrayList<Rect> wraith_dest = new ArrayList<>();
            wraith_src.add(new Rect (0, 0, wraith1.getWidth(), wraith1.getHeight() ));
            wraith_dest.add(new Rect (600, point.y - 450, 800, point.y - 250 ));

            //ArrayList<Rect> cage_src = new ArrayList<>();
            //ArrayList<Rect> cage_dest = new ArrayList<>();
            //  cage_src.add(new Rect (0, 0, cage.getWidth(), cage.getHeight() ));
            // cage_dest.add(new Rect (1000, 200, 1300, 600 ));

            floors.add(new wraith(context, wraith1, wraith_src, wraith_dest, 20, wraith, 600, point.y-450));
            //floors.add(new cage(context, cage, cage_src, cage_dest, 5, 200, 500));


            src.add(new Rect (0, 0, point.x, ground.getHeight()));
            dest.add( new Rect (point.x*2-400, point.y - 250, point.x*2, point.y));


            //adding water
            ArrayList<Rect> water_src = new ArrayList<>();
            ArrayList<Rect> water_dest = new ArrayList<>();
            water_src.add(new Rect (0, 0, water.getWidth(), water.getHeight()));
            water_dest.add( new Rect (point.x/3*2, point.y - 150, point.x*2-400, point.y));

            Floor temp = new Floor(context, water, water_src, water_dest);
            temp.type = HitType.WATER;
            floors.add(temp);

            //add falling spike
            ArrayList<Rect> spike_src = new ArrayList<>();
            ArrayList<Rect> spike_dest = new ArrayList<>();
            spike_src.add(new Rect (0, 0, fallingSpike.getWidth(), fallingSpike.getHeight()));
            spike_dest.add( new Rect (point.x+300, 0, point.x+340, 40));
            floors.add(new fallingSpike(context,fallingSpike,spike_src,spike_dest, 10, point.x/3*2,2*point.x-400));

            //adding magnet
            ArrayList<Rect> magnet_src = new ArrayList<>();
            ArrayList<Rect> magnet_dest = new ArrayList<>();
            magnet_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight()));
            magnet_dest.add( new Rect (2*point.x-250, point.y-400, 2*point.x-100, point.y-250));
            floors.add(new magnet(context, magnet, magnet_src, magnet_dest, point.y-400,2*point.x-250) );

            src.add(new Rect (0, 0, 700, ground.getHeight()));
            dest.add( new Rect (2*point.x, point.y - 400, 2*point.x+400, point.y));

            src.add(new Rect (0, 0, 150, ground.getHeight()));
            dest.add( new Rect (2*point.x+550, point.y - 300, 2*point.x+900, point.y));

            src.add(new Rect (300, 0, 550, ground.getHeight()));
            dest.add( new Rect (2*point.x+1100, point.y - 300, 2*point.x+1300, point.y));

            src.add(new Rect (0, 0, 600, ground.getHeight()));
            dest.add( new Rect (2*point.x+1300, point.y - 400, 3*point.x - 150, point.y));

            src.add(new Rect (0, 0, 400, ground.getHeight()));
            dest.add( new Rect (2*point.x+250, point.y - 800, 2*point.x+650, point.y-750));

            src.add(new Rect (300, 0, 500, ground.getHeight()));
            dest.add( new Rect (2*point.x+750, point.y - 770,2*point.x+950,  point.y - 700));

            src.add(new Rect (0, 0, point.x - 1050, ground.getHeight()));
            dest.add( new Rect (2*point.x+1050, point.y - 830, point.x*3, point.y-750));

            ArrayList<Rect> door_src = new ArrayList<>();
            ArrayList<Rect> door_dest = new ArrayList<>();
            door_src.add(new Rect (0, 0, door.getWidth(), door.getHeight() ));
            door_dest.add(new Rect (point.x*3 - 370, point.y - 1050, point.x*3 - 250, point.y - 800 ));

            ArrayList<Rect> key_src = new ArrayList<>();
            ArrayList<Rect> key_dest = new ArrayList<>();
            key_src.add(new Rect (0, 0, key.getWidth(), key.getHeight() ));
            key_dest.add(new Rect (2*point.x+1500, point.y - 500, 2*point.x+1600, point.y - 400 ));

            ArrayList<Rect> spiketwo_src = new ArrayList<>();
            ArrayList<Rect> spiketwo_dest = new ArrayList<>();
            spiketwo_src.add(new Rect (0, 0, spike_down.getWidth(), spike_down.getHeight() ));
            spiketwo_dest.add(new Rect (2*point.x+550, point.y - 750, 2*point.x+650, point.y - 690 ));

            //ladder
            ArrayList<Rect> ladder_src = new ArrayList<>();
            ArrayList<Rect> ladder_dest = new ArrayList<>();
            ladder_src.add(new Rect (0, 0, ladder.getWidth(), ladder.getHeight() ));
            ladder_dest.add(new Rect (2*point.x+270, -100, 2*point.x+370, point.y - 700));


            floors.add(new key(context, key, key_src, key_dest, 2*point.x+1400, point.y - 520));
            floors.add(new spike(context, spike_down, spiketwo_src, spiketwo_dest));
            floors.add(new door(context, door, door_src, door_dest));
            floors.add(new Floor(context, ground, src, dest));
            floors.add(new ladder(context, ladder, ladder_src, ladder_dest, 10, -100, point.y - 700));


        }


        /*
        else if (num == 6) {

            backgrounds.add(ground);
            src.add(new Rect(0, 0, 400, ground.getHeight()));
            dest.add(new Rect(0, point.y - 400, 250, point.y));

            src.add(new Rect(0, 0, point.x, ground.getHeight()));
            dest.add(new Rect(point.x - 300, point.y - 400, point.x, point.y));

            floors.add(new Floor(context, ground, src, dest));

            //adding water
            ArrayList<Rect> water_src = new ArrayList<>();
            ArrayList<Rect> water_dest = new ArrayList<>();
            water_src.add(new Rect(0, 0, water.getWidth(), water.getHeight()));
            water_dest.add(new Rect(250, point.y - 300, point.x - 300, point.y));


            //Adding two island
            ArrayList<Rect> island1_src = new ArrayList<>();
            ArrayList<Rect> island1_dest = new ArrayList<>();
            island1_src.add(new Rect(0, 0, ground.getWidth(), ground.getHeight()));
            island1_dest.add(new Rect(500, point.y - 300, 700, point.y - 200));

            ArrayList<Rect> island2_src = new ArrayList<>();
            ArrayList<Rect> island2_dest = new ArrayList<>();
            island2_src.add(new Rect(0, 0, ground.getWidth(), ground.getHeight()));
            island2_dest.add(new Rect(1050, point.y - 300, 1250, point.y - 200));

            floors.add(new island(context, ground, island1_src, island1_dest, 5, point.y - 300));
            floors.add(new island(context, ground, island2_src, island2_dest, 5, point.y - 300));

            Floor temp = new Floor(context, water, water_src, water_dest);
            temp.type = HitType.WATER;
            floors.add(temp);
        }

        else if (num == 7) {
            backgrounds.add(ground);
            //adding floor
            src.add(new Rect(0, 0, 400, ground.getHeight()));
            dest.add(new Rect(0, point.y - 300, 500, point.y));

            src.add(new Rect(0, 0, 400, ground.getHeight()));
            dest.add(new Rect(1100, point.y - 600, point.x, point.y));
            floors.add(new Floor(context, ground, src, dest));

            //adding water
            ArrayList<Rect> water_src = new ArrayList<>();
            ArrayList<Rect> water_dest = new ArrayList<>();
            water_src.add(new Rect(0, 0, water.getWidth(), water.getHeight()));
            water_dest.add(new Rect(500, point.y - 300, 1100, point.y));
            Floor temp = new Floor(context, water, water_src, water_dest);
            temp.type = HitType.WATER;
            floors.add(temp);

            //adding branch
            ArrayList<Rect> branch_src = new ArrayList<>();
            ArrayList<Rect> branch_dest = new ArrayList<>();
            branch_src.add(new Rect(20, 0, branch.getWidth(), branch.getHeight()));
            branch_dest.add(new Rect(0, point.y - 900, 500, point.y-500));
            floors.add(new Branch(context,branch, branch_src, branch_dest));

            //adding Ball
            floors.add(new Ball(context,ground, branch_src, branch_dest, 20,300, point.y-600, point.y-400));



        }*/

    }

    public void draw (Canvas canvas) {

        canvas.drawBitmap(background, 0, 0, null);

        for (PhysicalModel p : floors) {
            if (p instanceof bomb && ((Tool) p).count_down == 0 ) {
                if (((Tool) p).state == 2) {
                    floors.remove(p);
                    continue;
                }
                else {
                    ((Tool) p).state = 2;
                    ((Tool) p).count_down = 40;

                }
            }

            if (p instanceof bomb && ((bomb) p).state == 2) {
                bombHitWraith(((bomb) p).left, ((bomb)p).top);
            }
            else if (p instanceof  magnet && ((Tool)p).state == 1) {
                if (((magnet) p).count_down == 0) {
                    floors.remove(p);
                }
                else {
                    attractLadder(((magnet) p).left);
                }
            }
            p.draw(canvas);
        }


    }

    //for floor hit test, return LEFT, RIGHT, UP, DOWN
    public HitType hitFloor(Rect rect, HitType type) {
        for (int i = 0; i < floors.size(); i++) {
            PhysicalModel p = floors.get(i);
            if ((p instanceof Floor || p instanceof log) && p.hitModel(rect, type) != HitType.NULL) {
                if (type == HitType.DOWN) {
                    floorHeight = ((Floor) floors.get(i)).getFloorHeight();
                }
                return type;
            }
        }
        return HitType.NULL;
    }

    //for dynamic model or tools hit test (ladder, bomb, magnet, wraith, etc), returns the type of hit item
    public HitType hitTools(Rect rect) {
        HitType result = HitType.NULL;
        HitType temp = HitType.NULL;
        for (int i = 0; i < floors.size(); i++) {

            temp = floors.get(i).hitModel(rect);
            if (result == HitType.NULL) {
                result = temp;
                if (temp != HitType.NULL) {
                    if (floors.get(i) instanceof Tool) {
                        floors.remove(floors.get(i));
                    }
                }
            }
        }
        return result;
    }

    public void useBomb (int x, int y) {

        //bomb
        ArrayList<Rect> bomb_src = new ArrayList<>();
        ArrayList<Rect> bomb_dest = new ArrayList<>();
        bomb_src.add(new Rect (0, 0, bomb.getWidth(), bomb.getHeight() ));
        bomb_dest.add(new Rect (x-50, y-50, x+50, y+50 ));
        //   bomb_dest.add(new Rect(x-50, y-50, x+300, y+300));
        bomb b = new bomb(context, bomb, bomb_src, bomb_dest, y-50, x-50);

        //setting the count_down for the bomb
        b.set_count_down(60);

        //setting the state to bomb in use
        b.set_state(1);
        floors.add(b);
    }


    public void bombHitWraith(int left, int top) {
        for (PhysicalModel p : floors) {
            if (p instanceof wraith) {
                if (((wraith)p).hitWidth(left, top)) {
                    floors.remove(p);
                }
            }
        }
    }

    public void attractLadder (int left) {
        for (PhysicalModel p : floors) {
            if (p instanceof  ladder) {

                if (((ladder)p).hitLadder(left)) {
                    ((ladder)p).move();
                }

            }
        }
    }

    public void useMagnet(int x, int y) {

        //bomb
        ArrayList<Rect> magnet_src = new ArrayList<>();
        ArrayList<Rect> magnet_dest = new ArrayList<>();
        magnet_src.add(new Rect (0, 0, magnet.getWidth(), magnet.getHeight() ));
        magnet_dest.add(new Rect (x-75, y-75, x+75, y+75 ));

        magnet m = new magnet(context, magnet, magnet_src, magnet_dest, y-75, x-75);

        //setting the count_down for the bomb
        m.set_count_down(120);

        //setting the state to bomb in use
        m.set_state(1);
        floors.add(m);

    }

    public int pushLog() {
        for (PhysicalModel p : floors) {
            if (p instanceof log) {
                if (((log) p).canMove()) {
                    ((log) p).move();
                    return ((log) p).getMovingVelocity();
                } else {
                    return -1;
                }
            }
        }
        return -1;
    }


}