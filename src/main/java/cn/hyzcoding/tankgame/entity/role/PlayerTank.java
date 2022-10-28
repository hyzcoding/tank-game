package cn.hyzcoding.tankgame.entity.role;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class PlayerTank extends Tank {
    public int width = 30;
    public int height = 40;
    public int v = 5;

    public PlayerTank(int x, int y, char direction, boolean type, boolean isLive) {
        super(x, y, direction, type, isLive);
        // TODO Auto-generated constructor stub
    }

}