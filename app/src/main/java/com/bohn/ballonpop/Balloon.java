package com.bohn.ballonpop;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


import java.util.Random;

/**
 * Created by bohn on 15-05-2015.
 */
public class Balloon extends GameObject {
    private int color;
    private int xStart;
    private Path balloonshape;

    public Balloon (int x, int y) {
        super.x = x;
        super.y = y;
        super.width = 45;
        super.height = 110;
        balloonshape = new Path();
        Random rand = new Random();
        xStart = rand.nextInt(GamePanel.WIDTH);
        //color = Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
        color = Color.RED;
        super.dy = rand.nextInt(3)+2;
    }

    public void update() {
        super.x = xStart+(int)balloonMovement(super.y);
        super.y -= super.dy;
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

        //Draw string
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        //draw hitbox
        //canvas.drawRect(this.x - this.width, this.y, this.x + this.width, this.y - this.height,paint);

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

    public Path getPath() {return balloonshape;}

    public int getColor() {
        return color;
    }

}
