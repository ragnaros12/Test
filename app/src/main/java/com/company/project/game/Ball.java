package com.company.project.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.company.project.R;

public class Ball implements Drawable, MousePositioner{
    Bitmap bitmap;
    int x, y;
    int sizeX, sizeY;

    public Ball(Context context, int x, int y, int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x = x - sizeX / 2;
        this.y = y;
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, sizeX, sizeY, false);
    }
    @Override
    public void Redraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, new Paint());
    }

    @Override
    public void UpdateMousePosition(int x, int y) {
        this.x = x;
    }
}
