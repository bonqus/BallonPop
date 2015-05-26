package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by bohn on 12-05-2015.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 1440;
    public static final int HEIGHT = 852;
    private ArrayList<Balloon> balloons;
    private ArrayList<ExplosionAndBuff> explosionsAndBuffs;
    private Background bg;
    public static Needle needle;
    private boolean isDown = false;
    private long balloonStartTime;
    private long balloonSpawn = 0;
    private Random rand = new Random();
    private boolean playing;
    private Score score;
    private StartScreen ss;


    //buffs
    private boolean fasterBalloons = false;
    private boolean longerNeedle = false;
    private boolean needleGun = false;
    private boolean shortNeedle = false;
    private boolean sideSpikedNeedle = false;
    private boolean smallerBalloons = false;
    private boolean TwoEndedNeedle = false;


    // gør mindre for højere ballon spawnrate
    private int level = 0;

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
        explosionsAndBuffs = new ArrayList<ExplosionAndBuff>();
        needle = new Needle();
        score = new Score();
        ss = new StartScreen();
        playing = false;
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(playing) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                needle.touchMove(event.getX(), event.getY());
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                needle.shoot();
                return true;
            }
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ss.startUpdate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void update() {

        if (playing) {
            addBalloon();
            balloonUpdate();
            explosionsAndBuffsUpdate();

        } else {
            ss.update();
        }

    }

    public void explosionsAndBuffsUpdate() {
        // checks if there are any explosions or buffs
        if (explosionsAndBuffs.size() > 0) {
            //goes through all explosions and buffs and updates them
            for (int i = explosionsAndBuffs.size() - 1; i >= 0; i--) {
                explosionsAndBuffs.get(i).update();
                if(explosionsAndBuffs.get(i).isBuffActive()){
                    explosionsAndBuffs.get(i).getBuffLocation().getX();
                    explosionsAndBuffs.get(i).getBuffLocation().getY();
                    explosionsAndBuffs.get(i).getBuffLocation().getR();

                }
                if (explosionsAndBuffs.get(i).shouldRemove()) {
                    explosionsAndBuffs.remove(i);
                }
            }
        }
    }

    public void balloonUpdate() {
        for (int i = balloons.size() - 1; i >= 0; i--) {
            balloons.get(i).update();
            if (balloons.get(i).getY() < -200) {
                balloons.remove(i);
            }
            if (collision(balloons.get(i), needle.getLx(), needle.getLy())) {
                explosionsAndBuffs.add(new ExplosionAndBuff(balloons.get(i).getX(), balloons.get(i).getY(), balloons.get(i).getColor()));
                balloons.remove(i);
                score.setScore(score.getScore()+1);
                if (score.getScore() % 10 == 0 && level != 0) {
                    level -= 1;
                }
            }

        }
    }

    public void addBalloon() {

        long balloonElapsen = (System.nanoTime() - balloonStartTime)/1000000;
        // spawn balloon hvert andet sekund.
        if (balloonElapsen > balloonSpawn) {
            int spawn = rand.nextInt(WIDTH);
            balloons.add(new Balloon(spawn, HEIGHT+200));
            balloonStartTime = System.nanoTime();
            balloonSpawn = (rand.nextInt(2)+level)*1000;
        }
    }


    // Recatangular collision
    public boolean collision (GameObject a, int x, int y) {
        RectF b = new RectF(x,y, x+1, y+1);
        Path path = new Path();
        path.addRect(b, Path.Direction.CW);
        Region clip = new Region(0, 0, WIDTH, HEIGHT);

        Region region1 = new Region();
        region1.setPath(a.getPath(), clip);

        Region region2 = new Region();
        region2.setPath(path, clip);

        //Standard Needle Collision
        if (!region1.quickReject(region2) && region1.op(region2, Region.Op.INTERSECT) && needle.getPush()) {
            // Collision!
            return true;
        }

        //Buffed Needle Collision
        ArrayList<Region> spikes = needle.getSpikeRegions();
        for (Region r : spikes) {
            if (!region1.quickReject(r) && region1.op(r, Region.Op.INTERSECT)) {
                return true;
                }
            }
        return false;
    }





    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            //draw Background
            bg.draw(canvas);

            if(playing) {
                //draw Balloons
                for (Balloon b : balloons) {
                    b.draw(canvas);
                }

                //draw Explosions
                if (explosionsAndBuffs.size() > 0) {
                    for (ExplosionAndBuff e : explosionsAndBuffs) {
                        e.draw(canvas);
                    }
                }

                //draw needle
                needle.draw(canvas);

                //drawScore
                score.draw(canvas);
            } else {
                ss.draw(canvas);
                if (ss.getY() < -200) {
                    needle.draw(canvas);
                    if(needle.onSpot()){
                        playing = true;
                    }
                }
            }

            canvas.restoreToCount(savedState);
        }
    }

}