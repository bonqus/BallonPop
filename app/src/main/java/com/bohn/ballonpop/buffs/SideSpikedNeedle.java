package com.bohn.ballonpop.buffs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bohn.ballonpop.GamePanel;

/**
 * Created by bohn on 20-05-2015.
 */
public class SideSpikedNeedle extends Buff {

    public  SideSpikedNeedle(int x, int y) {
        super.x = x;
        super.y = y;
        super.paint = new Paint();
        super.paint.setStyle(Paint.Style.FILL);
        super.paint.setAntiAlias(true);
        super.paint.setStrokeWidth(1);
    }

    public void draw(Canvas canvas) {
        super.paint.setColor(Color.BLACK);
        canvas.drawCircle(super.x, super.y, super.r, super.paint);
        super.paint.setColor(Color.rgb(199, 233, 245));
        canvas.drawLine(super.x, super.y - r + 10, super.x, super.y + r - 10, paint);
        canvas.drawCircle(super.x, super.y - r + 15, 5, paint);
    }

    public void activate(){
        GamePanel.needle.activateSpikes();
    }
}
