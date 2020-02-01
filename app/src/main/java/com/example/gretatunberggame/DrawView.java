package com.example.gretatunberggame;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawView extends SurfaceView
        implements SurfaceHolder.Callback {
    private DrawThread drawThread;
    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.setSprite(new Sprite[]{
                new Sprite(BitmapFactory
                        .decodeResource(this.getResources(), R.drawable.trash)),
                new Sprite(BitmapFactory
                        .decodeResource(this.getResources(), R.drawable.trash2))
        });
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }

    Float prevX, prevY;
    boolean touch = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touch = true;
                break;
            case MotionEvent.ACTION_UP:
                if (touch){
                    if (prevX < event.getX() && prevY < event.getY())
                        drawThread.passCoordinates(prevX,prevY,event.getX(),event.getY());
                    else if (prevX > event.getX() && prevY > event.getY())
                        drawThread.passCoordinates(event.getX(),event.getY(),prevX,prevY);
                    else if (prevX > event.getX() && prevY < event.getY())
                        drawThread.passCoordinates(event.getX(),prevY,prevX,event.getY());
                    else if (prevX <= event.getX() && prevY >= event.getY())
                        drawThread.passCoordinates(prevX,event.getY(),event.getX(),prevY);
                    prevY = null;
                    prevX = null;
                }
                touch = false;
                break;
        }

        return true;
    }
}
