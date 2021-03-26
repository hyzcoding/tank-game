package com.hyzcoding.tankGame.panel;

import com.hyzcoding.tankGame.constant.FilePath;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class ScorePanel extends JPanel implements Runnable {
    /**
     *
     */
    private int score ;
    int etNum ;
    /**
     *
     */
    Image etIcon = null;
    Image ptIcon = null;
    Image flag = null;
    Image p1 = null;
    Image p2 = null;

    public ScorePanel() {
        try{
            etIcon= ImageIO.read(this.getClass().getResource(FilePath.ET_ICON));
            ptIcon=ImageIO.read(this.getClass().getResource(FilePath.PT_ICON));
            flag=ImageIO.read(this.getClass().getResource(FilePath.FLAG));
            p1=ImageIO.read(this.getClass().getResource(FilePath.SCORE_P1));
            p2=ImageIO.read(this.getClass().getResource(FilePath.SCORE_P2));
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            this.setBackground(Color.GRAY);
        }
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        int set = 0;
        for(int i = 0;i<etNum-1;i++){
            g.drawImage(etIcon, 10, 10+set, 20, 20, this);
            if(i%2 == 0){
                g.drawImage(etIcon, 50, 10+set, 20, 20, this);
                set+=20;
            }
        }
        g.drawImage(p1, 30, 230, 40, 20, this);
        g.drawImage(ptIcon, 30, 260, 15, 15, this);

        g.setFont(new Font("Consolas", Font.BOLD, 23));
        g.setColor(Color.black);
        g.drawString(Integer.toString(score), 55, 275);
    }

    @Override
    public void run() {

        while(true){
            try {
                //设置休眠时间
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("error ------ "+e.getMessage());
            }
            //重绘WelcomePanel
            this.repaint();
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setEtNum(int etNum) {
        this.etNum = etNum;
    }
}
