package com.hyzcoding.tankGame.flash;

import com.hyzcoding.tankGame.constant.ThreadConstant;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class Blast extends Flash {
    public Blast(int x, int y) {
        super(x, y);
        this.times= ThreadConstant.BLASH_TIME;
    }
}
