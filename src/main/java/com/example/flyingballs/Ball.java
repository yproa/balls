package com.example.flyingballs;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;


public class Ball {
    float x;
    float y;
    float r;
    float vx, vy;
    int colorn;
    Paint p = new Paint();

    ArrayList<Integer> colors = new ArrayList<Integer>(Arrays.asList(
            Color.rgb(192,192,192), Color.rgb(0,128,0),
            Color.rgb(128,0,128), Color.rgb(0,128,128),
            Color.rgb(192,192,0), Color.rgb(255,128,0),
            Color.rgb(128,0,255), Color.rgb(255,128,128)
    ));

    public Ball(float x,float y,float r, int p,float vx, float vy){
        this.x=x;
        this.y=y;
        this.r=r;
        this.colorn=p%8;
        this.p.setColor(colors.get(this.colorn));

        this.vx=vx;
        this.vy=vy;
    }
    public void updateColor(){
        this.colorn=(this.colorn+1)%8;
        this.p.setColor(colors.get(this.colorn));
    }

    public float getX(){
        return this.x;
    }
    public float getVX(){
        return this.vx;
    }
    public float getVY(){
        return this.vy;
    }

    public float getY(){
        return this.y;
    }
    public float getR(){
        return this.r;
    }
    public Paint getP(){
        return this.p;
    }
    public int getC(){
        return this.colorn;
    }

    public void updateCoords(int width,int height){
        this.x+=this.vx;
        this.y+=this.vy;

        if ((this.x>width-this.r)||(this.x<0+this.r)){//
            this.vx*=-1;
            this.updateColor();
        }
        if ((this.y>height-this.r)||(this.y<0+this.r)){//
            this.vy*=-1;
            this.updateColor();
        }
    }
    public void reverse(){
        vx*=-1;
        vy*=-1;

        this.updateColor();
    }

    public boolean ismath(Ball b2){
        if(Math.sqrt(Math.pow((this.x-b2.getX()),2)+Math.pow((this.y-b2.getY()),2))<(this.r+b2.getR())){
            return true;
        }
        else
            return false;
    }
}
