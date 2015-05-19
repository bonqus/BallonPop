package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by bohn on 19-05-2015.
 */
public class Score {
    private int score;
    public Score() {
        score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();

        //Typeface tf = Typeface.create("Raleway Thin", Typeface.NORMAL);
        //paint.setTypeface(tf);

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Score: " + this.score, GamePanel.WIDTH-150,50, paint);
    }
}
