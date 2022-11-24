package com.company.project.game;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.company.project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    GameThread gameThread;

    public Game(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread = new GameThread(getHolder(), getContext(), getWidth(), getHeight());
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case ACTION_MOVE:
            case ACTION_DOWN:
                gameThread.UpdateMousePosition((int)event.getRawX(), (int)event.getRawY());
        }
        return true;
    }

    static class GameThread extends Thread{
        SurfaceHolder holder;
        Ball gamer;
        Context context;
        List<Gate> gates = new ArrayList<>();
        int nextTime = 0, width, height,posX, posY, score = 0;
        Paint drawText;

        public GameThread(SurfaceHolder holder, Context context, int width, int height) {
            this.holder = holder;
            this.width = width;
            this.context = context;
            this.height = height;
            gamer = new Ball(context, width / 2, height - height / 7, width / 5, width / 5);

            drawText = new Paint();
            drawText.setColor(Color.BLACK);
            drawText.setStrokeWidth(20);
            drawText.setTextSize(100);
        }

        @Override
        public void run() {
            Canvas canvas;
            while (isAlive()){
                canvas = holder.lockCanvas();
                if(canvas == null)
                    continue;

                if(nextTime <= 0){
                    nextTime = ThreadLocalRandom.current().nextInt(100, 300);
                    gates.add(new Gate(context, width, width / 4, width / 5));
                }

                nextTime -= 3;

                canvas.drawColor(Color.WHITE);
                gamer.Redraw(canvas);
                gamer.UpdateMousePosition(posX, posY);

                boolean isCollision = false;
                for (Gate gate : gates){
                    gate.Redraw(canvas);
                    if(gate.isCollision(gamer)){
                        isCollision = true;
                    }
                }
                if(isCollision) {
                    gates.clear();
                    score = 0;
                    nextTime = 0;
                }
                canvas.drawText(String.valueOf(score), (float)width / 2, 100f, drawText);

                Iterator<Gate> ga = gates.iterator();
                while (ga.hasNext()){
                    Gate current = ga.next();
                    if(current.y >= height){
                        ga.remove();
                        score++;
                    }
                }

                holder.unlockCanvasAndPost(canvas);
            }
        }

        public void UpdateMousePosition(int x, int y){
            this.posX = x;
            this.posY = y;
        }
    }
}
