package com.hyzcoding.tankGame.role.tank;

import com.hyzcoding.tankGame.flash.Blast;
import com.hyzcoding.tankGame.role.bullet.Bullet;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class PlayerTank extends Tank {
    public PlayerTank(int x, int y, char direction) {
        super(x, y, direction);
        this.type =true;
    }

    @Override
    public Blast hit(Tank et) {
        if (et instanceof PlayerTank ) return null;
        bls.removeIf(b->{
            Bullet bullet = (Bullet) b;
            if( bullet.getX()>et.getX() &&
                    bullet.getX() <et.getX()+30 &&
                    bullet.getY() >et.getY() &&
                    bullet.getY() <et.getY()+30){
                et.setAlive(false);
                bullet.setAlive(false);
                blsTreadPool.remove(b);
                return true;
            }
            return false;
        });
        if(!et.isAlive()){
            return new Blast(et.getX(),et.getY());
        }else{
            return null;
        }
    }

    @Override
    public void run() {

    }

}
