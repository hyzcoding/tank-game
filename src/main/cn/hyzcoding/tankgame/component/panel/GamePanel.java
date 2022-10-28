package main.cn.hyzcoding.tankgame.component;

import main.cn.hyzcoding.tankgame.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class GamePanel extends JPanel implements Runnable, KeyListener {
    //版本号2.0
    private static final long serialVersionUID = 2L;
    //创建玩家坦克对象
    PlayerTank p1 = null;
    //创建敌人坦克vector数组+设置初始敌人坦克数量
    Vector<EnemyTank> ets = new Vector <EnemyTank>();
    int enSize = 8;
    //
    Born bornP = null;
    //创建出生vector数组
    Vector <Born> borns = new Vector <Born>();
    //创建爆炸vector数组
    Vector <Blast> blasts = new Vector <Blast>();
    //创建图片对象
    //玩家坦克
    Image image_p1tankU = null;
    Image image_p1tankR = null;
    Image image_p1tankD = null;
    Image image_p1tankL = null;
    //敌人坦克
    Image enemy3U = null;
    Image enemy3R = null;
    Image enemy3D = null;
    Image enemy3L = null;
    //出生
    Image born1 = null;
    Image born2 = null;
    Image born3 = null;
    Image born4 = null;

    Image bornP1 = null;
    Image bornP2 = null;
    //爆炸
    Image blast1 = null;
    Image blast2 = null;
    Image blast3 = null;
    Image blast4 = null;
    Image blast5 = null;
    Image blast6 = null;
    Image blast7 = null;
    Image blast8 = null;
    //
    Image baseFine = null;


    public GamePanel(){
        //初始化玩家坦克
        p1=new PlayerTank(350,470,'U',true,true);
        bornP = new Born(p1.x,p1.y);
        this.setBackground(Color.BLACK);
        //初始化敌人坦克,添加至面板
        for(int i=0;i<enSize;i++){
            EnemyTank et=new EnemyTank(80*i,0,'D',false,true);
            Born born = new Born(et.x,et.y);
            borns.add(born);

            Thread tet=new Thread(et);
            tet.start();
            ets.add(et);
        }

        //导入图片
        //导入玩家坦克图片
        image_p1tankU=Toolkit.getDefaultToolkit().createImage("images/player/p1tankU.gif");
        image_p1tankR=Toolkit.getDefaultToolkit().createImage("images/player/p1tankR.gif");
        image_p1tankD=Toolkit.getDefaultToolkit().createImage("images/player/p1tankD.gif");
        image_p1tankL=Toolkit.getDefaultToolkit().createImage("images/player/p1tankL.gif");
        //导入敌人坦克图片
        enemy3U=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3U.gif");
        enemy3R=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3R.gif");
        enemy3D=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3D.gif");
        enemy3L=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3L.gif");
        //出生图片
        born1=Toolkit.getDefaultToolkit().createImage("images/born/born1.gif");
        born2=Toolkit.getDefaultToolkit().createImage("images/born/born2.gif");
        born3=Toolkit.getDefaultToolkit().createImage("images/born/born3.gif");
        born4=Toolkit.getDefaultToolkit().createImage("images/born/born4.gif");

        bornP1 = Toolkit.getDefaultToolkit().createImage("images/born/bornP1.png");
        bornP2 = Toolkit.getDefaultToolkit().createImage("images/born/bornP2.png");
        //爆炸图片
        blast1=Toolkit.getDefaultToolkit().createImage("images/blast/blast1.gif");
        blast2=Toolkit.getDefaultToolkit().createImage("images/blast/blast2.gif");
        blast3=Toolkit.getDefaultToolkit().createImage("images/blast/blast3.gif");
        blast4=Toolkit.getDefaultToolkit().createImage("images/blast/blast4.gif");
        blast5=Toolkit.getDefaultToolkit().createImage("images/blast/blast5.gif");
        blast6=Toolkit.getDefaultToolkit().createImage("images/blast/blast6.gif");
        blast7=Toolkit.getDefaultToolkit().createImage("images/blast/blast7.gif");
        blast8=Toolkit.getDefaultToolkit().createImage("images/blast/blast8.gif");
        //障碍物图片


    }
    //碰撞
    public void collideEt(){
        for(int i = 0; i < ets.size();i ++){
            for(int j = 0; j < i;j ++){
                ets.get(i).collide(ets.get(j));
            }
        }
    }
    //绘制图像
    public void paint(Graphics g){
        super.paint(g);

        //绘制玩家坦克
        if(p1.isLive){
            this.drawtank(p1.x, p1.y, p1.width,p1.height, p1.direction, g, p1.type);
            for(int i = 0;i<p1.bls.size();i++){
                Bullet p1bl=p1.bls.get(i);
                if(p1bl.isLive){
                    //绘制子弹
                    g.setColor(Color.YELLOW);
                    g.fillRect(p1bl.x, p1bl.y, 4, 4);

                }else{
                    p1.bls.remove(i);
                }
            }
        }
        //绘制出生
        for(int i=0;i<borns.size();i++){
            Born born = borns.get(i);
            if(born.times%4 == 0){
                g.drawImage(born1, born.x, born.y,30,40, this);
            }else if(born.times%4 == 1){
                g.drawImage(born2, born.x, born.y,30,40, this);
            }else if(born.times%4 == 2){
                g.drawImage(born3, born.x, born.y,30,40, this);
            }else{
                g.drawImage(born4, born.x, born.y,30,40, this);
            }
            born.lifeDown();

            if(born.times == 0){
                borns.remove(born);
            }
        }
        //
        if(bornP.isLive){
            if(bornP.times%2 == 0){
                g.drawImage(bornP1, p1.x-5, p1.y+10,40,30, this);
            }else{
                g.drawImage(bornP2, p1.x-5, p1.y+10,40,30, this);
            }
            bornP.lifeDown();
        }

        //绘制敌人坦克
        for(int i=0;i<ets.size();i++){
            EnemyTank et=ets.get(i);
            if(et.isLive&&borns.size() == 0){
                this.drawtank(et.x, et.y, et.width, et.height, et.direction, g, et.type);
                for(int j = 0;j<et.bls.size();j++){
                    Bullet etbl=et.bls.get(j);
                    if(etbl.isLive){
                        //绘制子弹
                        g.setColor(Color.white);
                        g.fillRect(etbl.x, etbl.y, 4, 4);

                    }else{
                        et.bls.remove(j);
                    }
                }
            }
        }
        //绘制炸弹
        for(int i=0;i<ets.size();i++){//------可以尝试使用迭代器
            Blast blastP1 = ets.get(i).hit(p1);
            if(blastP1 != null){
                blasts.add(blastP1);
            }
            Blast blastEt = p1.hit(ets.get(i));
            if(blastEt != null){
                blasts.add(blastEt);
                ets.remove(i);
            }
        }
        drawBlast(blasts, g);
        //绘制障碍物

    }
    //
    public void drawBlast(Vector<Blast> blasts,Graphics g){
        for(int i=0;i<blasts.size();i++){
            Blast blast = blasts.get(i);
            if(blast.times%8 == 0){
                g.drawImage(blast1, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 1){
                g.drawImage(blast2, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 2){
                g.drawImage(blast3, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 3){
                g.drawImage(blast4, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 4){
                g.drawImage(blast5, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 5){
                g.drawImage(blast6, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 6){
                g.drawImage(blast7, blast.x, blast.y,30,40, this);
            }else if(blast.times%8 == 7){
                g.drawImage(blast3, blast.x, blast.y,30,40, this);
            }else{
                g.drawImage(blast8, blast.x, blast.y,30,40, this);
            }
            blast.lifeDown();
            if(blast.times == 0){
                blasts.remove(blast);

            }
        }
    }
    //创建绘制坦克方法--（敌人和玩家坦克）false敌人，true玩家
    public void drawtank(int x,int y,int width,int height,char direction,Graphics g,boolean type){
        switch(direction){
            case 'U':
                if(type){
                    g.drawImage(image_p1tankU, x, y, width, height, this);
                }else{
                    g.drawImage(enemy3U, x, y, width, height, this);
                }
                break;
            case 'R':
                if(type){
                    g.drawImage(image_p1tankR, x, y, height, width, this);
                }else{
                    g.drawImage(enemy3R, x, y, height, width, this);
                }
                break;
            case 'D':
                if(type){
                    g.drawImage(image_p1tankD, x, y, width, height, this);
                }else{
                    g.drawImage(enemy3D, x, y, width, height, this);
                }
                break;
            case 'L':
                if(type){
                    g.drawImage(image_p1tankL, x, y, height, width, this);
                }else{
                    g.drawImage(enemy3L, x, y, height, width, this);
                }
                break;
        }


    }
    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub
        while(true){
            try {
                //设置休眠时间
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //重绘GamePanel
            this.repaint();
            collideEt();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W){
            if(p1.direction != 'U') p1.move('U');
            p1.move('U');
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D){
            if(p1.direction != 'R') p1.move('R');
            p1.move('R');
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
            if(p1.direction != 'D') p1.move('D');
            p1.move('D');
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A){
            if(p1.direction != 'L') p1.move('L');
            p1.move('L');
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE||e.getKeyCode() == KeyEvent.VK_J||e.getKeyCode() == KeyEvent.VK_ENTER){
            p1.shot();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}

