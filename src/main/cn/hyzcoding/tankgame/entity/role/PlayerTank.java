package main.cn.hyzcoding.tankgame.entity;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class PlayerTank extends Tank{
    int width = 30;
    int height = 40;
    int v = 5;
    public PlayerTank(int x, int y, char direction, boolean type, boolean isLive) {
        super(x, y, direction, type, isLive);
        // TODO Auto-generated constructor stub
    }

}