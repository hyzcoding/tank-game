package com.hyzcoding.tankGame.flash;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class Flash implements Runnable{
    private int x;
    private int y;
    int times;
    private boolean isAlive;
    public Flash(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }
    public void lifeDown(){
        if(times>0){
            times--;
        }else{
            this.isAlive = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getTimes() {
        return times;
    }

    @Override
    public void run() {

    }
}
