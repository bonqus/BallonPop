package com.bohn.ballonpop.buffs;

import android.graphics.Canvas;

/**
 * Created by bohn on 20-05-2015.
 */
public interface Buffs {
    public void draw(Canvas canvas);
    public void update();
    public boolean shouldRemove();

}
