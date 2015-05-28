package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bohn on 15-05-2015.
 */
public class Needle {
    private Paint paint;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float pivotX;
    private float pivotY;
    private float totalLength;
    private float pivotToStartLength;
    private float pivotToEndLength;
    private boolean push;
    public boolean spikes, mirror, gun, smaller, longer;
    private float px, py;
    private ArrayList<Region> spikeRegions;
    private ArrayList<RectF> Rects;
    private ArrayList<Lines> lines;
    public ArrayList<Lines> needleGun;
    private CountDownTimer spikeCountDown, mirrorCountDown, gunCountDown, smallCountDown, longCountDown;

    public Needle() {
        lines = new ArrayList<>();
        needleGun = new ArrayList<>();
        longCountDown = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }

            @Override
            public void onFinish() {
                setTotalLength(getTotalLength()-150);
                longer = false;
            }
        };
        smallCountDown = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }

            @Override
            public void onFinish() {
                setTotalLength(getTotalLength()+150);
                smaller = false;
            }
        };
        gunCountDown = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }

            @Override
            public void onFinish() {
                gun = false;
            }
        };
        spikeCountDown = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }

            @Override
            public void onFinish() {
                spikes = false;
            }
        };
        mirrorCountDown = new CountDownTimer(10000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                return;
            }

            @Override
            public void onFinish() {
                mirror = false;
            }
        };
        this.paint = new Paint();
        this.paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        pivotX = GamePanel.WIDTH/2;
        pivotY = GamePanel.HEIGHT/2;
        totalLength = GamePanel.WIDTH/2;
        this.startX = pivotX;
        this.startY = pivotY-200;

    }

    public void touchMove(float x, float y){
        activateGun();
        if (distance(startX, pivotX, startY, pivotY) > distance(x, pivotX, y, pivotY)){
            push = true;
        }
        else { push = false; }

        this.startX = x;
        this.startY = y;
    }


    public void draw(Canvas canvas) {

        pivotToStartLength = FloatMath.sqrt((startX-pivotX) * (startX-pivotX) + (startY-pivotY) * (startY-pivotY));
        if (pivotToStartLength > totalLength) {
            startX = ((startX-pivotX)*(totalLength/pivotToStartLength))+pivotX;
            startY = ((startY-pivotY)*(totalLength/pivotToStartLength))+pivotY;

            pivotToStartLength = totalLength;
        }
        // fingers circle
        if (!mirror) {
            canvas.drawCircle(this.startX, this.startY, 25, paint);
        }
        // pivot circle
        canvas.drawCircle(pivotX, pivotY, 5, paint);

        pivotToEndLength = totalLength-pivotToStartLength;

        float relation = pivotToEndLength/pivotToStartLength;

        this.endX = -1*(startX-pivotX);
        this.endY = -1*(startY-pivotY);
        this.endX = (endX*relation) + pivotX;
        this.endY = (endY*relation) + pivotY;

        canvas.drawLine(this.startX, this.startY, this.endX, this.endY, paint);
        if (spikes) {
            sideSpikes();
            for (Lines l : lines) {
                canvas.drawLine(l.getX(), l.getY(), l.getX1(), l.getY1(), paint);
            }
        }

        for (Lines l : needleGun){
            l.update();
            canvas.drawLine(l.getX(), l.getY(), l.getX1(), l.getY1(), paint);
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2)+Math.pow(y2-y1, 2));
    }

    public int getLx() {
        return (int)endX;
    }

    public boolean getPush() {return push;}

    public int getLy() {
        return (int)endY;
    }

    public boolean onSpot() {
        return true;
    }

    public void shoot(){
        needleGun();
    }

    public void activateGun(){
        if (gun){
            gunCountDown.cancel();
        }
        gun = true;
        gunCountDown.start();
    }

    public void activateMirror(){
        if (mirror){
            mirrorCountDown.cancel();
        }
        mirror = true;
        mirrorCountDown.start();
    }

    public void activateSpikes(){
        if (spikes){
            spikeCountDown.cancel();
        }
        spikes = true;
        spikeCountDown.start();
    }

    public void activateSmaller(){
        if (longer){
            longer = false;
            setTotalLength(getTotalLength()-150);
            longCountDown.cancel();
        }
        else {
            if (smaller) {
                smallCountDown.cancel();
            }
            else {
                setTotalLength(getTotalLength()-150);
            }
            smaller = true;
            smallCountDown.start();
        }
    }

    public void activateLonger(){
        if (smaller){
            smaller = false;
            setTotalLength(getTotalLength()+150);
            smallCountDown.cancel();
        }
        else {
            if (longer) {
                longCountDown.cancel();
            }
            else {
                setTotalLength(getTotalLength()+150);
            }
            longer = true;
            longCountDown.start();
        }
    }

    public void sideSpikes(){
        float slope = (this.startY-this.endY)/(this.startX-this.endX);
        float m = -1/slope;
        int l = 100;
        float r =(float) Math.sqrt(1+m*m);
        lines = new ArrayList<>();
        if (longer){
            l = 140;
        }
        else if (smaller){
            l = 60;
        }
        //Spike 1
        line_point(this.startX, this.startY, this.endX, this.endY, -50);
        lines.add(new Lines(px+l/r, py+(l*m)/r, px-l/r, py+(-l*m)/r));

        //Spike 2
        line_point(this.startX, this.startY, this.endX, this.endY, -100);
        lines.add(new Lines(px + l / r, py + (l * m) / r, px - l / r, py + (-l * m) / r));

        if (mirror) {
            //Spike 3
            line_point(this.endX, this.endY, this.startX, this.startY, -50);
            lines.add(new Lines(px+l/r, py+(l*m)/r, px-l/r, py+(-l*m)/r));

            //Spike 4
            line_point(this.endX, this.endY, this.startX, this.startY, -100);
            lines.add(new Lines(px+l/r, py+(l*m)/r, px-l/r, py+(-l*m)/r));
        }
    }

    public void line_point(float x1, float y1, float x2, float y2, int distance){
        float vx = x2 - x1;
        float vy = y2 - y1;
        float mag = (float)Math.sqrt(vx*vx + vy*vy);
        vx /= mag;
        vy /= mag;
        px = (int)((float)x1 + vx * (mag + distance));
        py = (int)((float)y1 + vy * (mag + distance));
    }

    public void needleGun(){
        int len = -50;
        line_point(this.startX, this.startY, this.endX, this.endY, len);
        needleGun.add(new Lines(px, py, this.endX, this.endY));
        if (spikes){
            for (Lines l : lines){
                line_point(l.getX(), l.getY(), l.getX1(), l.getY1(), len);
                needleGun.add(new Lines(px, py, l.getX(), l.getY()));
                needleGun.add(new Lines(px, py, l.getX1(), l.getY1()));
            }
        }
        if (mirror){
            line_point(this.endX, this.endY, this.startX, this.startY, len);
            needleGun.add(new Lines(px, py, this.startX, this.startY));
        }
    }

    public ArrayList getSpikeRegions(){
        Region clip = new Region(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        spikeRegions = new ArrayList<Region>();
        Rects = new ArrayList<RectF>();
        if (gun){
            for (Lines l : needleGun){
                RectF b = new RectF(l.getX1(), l.getY1(), l.getX1()+1, l.getY1()+1);
                Rects.add(b);
            }
        }

        if (spikes) {
            for (Lines l : lines){
                RectF b = new RectF(l.getX(), l.getY(), l.getX()+1, l.getY()+1);
                RectF b1 = new RectF(l.getX1(), l.getY1(), l.getX1()+1, l.getY1()+1);
                Rects.add(b);
                Rects.add(b1);
            }
        }
        if (mirror){
            RectF b5 = new RectF(this.startX, this.startY, this.startX + 1, this.startY + 1);
            Rects.add(b5);
        }
        for (RectF r: Rects) {
            Path path = new Path();
            path.addRect(r, Path.Direction.CW);
            Region region1 = new Region();
            region1.setPath(path, clip);
            spikeRegions.add(region1);
        }

        return spikeRegions;
    }

/*
    Buff relevant stuff
            ;;;;;
            ;;;;;
            ;;;;;
            ;;;;;
            ;;;;;
          ..;;;;;..
           ':::::'
             ':`
*/

    public void setTotalLength(int length) {
        this.totalLength = length;
    }

    public int getTotalLength() {
        return (int)this.totalLength;
    }
}
