package com.bohn.ballonpop;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bohn on 18-05-2015.
 */
public class Explosion extends GameObject{
    ArrayList<ExplosionFragment> explosionFragments;
    public Explosion(int x, int y) {
        explosionFragments = new ArrayList<ExplosionFragment>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int r = rand.nextInt(4) + 1;

            // area of explosion
            int xA = x + rand.nextInt(20) - 10;
            int yA = y - 50 - rand.nextInt(50);

            super.dx = rand.nextInt(10) - 5;
            super.dy = rand.nextInt(10) - 5;
            explosionFragments.add(new ExplosionFragment(xA, yA, super.dx, super.dy, r));
        }
    }

    public void draw(Canvas canvas) {
        for(ExplosionFragment ef: explosionFragments) {
            ef.draw(canvas);
        }
    }
    public void update() {
        for(ExplosionFragment ef: explosionFragments) {
            ef.update();
        }
    }

    public boolean shouldRemove() {
        float sum = 0;
        for(ExplosionFragment ef: explosionFragments) {
            sum += ef.getR();
        }
        if (sum == 0) {
            return true;
        }
        return false;
    }

}
