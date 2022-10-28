package cn.hyzcoding.tankgame.component.panel;


import cn.hyzcoding.tankgame.TankGame;
import cn.hyzcoding.tankgame.consts.FilePathConst;
import cn.hyzcoding.tankgame.entity.role.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class GamePanel extends JPanel implements Runnable, KeyListener {
    //版本号2.0
    private static final long serialVersionUID = 2L;
    //创建敌人坦克vector数组+设置初始敌人坦克数量
    public Vector<EnemyTank> ets = new Vector<>();
    public int enSize = 5;
    //创建玩家坦克对象
    PlayerTank p1;
    //
    Born bornP;
    //创建出生vector数组
    Vector<Born> borns;
    //创建爆炸vector数组
    Vector<Blast> blasts = new Vector<>();
    //创建图片对象
    //玩家坦克
    Image image_p1tankU;
    Image image_p1tankR;
    Image image_p1tankD;
    Image image_p1tankL;
    //敌人坦克
    Image enemy3U;
    Image enemy3R;
    Image enemy3D;
    Image enemy3L;
    //出生
    Image born1;
    Image born2;
    Image born3;
    Image born4;

    Image bornP1;
    Image bornP2;
    //爆炸
    Image blast1;
    Image blast2;
    Image blast3;
    Image blast4;
    Image blast5;
    Image blast6;
    Image blast7;
    Image blast8;
    //
    @SuppressWarnings("unused")
    Image baseFine;
    CountDownLatch latch;

    public GamePanel(CountDownLatch latch) {
        this.latch = latch;
        //初始化玩家坦克
        p1 = new PlayerTank(350, 470, 'U', true, true);
        bornP = new Born(p1.x, p1.y);
        this.setBackground(Color.BLACK);
        //初始化敌人坦克,添加至面板
        borns = new Vector<>();
        for (int i = 0; i < enSize; i++) {
            EnemyTank et = new EnemyTank(80 * i, 0, 'D', false, true);
            Born born = new Born(et.x, et.y);
            borns.add(born);

            Thread tet = new Thread(et);
            tet.start();
            ets.add(et);
        }

        //导入图片
        //导入玩家坦克图片
        image_p1tankU = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_PLAYER_U)).getPath());
        image_p1tankR = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_PLAYER_R)).getPath());
        image_p1tankD = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_PLAYER_D)).getPath());
        image_p1tankL = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_PLAYER_L)).getPath());
        //导入敌人坦克图片
        enemy3U = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_ENEMY_U)).getPath());
        enemy3R = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_ENEMY_R)).getPath());
        enemy3D = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_ENEMY_D)).getPath());
        enemy3L = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_ENEMY_L)).getPath());
        //出生图片
        born1 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_1)).getPath());
        born2 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_2)).getPath());
        born3 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_3)).getPath());
        born4 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_4)).getPath());

        bornP1 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_P1)).getPath());
        bornP2 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BORN_P2)).getPath());
        //爆炸图片
        blast1 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_1)).getPath());
        blast2 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_2)).getPath());
        blast3 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_3)).getPath());
        blast4 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_4)).getPath());
        blast5 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_5)).getPath());
        blast6 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_6)).getPath());
        blast7 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_7)).getPath());
        blast8 = Toolkit.getDefaultToolkit().createImage(Objects.requireNonNull(this.getClass().getResource(FilePathConst.IMG_BLAST_8)).getPath());
        //障碍物图片


    }

    //碰撞
    public void collideEt() {
        for (int i = 0; i < ets.size(); i++) {
            for (int j = 0; j < i; j++) {
                ets.get(i).collide(ets.get(j));
            }
        }
    }

    //绘制图像
    public void paint(Graphics g) {
        super.paint(g);

        //绘制玩家坦克
        if (p1.isLive) {
            this.drawTank(p1.x, p1.y, p1.width, p1.height, p1.direction, g, p1.type);
            for (int i = 0; i < p1.bls.size(); i++) {
                Bullet p1bl = p1.bls.get(i);
                if (p1bl.isLive) {
                    //绘制子弹
                    g.setColor(Color.YELLOW);
                    g.fillRect(p1bl.x, p1bl.y, 4, 4);
                } else {
                    // TODO
                    p1.bls.remove(i);
                }
            }
        }
        //绘制出生
        for (int i = 0; i < borns.size(); i++) {
            Born born = borns.get(i);
            if (born.times % 4 == 0) {
                g.drawImage(born1, born.x, born.y, 30, 40, this);
            } else if (born.times % 4 == 1) {
                g.drawImage(born2, born.x, born.y, 30, 40, this);
            } else if (born.times % 4 == 2) {
                g.drawImage(born3, born.x, born.y, 30, 40, this);
            } else {
                g.drawImage(born4, born.x, born.y, 30, 40, this);
            }
            born.lifeDown();

            if (born.times == 0) {
                borns.remove(born);
            }
        }
        //
        if (bornP.isLive) {
            if (bornP.times % 2 == 0) {
                g.drawImage(bornP1, p1.x - 5, p1.y + 10, 40, 30, this);
            } else {
                g.drawImage(bornP2, p1.x - 5, p1.y + 10, 40, 30, this);
            }
            bornP.lifeDown();
        }

        //绘制敌人坦克
        for (EnemyTank et : ets) {
            if (et.isLive && borns.size() == 0) {
                this.drawTank(et.x, et.y, et.width, et.height, et.direction, g, et.type);
                et.bls.removeIf(bullet -> !bullet.isLive);
                et.bls.forEach(bullet -> {
                    g.setColor(Color.white);
                    g.fillRect(bullet.x, bullet.y, 4, 4);
                });
            }
        }
        //绘制炸弹
        ets.forEach(enemyTank -> {
            Blast blastP1 = enemyTank.hit(p1);
            if (blastP1 != null) {
                blasts.add(blastP1);
            }
            Blast blastEt = p1.hit(enemyTank);
            if (blastEt != null) {
                blasts.add(blastEt);
            }
        });
        ets.removeIf(enemyTank -> p1.hit(enemyTank) != null);
        drawBlast(blasts, g);
        //绘制障碍物

    }

    /**
     * 绘制爆炸
     * @param blasts 爆炸位置序列
     * @param g 绘图
     */
    public void drawBlast(Vector<Blast> blasts, Graphics g) {
        for (int i = 0; i < blasts.size(); i++) {
            Blast blast = blasts.get(i);
            if (blast.times % 8 == 0) {
                g.drawImage(blast1, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 1) {
                g.drawImage(blast2, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 2) {
                g.drawImage(blast3, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 3) {
                g.drawImage(blast4, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 4) {
                g.drawImage(blast5, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 5) {
                g.drawImage(blast6, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 6) {
                g.drawImage(blast7, blast.x, blast.y, 30, 40, this);
            } else if (blast.times % 8 == 7) {
                g.drawImage(blast3, blast.x, blast.y, 30, 40, this);
            } else {
                g.drawImage(blast8, blast.x, blast.y, 30, 40, this);
            }
            blast.lifeDown();
            if (blast.times == 0) {
                blasts.remove(blast);

            }
        }
    }

    //创建绘制坦克方法--（敌人和玩家坦克）false敌人，true玩家
    public void drawTank(int x, int y, int width, int height, char direction, Graphics g, boolean type) {
        switch (direction) {
            case 'U':
                if (type) {
                    g.drawImage(image_p1tankU, x, y, width, height, this);
                } else {
                    g.drawImage(enemy3U, x, y, width, height, this);
                }
                break;
            case 'R':
                if (type) {
                    g.drawImage(image_p1tankR, x, y, height, width, this);
                } else {
                    g.drawImage(enemy3R, x, y, height, width, this);
                }
                break;
            case 'D':
                if (type) {
                    g.drawImage(image_p1tankD, x, y, width, height, this);
                } else {
                    g.drawImage(enemy3D, x, y, width, height, this);
                }
                break;
            case 'L':
                if (type) {
                    g.drawImage(image_p1tankL, x, y, height, width, this);
                } else {
                    g.drawImage(enemy3L, x, y, height, width, this);
                }
                break;
        }


    }

    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub
        do {
            try {
                //设置休眠时间
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //重绘GamePanel
            this.repaint();
            collideEt();
            if (!p1.isLive()) {
                TankGame.gameTrigger = false;
                break;
            }
        } while (true);
        latch.countDown();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            if (p1.direction != 'U') p1.move('U');
            p1.move('U');
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            if (p1.direction != 'R') p1.move('R');
            p1.move('R');
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            if (p1.direction != 'D') p1.move('D');
            p1.move('D');
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (p1.direction != 'L') p1.move('L');
            p1.move('L');
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_ENTER) {
            p1.shot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}

