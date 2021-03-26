package com.hyzcoding.tankGame.panel;

import com.hyzcoding.tankGame.constant.FilePath;
import com.hyzcoding.tankGame.constant.Size;
import com.hyzcoding.tankGame.flash.Blast;
import com.hyzcoding.tankGame.flash.Born;
import com.hyzcoding.tankGame.role.bullet.Bullet;
import com.hyzcoding.tankGame.role.tank.EnemyTank;
import com.hyzcoding.tankGame.role.tank.PlayerTank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    int enSize = 8;

    private PlayerTank p1;

    private Born bornP;

    private CopyOnWriteArrayList<Born> borns = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Blast> blasts = new CopyOnWriteArrayList<>();

    private Map<String,Image> imageTank = new HashMap<>(4);
    private Map<String,Image> imageEnemyTank = new HashMap<>(4);
    private Map<String,Image> imageBornTank = new HashMap<>(4);
    private Map<String,Image> imageBlastTank = new HashMap<>(4);


    private CopyOnWriteArrayList<EnemyTank> ets = new CopyOnWriteArrayList<>();

    public GamePanel() {
        try{
            importImage();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        this.setBackground(Color.BLACK);
        p1=new PlayerTank(350,470,'U');
        bornP = new Born(p1.getX(),p1.getY());
        this.setBackground(Color.BLACK);
        //初始化敌人坦克,添加至面板
        for(int i=0;i<enSize;i++){
            EnemyTank et=new EnemyTank(80*i,0,'D');
            Born born = new Born(et.getX(),et.getY());
            borns.add(born);
            Thread tet=new Thread(et);
            tet.start();
            ets.add(et);
        }

    }

    private void importImage() throws IOException {
        //导入图片
        //导入玩家坦克图片
        imageTank.put("image_p1tankU", ImageIO.read(this.getClass().getResource(FilePath.P1_TANK_U)));
        imageTank.put("image_p1tankR", ImageIO.read(this.getClass().getResource(FilePath.P1_TANK_R)));
        imageTank.put("image_p1tankD", ImageIO.read(this.getClass().getResource(FilePath.P1_TANK_D)));
        imageTank.put("image_p1tankL", ImageIO.read(this.getClass().getResource(FilePath.P1_TANK_L)));
        //导入敌人坦克图片
        imageEnemyTank.put("enemy3U", ImageIO.read(this.getClass().getResource(FilePath.ENEMY_3U)));
        imageEnemyTank.put("enemy3R", ImageIO.read(this.getClass().getResource(FilePath.ENEMY_3R)));
        imageEnemyTank.put("enemy3D", ImageIO.read(this.getClass().getResource(FilePath.ENEMY_3D)));
        imageEnemyTank.put("enemy3L", ImageIO.read(this.getClass().getResource(FilePath.ENEMY_3L)));
        //出生图片
        for (int i=1;i<=4;i++){
            imageBornTank.put("born"+i, ImageIO.read(this.getClass().getResource(FilePath.BORN_PRE+i+".gif")));
        }
        for (int i=1;i<=2;i++){
            imageBornTank.put("bornP"+i, ImageIO.read(this.getClass().getResource(FilePath.BORN_P_PRE+i+".png")));
        }
        //爆炸图片
        for (int i=1;i<=8;i++){
            imageBornTank.put("blast"+i, ImageIO.read(this.getClass().getResource(FilePath.BLAST_PRE+i+".png")));
        }
        //障碍物图片
    }

    /**
     * 敌方相撞
     * todo 代码优化
     */
    public void crashEnemy(){
        for(int i = 0; i < ets.size();i ++){
            for(int j = 0; j < i;j ++){
                ets.get(i).crash(ets.get(j));
            }
        }
    }

    /**
     * 绘制坦克
     * @param x
     * @param y
     * @param width
     * @param height
     * @param direction
     * @param g
     * @param type
     */
    public void drawTank(int x,int y,int width,int height,char direction,Graphics g,boolean type){
        switch(direction){
            case 'U':
                if(type){
                    g.drawImage(imageTank.get("image_p1tankU"), x, y, width, height, this);
                }else{
                    g.drawImage(imageEnemyTank.get("enemy3U"), x, y, width, height, this);
                }
                break;
            case 'R':
                if(type){
                    g.drawImage(imageTank.get("image_p1tankR"), x, y, height, width, this);
                }else{
                    g.drawImage(imageEnemyTank.get("enemy3R"), x, y, height, width, this);
                }
                break;
            case 'D':
                if(type){
                    g.drawImage(imageTank.get("image_p1tankD"), x, y, width, height, this);
                }else{
                    g.drawImage(imageEnemyTank.get("enemy3D"), x, y, width, height, this);
                }
                break;
            case 'L':
                if(type){
                    g.drawImage(imageTank.get("image_p1tankL"), x, y, height, width, this);
                }else{
                    g.drawImage(imageEnemyTank.get("enemy3L"), x, y, height, width, this);
                }
                break;
        }
    }
    private void drawBlast(CopyOnWriteArrayList<Blast> blasts,Graphics g){
        for(int i=0;i<blasts.size();i++){
            Blast blast = blasts.get(i);
            int index = blast.getTimes()%8;
            String key = "blast"+(index+1);
            g.drawImage(imageBlastTank.get(key),blast.getX(),blast.getY(),30,40,this);
            blast.lifeDown();
            if(blast.getTimes() == 0){
                blasts.remove(blast);
            }
        }
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);

        //绘制玩家坦克
        if(p1.isAlive()){
            this.drawTank(p1.getX(), p1.getY(), Size.TANK_WIDTH,Size.TANK_HIGHT, p1.getDirection(), g,true);
            p1.getBls().removeIf(b->{
                Bullet bullet = (Bullet) b;
                if(bullet.isAlive()){
                    //绘制子弹
                    g.setColor(Color.YELLOW);
                    g.fillRect(bullet.getX(), bullet.getY(), 4, 4);
                    return false;
                }else{
                    return true;
                }
            });
        }
        //绘制出生
        for(int i=0;i<borns.size();i++){
            Born born = borns.get(i);
            if(born.getTimes()%4 == 0){
                g.drawImage(imageBornTank.get("born1"), born.getX(),born.getY(),30,40, this);
            }else if(born.getTimes()%4 == 1){
                g.drawImage(imageBornTank.get("born2"), born.getX(),born.getY(),30,40, this);
            }else if(born.getTimes()%4 == 2){
                g.drawImage(imageBornTank.get("born3"), born.getX(),born.getY(),30,40, this);
            }else{
                g.drawImage(imageBornTank.get("born4"), born.getX(),born.getY(),30,40, this);
            }
            born.lifeDown();

            if(born.getTimes() == 0){
                borns.remove(born);
            }
        }
        //
        if(bornP.isAlive()){
            if(bornP.getTimes()%2 == 0){
                g.drawImage(imageBornTank.get("bornP1"), p1.getX()-5, p1.getY()+10,40,30, this);
            }else{
                g.drawImage(imageBornTank.get("bornP2"), p1.getX()-5, p1.getY()+10,40,30, this);
            }
            bornP.lifeDown();
        }

        //绘制敌人坦克
        for (EnemyTank et : ets) {
            if (et.isAlive() && borns.size() == 0) {
                this.drawTank(et.getX(), et.getY(), Size.TANK_WIDTH, Size.TANK_HIGHT, et.getDirection(), g, false);
                et.getBls().removeIf(b -> {
                    Bullet bullet = (Bullet) b;
                    if (bullet.isAlive()) {
                        //绘制子弹
                        g.setColor(Color.WHITE);
                        g.fillRect(bullet.getX(), bullet.getY(), 4, 4);
                        return false;
                    } else {
                        return true;
                    }
                });
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

    @Override
    public void run() {
        while(true){
            try {
                //设置休眠时间
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //重绘GamePanel
            this.repaint();
            crashEnemy();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W){
            if(p1.getDirection() != 'U') p1.move('U');
            p1.move('U');
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D){
            if(p1.getDirection() != 'R') p1.move('R');
            p1.move('R');
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
            if(p1.getDirection() != 'D') p1.move('D');
            p1.move('D');
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A){
            if(p1.getDirection() != 'L') p1.move('L');
            p1.move('L');
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE||e.getKeyCode() == KeyEvent.VK_J||e.getKeyCode() == KeyEvent.VK_ENTER){
            p1.shot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public int getEnemyNum(){
        return ets.size();
    }
    public int getEnSize(){
        return enSize;
    }
}
