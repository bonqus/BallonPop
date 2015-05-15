package com.bohn.ballonpop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by bohn on 15-05-2015.
 */
public class Needle extends GameObject {

    private boolean down = false;
    private Bitmap spritesheet;
    private float angle;

    public Needle (Bitmap res)
    {
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2;

        spritesheet = res;
    }

    public void onTouch(MotionEvent event){
        int eventAction = event.getAction();

        float x = event.getX();
        float y = event.getY();

        if (eventAction == MotionEvent.ACTION_DOWN)
        {
            down = true;
        }
        else if (eventAction == MotionEvent.ACTION_UP)
        {
            down = false;
        }
        while (down)
        {
        }

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(spritesheet, x, y, null);
    }

}
