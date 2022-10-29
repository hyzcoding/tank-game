package com.hyzcoding.tankgame.entity.role;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class Born {
    public int x, y;
    public int times = 16;
    public boolean isLive = true;

    public Born(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown() {
        if (times > 0) {
            times--;
        } else {
            this.isLive = false;
        }
    }
}