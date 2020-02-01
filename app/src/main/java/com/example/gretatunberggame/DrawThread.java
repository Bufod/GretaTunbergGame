package com.example.gretatunberggame;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Sprite sprite;//текущий спрайт
    private Sprite[] sprites;// набор спрайтов
    private volatile boolean running = true;//флаг для остановки потока
    private boolean change = true;
    private Paint bkgr = new Paint();{
        bkgr.setColor(Color.WHITE);
    }

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }

    public void setSprite(Sprite[] sprites) {
        this.sprites = sprites;
    }

    public void requestStop() {
        running = false;
    }

    private void setStartCoordinate(Sprite sprite, int canvasWidth){
        sprite.setX((float)(Math.random()*canvasWidth));
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                if (change){
                    sprite = sprites[(int)(Math.random()*sprites.length)];
                    setStartCoordinate(sprite, canvas.getWidth());
                    change = false;
                }
                try {
                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), bkgr);

                    if (sprite != null) {
                        sprite.draw(canvas);
                        sprite.moveDown(1, canvas);
                    }
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
