package com.example.flyingballs;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    Rect rect=new Rect(1000,500);
    boolean moving = false;
    class DrawThread extends Thread {
        Random r = new Random();
        boolean runFlag = true;

        ArrayList<Ball> balls = new ArrayList<Ball>(Arrays.asList(
                new Ball(100,200,100,r.nextInt(8),r.nextFloat() * 100 - 50,r.nextFloat() * 100 - 50),
                new Ball(600,400,100,r.nextInt(8),r.nextFloat() * 100 - 50,r.nextFloat() * 100 - 50),
                new Ball(300,700,100,r.nextInt(8),r.nextFloat() * 100 - 50,r.nextFloat() * 100 - 50)

        ));


        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        SurfaceHolder holder;
        @Override
        public void run() {
            super.run();
            // выполняем цикл (рисуем кадры) пока флаг включен
            while (runFlag) {
                Canvas c = holder.lockCanvas();
                int width=c.getWidth();
                int height=c.getHeight();
                float traceX=0;
                float traceY=0;
                Paint white=new Paint();
                white.setColor(Color.WHITE);

                // если успешно захватили канву
                if (c != null) {
                    c.drawColor(Color.RED);
                    if((balls.get(0).getC()== balls.get(1).getC())&&(balls.get(0).getC()== balls.get(2).getC())){
                        c.drawColor(Color.GREEN);
                        runFlag=false;
                    }

                    // случайные блуждания - сдвигаем координаты шарика в случайную сторону
                    for (int i=0;i<3;i++) {
                        balls.get(i).updateCoords(width,height);
                        traceX=balls.get(i).getX();
                        traceY=balls.get(i).getY();
                        while (((traceX>0)&&(traceX<width))&&((traceY>0)&&(traceY<height))) {
                            if (rect.isInRect2(traceX, traceY,20)) {
                                traceX = -100;
                                traceY = -100;
                            } else {
                                traceX += balls.get(i).getVX();
                                traceY += balls.get(i).getVY();
                                c.drawCircle(traceX, traceY, 5, white);
                            }
                        }
                        c.drawCircle(balls.get(i).getX(), balls.get(i).getY(), balls.get(i).getR(), balls.get(i).getP());
                    }
                    c.drawRect(rect.getX1(),rect.getY1(),rect.getX2(),rect.getY2(),white);
                    if (balls.get(0).ismath(balls.get(1))){
                        balls.get(1).reverse();
                        balls.get(0).reverse();
                    }
                    if (balls.get(0).ismath(balls.get(2))){
                        balls.get(2).reverse();
                        balls.get(0).reverse();
                    }
                    if (balls.get(2).ismath(balls.get(1))){
                        balls.get(1).reverse();
                        balls.get(2).reverse();
                    }

                    if (rect.isInRect2(balls.get(0).getX(),balls.get(0).getY(),balls.get(0).getR())){
                        balls.get(0).reverse();
                    }
                    if (rect.isInRect2(balls.get(1).getX(),balls.get(1).getY(),balls.get(1).getR())){
                        balls.get(1).reverse();
                    }
                    if (rect.isInRect2(balls.get(2).getX(),balls.get(2).getY(),balls.get(0).getR())){
                        balls.get(2).reverse();
                    }

                    holder.unlockCanvasAndPost(c);



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

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                moving=true;
                rect.getUp();

                final int x1 = (int) event.getX();
                final int y1 = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                if (rect.isUp()) {

                    final int x_new = (int) event.getX();
                    final int y_new = (int) event.getY();
                    rect.setXY(x_new-100,y_new-100);


                }
                return true;
            case MotionEvent.ACTION_UP:

                rect.getDown();
                return true;
        }

        return true;

    }
}