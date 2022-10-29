package com.hyzcoding.tankgame.entity.role;

import java.util.concurrent.TimeUnit;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class EnemyTank extends Tank implements Runnable {
    //访问GamePanel上创建的敌人坦克数量
    public int ifShot = 1;
//	Vector<EnemyTank> ets=new Vector<EnemyTank>();

    public EnemyTank(int x, int y, char direction, boolean type, boolean isLive) {
        super(x, y, direction, type, isLive);
        // TODO Auto-generated constructor stub
    }

    //	public Vector<EnemyTank> getEts() {
//		return ets;
//	}
//	public void setEts(Vector<EnemyTank> ets) {
//		this.ets = ets;
//	}
    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //用于退出线程
        while (true) {
            this.ifShot++;
            if (ifShot % 2 == 0) {
                if (this.isLive) {
                    if (this.bls.size() < 10) {
                        this.shot();
                    }
                }
            }
            for (int i = 0; i <= 30; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move(this.direction);
            }

            randomDirection();

        }
    }

    private void randomDirection() {
        int randomNumber = (int) (Math.random() * 4.0);
        switch (randomNumber) {
            case 0:
                this.direction = 'U';
                break;
            case 1:
                this.direction = 'R';
                break;
            case 2:
                this.direction = 'D';
                break;
            case 3:
                this.direction = 'L';
                break;
        }
    }
}