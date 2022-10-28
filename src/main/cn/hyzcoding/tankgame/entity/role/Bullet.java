package main.cn.hyzcoding.tankgame.entity;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class Bullet implements Runnable{
    boolean isLive=true;
    int x,y;
    int size = 4;
    public boolean isLive() {
        if(x<0||x>700||y<0||y>600){
            isLive = false;

        }else{
            isLive = true;
        }
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

    char direction;
    int v=5;
    public Bullet(int x,int y,char direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub

        while(true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            isLive();
            switch(this.direction){
                case 'U':
                    y-=v;
                    break;
                case 'R':
                    x+=v;
                    break;
                case 'D':
                    y+=v;
                    break;
                case 'L':
                    x-=v;
                    break;
            }
        }
    }
}
