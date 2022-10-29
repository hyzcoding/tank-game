package com.hyzcoding.tankGame.flash;

import com.hyzcoding.tankGame.constant.ThreadConstant;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class Born extends Flash {
    public Born(int x, int y) {
        super(x, y);
        this.times = ThreadConstant.BORN_TIME;
    }
}
