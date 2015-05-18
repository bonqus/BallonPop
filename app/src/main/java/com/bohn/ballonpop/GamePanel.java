package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    public static final int WIDTH = 1440;
    public static final int HEIGHT = 852;
    public static final int MOVESPEED = -5;
    private ArrayList<Balloon> balloons;
    private ArrayList<Explosion> explosions;
    private Background bg;
    private Needle needle;
    private boolean isDown = false;
    private long balloonStartTime;
    private long balloonSpawn = 0;
    private Random rand = new Random();

    // gør mindre for højere ballon spawnrate
    private int level = 4;

    public static int SCORE = 0;

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
        explosions = new ArrayList<Explosion>();
        needle = new Needle();
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            needle.touchDown(event.getX(), event.getY());
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            needle.touchDown(event.getX(), event.getY());
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {


        addBalloon();

        for (int i = balloons.size()-1; i >= 0; i--) {
            balloons.get(i).update();
            if(balloons.get(i).getY() < -200) {
                balloons.remove(i);
            }
            if( collision(balloons.get(i), needle.getLx(), needle.getLy())){
                explosions.add(new Explosion(balloons.get(i).getX(), balloons.get(i).getY()));
                balloons.remove(i);
                SCORE++;
                if(SCORE % 10 == 0 && level != 0) {
                    level -= 1;
                }
            }

        }
        if (explosions.size() > 0) {
            for(int i = explosions.size()-1; i >= 0; i--) {
                explosions.get(i).update();
                if (explosions.get(i).shouldRemove()) {
                    explosions.remove(i);
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


    public boolean collision (GameObject a, int x, int y) {
        Rect b = new Rect(x,y, x+1, y+1);
        if (Rect.intersects(a.getRectangle(),b)) {
            return true;
        }
        return false;
    }

    public void drawScore(Canvas canvas) {
        //AssetManager am = context.getApplicationContext().getAssets();
        //Typeface tf = Typeface.createFromAsset(am, String.format(Locale.US, "fonts/%s", "abc.ttf"));



       // Typeface tf = Typeface.createFromAsset(R.getAssets(),fonts.RalewayThin.ttf);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Score: " + this.SCORE,this.WIDTH-150,50, paint);
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            for (Balloon b: balloons) {
                b.draw(canvas);
            }
            if (explosions.size() > 0) {
                for (Explosion e : explosions) {
                    e.draw(canvas);
                }
            }
            needle.draw(canvas);

            drawScore(canvas);
            canvas.restoreToCount(savedState);
        }
    }

}