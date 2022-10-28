package cn.hyzcoding.tankgame.component.panel;

import cn.hyzcoding.tankgame.TankGame;
import cn.hyzcoding.tankgame.consts.FilePathConst;
import cn.hyzcoding.tankgame.consts.FontConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class ScorePanel extends JPanel implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 2L;
    /**
     *
     */
    public int etNum;
    /**
     *
     */
    Image etIcon = null;
    Image ptIcon = null;
    Image flag = null;
    Image p1 = null;
    Image p2 = null;
    CountDownLatch latch;

    public ScorePanel(CountDownLatch latch) {
        this.latch = latch;
        try {
            etIcon = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(FilePathConst.ICON_ENEMY_TANK)).getPath()));
            ptIcon = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(FilePathConst.ICON_PLAYER_TANK)).getPath()));
            flag = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(FilePathConst.ICON_SCORE_FLAG)).getPath()));
            p1 = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(FilePathConst.ICON_SCORE_P1)).getPath()));
            p2 = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(FilePathConst.ICON_SCORE_P2)).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.setBackground(Color.GRAY);
        }

    }

    public void paint(Graphics g) {

        super.paint(g);
        int set = 0;
        for (int i = 0; i < etNum - 1; i++) {
            g.drawImage(etIcon, 10, 10 + set, 20, 20, this);
            if (i % 2 == 0) {
                g.drawImage(etIcon, 50, 10 + set, 20, 20, this);
                set += 20;
            }
        }
        g.drawImage(p1, 30, 230, 40, 20, this);
        g.drawImage(ptIcon, 30, 260, 15, 15, this);

        g.setFont(new Font(FontConst.FONT_CONSOLE, Font.BOLD, 23));
        g.setColor(Color.black);
        g.drawString(Integer.toString(TankGame.score), 55, 275);
    }

    @Override
    public synchronized void run() {
       do {
            try {
                //设置休眠时间
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            //重绘 Panel
            this.repaint();
        } while (TankGame.gameTrigger);
        latch.countDown();
    }
}
