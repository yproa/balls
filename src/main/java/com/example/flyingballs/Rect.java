package com.example.flyingballs;

public class Rect {
    float x1,x2;
    float y1,y2;
    Boolean up=false;

    Rect(float x1,float y1){
        this.x1=x1;
        this.x2=x1+200;
        this.y1=y1;
        this.y2=y1+200;
    }
    public void setXY(float x,float y){
        this.x1=x;
        this.x2=x+200;
        this.y1=y;
        this.y2=y+200;
    }
    public boolean isUp(){
        return this.up;
    }

    public float getX1(){
        return this.x1;
    }
    public float getX2(){
        return this.x2;
    }
    public float getY1(){
        return this.y1;
    }
    public float getY2(){
        return this.y2;
    }
    public void getUp(){
        this.up=true;

    }
    public void getDown(){
        this.up=false;

    }
    public boolean isInRect(float x,float y){
        if ((x>=this.x1)&&(y<=this.y1)&&((x<=this.x2)&&(y>=this.y2)))
            return true;
        else
            return false;
    }
    public boolean isInRect2(float x,float y, float r){
        if ((Math.sqrt(Math.pow(this.x1-x,2)+Math.pow(this.y1-y,2))<=r)&&!this.up){//left up
            return true;
        }
        else if((Math.sqrt(Math.pow(this.x2-x,2)+Math.pow(this.y1-y,2))<=r)&&!this.up){//right up
            return true;
        }
        else if((Math.sqrt(Math.pow(this.x1-x,2)+Math.pow(this.y2-y,2))<=r)&&!this.up){//left down
            return true;
        }
        else if((Math.sqrt(Math.pow(this.x2-x,2)+Math.pow(this.y2-y,2))<=r)&&!this.up){//left up
            return true;
        }
        else return false;

    }
}
