package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

/**
 * Created by QQ on 28-05-2015.
 */
public class lives extends GameObject {

    private int color;
    private Path balloonshape;
    private int xStart;

    public lives (int x, int y) {
        super.x = x;
        super.y = y;
        super.width = 30;
        super.height = 74;
        Random rand = new Random();
        xStart = rand.nextInt(GamePanel.WIDTH);
        balloonshape = new Path();
        color = Color.WHITE;
    }

    public double balloonMovement(double d) {
        return 0.1*(Math.pow(Math.sin((d/GamePanel.HEIGHT)*Math.PI),2)*d);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        Path path = new Path();
        path.moveTo(x, y);
        path.cubicTo(xStart + (int) balloonMovement(super.y + dy * 30), y + 70, xStart + (int) balloonMovement(super.y + dy * 20), y + 70, xStart + (int) balloonMovement(super.y + dy * 80), y + 118);
        paint.setStyle(Paint.Style.FILL);

        //Draw Balloon
        canvas.drawCircle(x,y,4,paint);
        balloonshape = getPath2D();
        canvas.drawPath(balloonshape, paint);
    }

    public Path getPath2D() {
        Path path = new Path();
        path.moveTo(x, y);
        path.cubicTo(x - 150, y - 150, x + 150, y - 150, x, y);
        return path;
    }
}
