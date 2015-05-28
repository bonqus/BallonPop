package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Path;

import com.bohn.ballonpop.buffs.*;
import com.bohn.ballonpop.smartClasses.PositionSize;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bohn on 18-05-2015.
 */
public class ExplosionAndBuff extends GameObject{
    ArrayList<ExplosionFragment> explosionFragments;
    private Buff buff = null;
    private boolean activeBuff = false;



    // constructor
    public ExplosionAndBuff(int x, int y, int color) {
        explosionFragments = new ArrayList<ExplosionFragment>();
        Random rand = new Random();


        // chance of buff spawn
        int choose = rand.nextInt(100);
        if(choose >= 50) {
            activeBuff = true;
            if (choose > 90) {
                buff = new LongerNeedle(x, y);
            } else if (choose > 80) {
                buff = new ShorterNeedle(x, y);
            } else if (choose > 70) {
                buff = new NeedleGun(x, y);
            } else if (choose > 60) {
                buff = new LongerNeedle(x, y);
            } else if (choose >= 50) {
                buff = new LongerNeedle(x, y);
            }
        }


        // create all explosions elements
        for (int i = 0; i < 20; i++) {
            int r = rand.nextInt(4) + 1;

            // area of explosion
            int xA = x + rand.nextInt(20) - 10;
            int yA = y - 50 - rand.nextInt(50);

            super.dx = rand.nextInt(10) - 5;
            super.dy = rand.nextInt(10) - 5;
            explosionFragments.add(new ExplosionFragment(xA, yA, super.dx, super.dy, r, color));
        }
    }

    public void draw(Canvas canvas) {



        for(ExplosionFragment ef: explosionFragments) {
            ef.draw(canvas);
        }
        if(buff != null) {
            buff.draw(canvas);
        }

    }
    public void update() {

        for(ExplosionFragment ef: explosionFragments) {
            ef.update();
        }
        if(buff != null) {
            buff.update();
        }

    }

    public boolean shouldRemove() {
        float sum = 0;
        for(ExplosionFragment ef: explosionFragments) {
            sum += ef.getR();
        }
        if (sum == 0) {
            if (buff != null) {
                return buff.shouldRemove();
            } else {
                return true;
            }
        }
        return false;
    }

    public Path getPath() {
        return buff.getPath();
    }

    public boolean isBuffActive() {
        return activeBuff;
    }

    public void activateBuff() {
        buff.activate();
        buff.remove();
    }
}
