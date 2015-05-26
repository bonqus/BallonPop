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
    private float SpikeX, SpikeY, SpikeXend, SpikeYend, px, py;
    private float Spike1X, Spike1Y, Spike1Xend, Spike1Yend;
    private ArrayList<Region> spikeRegions;
    private ArrayList<RectF> Rects;

    public Needle() {
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
        canvas.drawCircle(this.startX, this.startY, 25, paint);

        // pivot circle
        canvas.drawCircle(pivotX, pivotY, 5, paint);


        pivotToEndLength = totalLength-pivotToStartLength;

        float relation = pivotToEndLength/pivotToStartLength;

        this.endX = -1*(startX-pivotX);
        this.endY = -1*(startY-pivotY);


        this.endX = (endX*relation) + pivotX;
        this.endY = (endY*relation) + pivotY;

        SideSpikes();



        canvas.drawLine(this.startX, this.startY, this.endX, this.endY, paint);
        canvas.drawLine(this.SpikeX, this.SpikeY, this.SpikeXend, this.SpikeYend, paint);
        canvas.drawLine(this.Spike1X, this.Spike1Y, this.Spike1Xend, this.Spike1Yend, paint);
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

    public void SideSpikes(){
        float slope = (this.startY-this.endY)/(this.startX-this.endX);
        float m = -1/slope;
        int l = 100;
        float r =(float) Math.sqrt(1+m*m);

        //Spike 1
        line_point(this.startX, this.startY, this.endX, this.endY, -50);
        SpikeX = px+l/r;
        SpikeY = py+(l*m)/r;
        SpikeXend = px-l/r;
        SpikeYend = py+(-l*m)/r;

        //Spike 2
        line_point(this.startX, this.startY, this.endX, this.endY, -100);
        Spike1X = px+l/r;
        Spike1Y = py+(l*m)/r;
        Spike1Xend = px-l/r;
        Spike1Yend = py+(-l*m)/r;
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

    public ArrayList getSpikeRegions(){
        Region clip = new Region(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        spikeRegions = new ArrayList<Region>();
        Rects = new ArrayList<RectF>();
        RectF b1 = new RectF(this.SpikeX, this.SpikeY, this.SpikeX+1, this.SpikeY+1);
        RectF b2 = new RectF(this.Spike1X, this.Spike1Y, this.Spike1X+1, this.Spike1Y+1);
        RectF b3 = new RectF(this.SpikeXend, this.SpikeYend, this.SpikeXend+1, this.SpikeYend+1);
        RectF b4 = new RectF(this.Spike1Xend, this.Spike1Yend, this.Spike1Xend+1, this.Spike1Yend+1);
        Rects.add(b1);
        Rects.add(b2);
        Rects.add(b3);
        Rects.add(b4);

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
