package com.hyzcoding.tankGame.role.bullet;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class Bullet implements Runnable{
    private int x;
    private int y;
    private char direction;
    private boolean isAlive = true;
    private int speed = 5;
    public Bullet(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            isAlive();
            if (!isAlive) return;
            switch(this.direction){
                case 'U':
                    y-=speed;
                    break;
                case 'R':
                    x+=speed;
                    break;
                case 'D':
                    y+=speed;
                    break;
                case 'L':
                    x-=speed;
                    break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        isAlive = x >= 0 && x <= 700 && y >= 0 && y <= 600;
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
