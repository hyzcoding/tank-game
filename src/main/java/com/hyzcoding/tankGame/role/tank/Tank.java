package com.hyzcoding.tankGame.role.tank;

import com.hyzcoding.tankGame.constant.Bound;
import com.hyzcoding.tankGame.constant.Size;
import com.hyzcoding.tankGame.constant.ThreadConstant;
import com.hyzcoding.tankGame.flash.Blast;
import com.hyzcoding.tankGame.role.bullet.Bullet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public abstract class Tank implements Runnable {
    private int x;
    private int y;
    private int speed = 3;
    private char direction;
    private boolean isAlive= true;
    boolean type;
    BlockingQueue<Runnable> bls = new ArrayBlockingQueue<>(8);
    ThreadPoolExecutor blsTreadPool = new ThreadPoolExecutor(ThreadConstant.POOL_SIZE,
            ThreadConstant.MAX_POOLSIZE,ThreadConstant.KEEP_ALIVE_TIME,
        TimeUnit.MILLISECONDS,bls);
    public Tank(int x, int y, char direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public BlockingQueue<Runnable> getBls() {
        return bls;
    }

    public void shot(){
        Bullet bl = null;
        switch(direction){
            case 'U':
                bl = new Bullet(x-2+ Size.TANK_WIDTH>>1,y,direction);
                break;
            case 'R':
                bl = new Bullet(x+Size.TANK_WIDTH,y-2+Size.TANK_WIDTH>>1,direction);
                break;
            case 'D':
                bl = new Bullet(x-2+Size.TANK_WIDTH>>1,y+Size.TANK_WIDTH,direction);
                break;
            case 'L':
                bl = new Bullet(x,y-2+Size.TANK_WIDTH>>2,direction);
                break;
        }
        if (bl!=null){
            blsTreadPool.execute(bl);
        }
    }

    public void move(char direction){
        this.direction = direction;
        switch (direction){
            case 'U':
                if(y>0){
                    y-=speed;
                }else if(!type){
                    this.direction ='D';
                }
                break;
            case 'R':
                if(x< Bound.BOUND_X-Size.TANK_HIGHT-2*speed){
                    x+=speed;
                }else if(!type){
                    this.direction ='L';
                }
                break;
            case 'D':
                if(y<Bound.BOUND_Y-2*Size.TANK_HIGHT-2*speed){
                    y+=speed;
                }else if(!type){
                    this.direction ='U';
                }
                break;
            case 'L':
                if(x>0){
                    x-=speed;
                }else if(!type){
                    this.direction ='R';
                }
                break;
        }
    }

    public abstract Blast hit(Tank tank);

    @Override
    public abstract void run();

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
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
