package com.hyzcoding.tankgame.entity.role;

import com.hyzcoding.tankgame.consts.PaintConst;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class Bullet implements Runnable {
    public boolean isLive = true;
    public int x, y;
    public int size = 4;
    char direction;
    int v = 5;

    public Bullet(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public boolean isLive() {
        isLive = x >= 0 && x <= PaintConst.HEIGHT && y >= 0 && y <= PaintConst.WIDTH;
        return isLive;
    }

    public void setLive(boolean isLive) {
        this.isLive = isLive;
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

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub

        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            isLive();
            switch (this.direction) {
                case 'U':
                    y -= v;
                    break;
                case 'R':
                    x += v;
                    break;
                case 'D':
                    y += v;
                    break;
                case 'L':
                    x -= v;
                    break;
            }
        }
    }
}
