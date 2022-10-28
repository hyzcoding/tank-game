package main.cn.hyzcoding.tankgame.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class ScorePanel extends JPanel implements Runnable{

    /**
     *
     */
    private static final long serialVersionUID = 2L;
    /**
     *
     */
    int score ;
    int etNum ;
    /**
     *
     */
    Image etIcon = null;
    Image ptIcon = null;
    Image flag = null;
    Image p1 = null;
    Image p2 = null;
    public ScorePanel(){

        try{
            etIcon= ImageIO.read(new File("images/score/etIcon.png"));
            ptIcon=ImageIO.read(new File("images/score/ptIcon.png"));
            flag=ImageIO.read(new File("images/score/flag.png"));
            p1=ImageIO.read(new File("images/score/p1.png"));
            p2=ImageIO.read(new File("images/score/p2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            this.setBackground(Color.GRAY);
        }

    }
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

            //重绘WelcomePanel
            this.repaint();

        }
    }
}
