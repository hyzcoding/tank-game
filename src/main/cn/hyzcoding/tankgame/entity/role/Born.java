package main.cn.hyzcoding.tankgame.entity;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class Born{
    int x,y;
    int times = 16;
    boolean isLive =true;
    public Born(int x,int y){
        this.x = x;
        this.y = y;
    }
    public void lifeDown(){
        if(times>0){
            times--;
        }else{
            this.isLive = false;
        }
    }
}