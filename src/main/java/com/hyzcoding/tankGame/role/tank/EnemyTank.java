package com.hyzcoding.tankGame.role.tank;

import com.hyzcoding.tankGame.flash.Blast;
import com.hyzcoding.tankGame.role.bullet.Bullet;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class EnemyTank extends Tank {
    public EnemyTank(int x, int y, char direction) {
        super(x, y, direction);
        this.type = false;
    }

    @Override
    public Blast hit(Tank p1) {
        if (p1 instanceof EnemyTank ) return null;
        bls.removeIf(b->{
            Bullet bullet = (Bullet) b;
            switch(p1.getDirection()){
                case 'U':
                case 'D':
                    if(bullet.getX() >p1.getX()&&
                            bullet.getX() <p1.getX()+20&&
                            bullet.getY() >p1.getY()&&
                            bullet.getY() <p1.getY()+30){
                        p1.setAlive(false);
                        bullet.setAlive(false);
                        blsTreadPool.remove(b);
                        return true;
                    }
                    break;
                case 'R':
                case 'L':
                    if(bullet.getX() >p1.getX()&&
                        bullet.getX()<p1.getX()+30&&
                        bullet.getY() >p1.getY()&&
                        bullet.getY() <p1.getY()+20){
                        p1.setAlive(false);
                        bullet.setAlive(false);
                        return true;
                    }
                    break;
            }
            return false;
        });
        if(!p1.isAlive()){
           return new Blast(p1.getX(),p1.getY());
        }else{
            return null;
        }
    }

    @Override
    public void run() {
        try {
           TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //用于退出线程
        while(true){
            if(!this.isAlive()) return;
            if(this.blsTreadPool.getActiveCount()<10){
                this.shot();//
            }
            for(int i=0;i<=30;i++){
                try {
                   TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move(this.getDirection());
            }

            int randomNumber=(int)(Math.random()*4.0);
            switch(randomNumber){
                case 0:
                    this.setDirection('U');
                    break;
                case 1:
                    this.setDirection('R');
                    break;
                case 2:
                    this.setDirection('D');
                    break;
                case 3:
                    this.setDirection('L');
                    break;
            }
        }
    }

    public void crash(EnemyTank et){
        if(Math.abs(getX()-et.getX())<=30&&Math.abs(this.getY()-et.getY())<=30){
            char thisDirect = getDirection();
            char etDirect = et.getDirection();
            switch(getDirection()){
                case 'U':
                    switch(et.getDirection()){
                        case 'R':
                            if(et.getX()<getX()){
                                thisDirect='D';
                                etDirect ='L';
                            }
                            break;
                        case 'D':
                            if(et.getY()<getY()){
                                thisDirect='D';
                                etDirect ='U';
                            }
                            break;
                        case 'L':
                            if(et.getX()>getX()){
                                thisDirect = 'D';
                                etDirect = 'R';
                            }
                            break;
                    }
                    break;
                case 'R':
                    switch(et.getDirection()){
                        case 'U':
                            if(et.getY()>getY()){
                                thisDirect = 'L';
                                etDirect = 'D';
                            }
                            break;
                        case 'D':
                            if(et.getY()<getY()){
                                thisDirect = 'L';
                                etDirect = 'U';
                            }
                            break;
                        case 'L':
                            if(et.getX()>getX()){
                                thisDirect = 'L';
                                etDirect = 'R';
                            }
                            break;
                    }
                    break;
                case 'D':
                    switch(et.getDirection()){
                        case 'R':
                            if(et.getX()<getX()){
                                thisDirect = 'U';
                                etDirect = 'L';
                            }
                            break;
                        case 'U':
                            if(et.getY()<getY()){
                                thisDirect = 'U';
                                etDirect = 'D';
                            }
                            break;
                        case 'L':
                            if(et.getX()>getX()){
                                thisDirect = 'U';
                                etDirect = 'R';
                            }
                            break;
                    }
                    break;
                case 'L':
                    switch(et.getDirection()){
                        case 'U':
                            if(et.getY()>getY()){
                                thisDirect = 'R';
                                etDirect = 'D';
                            }
                            break;
                        case 'R':
                            if(et.getX()<getX()){
                                thisDirect = 'R';
                                etDirect = 'L';
                            }
                            break;
                        case 'D':
                            if(et.getY()<getY()){
                                thisDirect = 'R';
                                etDirect = 'U';
                            }
                            break;
                    }
                    break;
            }
            setDirection(thisDirect);
            et.setDirection(etDirect);
        }
    }
}
