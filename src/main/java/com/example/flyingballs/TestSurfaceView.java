package com.example.flyingballs;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    class DrawThread extends Thread {
        float firstX = 300, firstY = 300;
        float secX = 700, secY = 100;
        Random r = new Random();
        Paint p1 = new Paint();
        Paint p2 = new Paint();
        boolean runFlag = true;
        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        SurfaceHolder holder;
        @Override
        public void run() {
            super.run();
            p1.setColor(Color.YELLOW);
            p2.setColor(Color.GREEN);
            float randX=r.nextFloat() * 100 - 50;
            float randY=r.nextFloat() * 100 - 50;
            float randX2=r.nextFloat() * 100 - 50;
            float randY2=r.nextFloat() * 100 - 50;
            // выполняем цикл (рисуем кадры) пока флаг включен
            while (runFlag) {
                Canvas c = holder.lockCanvas();
                int width=c.getWidth();
                int height=c.getHeight();
                // если успешно захватили канву
                if (c != null) {
                    c.drawColor(Color.RED);

                    // случайные блуждания - сдвигаем координаты шарика в случайную сторону
                    firstX += randX;
                    firstY += randY;
                    secX += randX2;
                    secY += randY2;
                    c.drawCircle(firstX, firstY,100,p1);
                    c.drawCircle(secX, secY,100,p2);
                    holder.unlockCanvasAndPost(c);
                    if ((firstY >height)||(firstY <0)){//
                        randY*=-1;
                    }
                    if ((firstX >width)||(firstX <0)){//
                        randX*=-1;
                    }
                    if ((secY >height)||(secY <0)){//
                        randY2*=-1;
                    }
                    if ((secX >width)||(secX <0)){//
                        randX2*=-1;
                    }

                    if(Math.sqrt(Math.pow((secX-firstX),2)+Math.pow((secY-firstY),2))<200){
                        randY2*=-1;
                        randX2*=-1;
                        randX*=-1;
                        randY*=-1;
                        p2.setColor(Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
                        p1.setColor(Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
                    }
                    // нужна пауза на каждом кадре
                    try {
                        Thread.sleep(100); }
                    catch (InterruptedException e) {}
                }
            }

        }
    }
    DrawThread thread;

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DrawThread(holder);
        thread.start();
        // убеждаемся, что поток запускается
        Log.d("mytag", "DrawThread is running");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // при изменении конфигурации поверхности поток нужно перезапустить
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }

    // поверхность уничтожается - поток останавливаем
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }
}