package com.example.gretatunberggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    private float x, y;
    private int height, width;
    private Bitmap bmp;
    private Paint paint = new Paint();

    public Sprite(Bitmap bmp) {
        this.bmp = bmp;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void moveDown(int dy, Canvas canvas){
        if (y < canvas.getHeight()){
            y += dy;
        }
    }

    public void draw(Canvas canvas){
        Rect rect = new Rect();
        rect.set((int) x,(int) y, (int)(x + 100), (int)(y + 100));
        canvas.drawBitmap(bmp, null, rect, paint);
    }

    public boolean isIntersect(Rect rect)
}
