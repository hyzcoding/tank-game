package cn.hyzcoding.tankgame.component.panel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static cn.hyzcoding.tankgame.consts.FontConst.FONT_CONSOLE;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class StagePanel extends JPanel implements Runnable {
    /**
     *
     */
    private static final long serialVersionUID = 2L;
    /**
     *
     */
    int life = 50;
    CountDownLatch latch;
    public StagePanel(CountDownLatch latch ) {
        this.latch = latch;
        this.setBackground(Color.gray);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Font f = new Font(FONT_CONSOLE, Font.BOLD, 30);
        g.setFont(f);
        g.drawString("STAGE 1", 330, 250);
    }

    @Override
    public void run() {
        do {
            this.repaint();
            try {
                //设置休眠时间
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            life--;
        } while (life >= 0);
        latch.countDown();
    }
}