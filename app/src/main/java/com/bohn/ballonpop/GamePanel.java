package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by bohn on 12-05-2015.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int MOVESPEED = -5;
    private ArrayList<Balloon> balloons;
    private Background bg;
    private long balloonStartTime;
    private long balloonSpawn = 0;
    private Random rand = new Random();

    //Increase, to slow down difficulty progression speed.
    private int progressDenom = 20;

    private MainThread thread;


    public GamePanel(Context context) {
        super(context);

        // add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel so it can handle events
        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        bg = new Background(WIDTH, HEIGHT);
        balloons = new ArrayList<Balloon>();
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        addBalloon();

        for(Balloon b: balloons) {
            b.update();
        }

    }

    public void addBalloon() {

        long balloonElapsen = (System.nanoTime() - balloonStartTime)/1000000;
        // spawn balloon hvert andet sekund.
        if (balloonElapsen > balloonSpawn) {
            int spawn = rand.nextInt(WIDTH);
            balloons.add(new Balloon(spawn, HEIGHT+10));
            balloonStartTime = System.nanoTime();
            balloonSpawn = (rand.nextInt(2)+1)*1000;
        }
    }


    public boolean collision (GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(),b.getRectangle())) {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);


        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);

            bg.draw(canvas);
            for (Balloon b: balloons) {
                b.draw(canvas);
            }

            canvas.restoreToCount(savedState);
        }
    }


}