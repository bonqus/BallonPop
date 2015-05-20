package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public Needle() {
        this.paint = new Paint();
        this.paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        pivotX = GamePanel.WIDTH/2;
        pivotY = GamePanel.HEIGHT/2;
        totalLength = GamePanel.WIDTH/2;
        this.startX = pivotX;
        this.startY = pivotY-200;

    }

    public void touchMove(float x, float y){
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





        canvas.drawLine(this.startX, this.startY, this.endX ,this.endY , paint);
    }

    public void setTotalLength(int length) {
        this.totalLength = length;
    }

    public int getLx() {
        return (int)endX;
    }

    public int getLy() {
        return (int)endY;
    }

    public boolean onSpot() {
        return true;
    }


}
