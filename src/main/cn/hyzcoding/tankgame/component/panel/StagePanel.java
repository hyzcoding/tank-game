package main.cn.hyzcoding.tankgame.component;

import javax.swing.*;
import java.awt.*;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class StagePanel extends JPanel implements Runnable{
    /**
     *
     */
    private static final long serialVersionUID = 2L;
    /**
     *
     */
    int life = 50;
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
                //…Ë÷√–›√ﬂ ±º‰
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            life--;

            if(life<0)
                break;
        }

    }
}