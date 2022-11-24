package com.company.project.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.company.project.R;

import java.util.concurrent.ThreadLocalRandom;

public class Gate implements Drawable{
    int x;
    int y = -200;
    Bitmap bitmap;
    int sizeX, sizeY;

    public Gate(Context context, int width, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = ThreadLocalRandom.current().nextInt(0, width - sizeX);
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gate);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, sizeX, sizeY, false);
    }

    public boolean isCollision(Ball ball){
        return !(((x + sizeX) < ball.x)||(x > (ball.x + ball.sizeX))||((y + sizeY) < ball.y)||(y > (ball.y + ball.sizeY)));
    }

    @Override
    public void Redraw(Canvas canvas) {
        y += 10;
        canvas.drawBitmap(bitmap, x, y, new Paint());
    }
}
