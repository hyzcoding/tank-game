package main.cn.hyzcoding.tankgame.entity;

import java.util.Vector;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class Tank{
    int x,y;
    //
    int boundsX = 680;
    int boundsY = 560;
    //
    int v=3;
    //���ÿ��
    int width = 30;
    //���ó���
    int height = 30;
    char direction;
    // false���ˣ�true���
    boolean type;
    boolean isLive;
    Vector<Bullet> bls=new Vector<Bullet>();
    public Tank(){}
    public Tank(int x,int y,char direction,boolean type,boolean isLive){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
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
    public int getV() {
        return v;
    }
    public void setV(int v) {
        this.v = v;
    }
    public char getDirection() {
        return direction;
    }
    public void setDirection(char direction) {
        this.direction = direction;
    }
    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }
    public boolean isLive() {
        return isLive;
    }
    public void setLive(boolean isLive) {
        this.isLive = isLive;
    }
    public void shot(){
        Bullet bl = null;
        switch(direction){
            case 'U':
                bl = new Bullet(x+width/2-2,y,direction);
                break;
            case 'R':
                bl = new Bullet(x+height,y+width/2-2,direction);
                break;
            case 'D':
                bl = new Bullet(x+width/2-2,y+height,direction);
                break;
            case 'L':
                bl = new Bullet(x,y+width/2-2,direction);
                break;
            default:
                break ;
        }
        bls.add(bl);
        Thread tbl = new Thread(bl);
        tbl.start();
    }
    /**
     * �ƶ�
     * @param direction
     */
    public void move(char direction) {
        // TODO Auto-generated method stub
        setDirection(direction);
        switch(this.direction){
            case 'U':
                if(y>0){
                    y-=v;
                }else if(!type){
                    setDirection('D');
                }
                break;
            case 'R':
                if(x<boundsX-height-2*v){
                    x+=v;
                }else if(!type){
                    setDirection('L');
                }
                break;
            case 'D':
                if(y<boundsY-2*height-2*v){
                    y+=v;
                }else if(!type){
                    setDirection('U');
                }
                break;
            case 'L':
                if(x>0){
                    x-=v;
                }else if(!type){
                    setDirection('R');
                }
                break;
        }
    }
    /**
     * �ж��Ƿ�������̹��
     * @param p1  ���̹��
     * @return  ��ըЧ������
     */
    public Blast hit(PlayerTank p1){
        for(int i = 0;i<bls.size();i++){
            switch(p1.direction){
                case 'U':
                case 'D':
                    if(bls.get(i).x >p1.x&&bls.get(i).x <p1.x+20&&bls.get(i).y >p1.y&&bls.get(i).y <p1.y+30){
                        p1.isLive = false;
                        bls.get(i).isLive = false;
                        this.bls.remove(i);
                    }
                    break;
                case 'R':
                case 'L':
                    if(bls.get(i).x >p1.x&&bls.get(i).x <p1.x+30&&bls.get(i).y >p1.y&&bls.get(i).y <p1.y+20){
                        p1.isLive = false;
                        bls.get(i).isLive = false;
                        this.bls.remove(i);
                    }
                    break;

            }
        }
        if(p1.isLive == false){
            return new Blast(p1.x,p1.y);
        }else{
            return null;
        }
    }
    /**
     * �ж��Ƿ���е���̹��
     */
    public Blast hit(EnemyTank et){
        for(int i = 0;i<bls.size();i++){
            if(bls.get(i).x >et.x && bls.get(i).x <et.x+30 && bls.get(i).y >et.y && bls.get(i).y <et.y+30){
                et.isLive = false;
                bls.get(i).isLive = false;
                this.bls.remove(i);
            }
        }
        if(et.isLive == false){
            return new Blast(et.x,et.y);
        }else{
            return null;
        }
    }
    /**
     * �ж��Ƿ���ײ�������ײ���ı�����������ֻ�жϵ���̹�ˣ�------������
     */
    public void collide(EnemyTank et){
        if(Math.abs(this.x-et.x)<=30&&Math.abs(this.y-et.y)<=30){
            switch(direction){
                case 'U':
                    switch(et.direction){
                        case 'R':
                            if(et.x<x){
                                direction = 'D';
                                et.direction = 'L';
                            }
                            break;
                        case 'D':
                            if(et.y<y){
                                direction = 'D';
                                et.direction = 'U';
                            }
                            break;
                        case 'L':
                            if(et.x>x){
                                direction = 'D';
                                et.direction = 'R';
                            }
                            break;
                    }
                    break;
                case 'R':
                    switch(et.direction){
                        case 'U':
                            if(et.y>y){
                                direction = 'L';
                                et.direction = 'D';
                            }
                            break;
                        case 'D':
                            if(et.y<y){
                                direction = 'L';
                                et.direction = 'U';
                            }
                            break;
                        case 'L':
                            if(et.x>x){
                                direction = 'L';
                                et.direction = 'R';
                            }
                            break;
                    }
                    break;
                case 'D':
                    switch(et.direction){
                        case 'R':
                            if(et.x<x){
                                direction = 'U';
                                et.direction = 'L';
                            }
                            break;
                        case 'U':
                            if(et.y<y){
                                direction = 'U';
                                et.direction = 'D';
                            }
                            break;
                        case 'L':
                            if(et.x>x){
                                direction = 'U';
                                et.direction = 'R';
                            }
                            break;
                    }
                    break;
                case 'L':
                    switch(et.direction){
                        case 'U':
                            if(et.y>y){
                                direction = 'R';
                                et.direction = 'D';
                            }
                            break;
                        case 'R':
                            if(et.x<x){
                                direction = 'R';
                                et.direction = 'L';
                            }
                            break;
                        case 'D':
                            if(et.y<y){
                                direction = 'R';
                                et.direction = 'U';
                            }
                            break;
                    }
                    break;
            }
        }
    }
}

