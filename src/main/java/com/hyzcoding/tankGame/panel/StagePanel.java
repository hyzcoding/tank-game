package com.hyzcoding.tankGame.panel;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class StagePanel extends JPanel implements Runnable {
    private int life = 50;
    public StagePanel(){
        this.setBackground(Color.gray);
    }
    public void paint(Graphics g){
        super.paint(g);
        Font f = new Font("Consolas", Font.BOLD, 30);
        g.setFont(f);
        g.drawString("STAGE 1", 330, 250);
    }
    @Override
    public void run() {
        while(true){
            this.repaint();
            try {
                //设置休眠时间
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            life--;
            if(life<0)
                break;
        }

    }
}
