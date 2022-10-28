package main.cn.hyzcoding.tankgame.component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class WelcomePanel extends JPanel implements Runnable, KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 2L;

    //
    int maxscore = 20000;
    int gamescore = 0;
    int cursor = 0;
    //
    boolean enter = false;
    //
    int time = 500;
    //
    int shine1 = 10;
    int shine2 = 10;
    //
    Image battlecity1 = null;
    Image Dteam = null;
    Image selectP1 = null;
    //
    public WelcomePanel(){

        this.setBackground(Color.black);
        try{
            battlecity1= ImageIO.read(new File("images/welcome/battlecity1.png"));
            Dteam=ImageIO.read(new File("images/welcome/Dteam.png"));
            selectP1=ImageIO.read(new File("images/welcome/selectP1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        //初始化界面，否则白屏。
    }
    public void paint(Graphics g){
        super.paint(g);
        //
        Font f = new Font("Consolas", Font.PLAIN, 22);
        g.drawImage(battlecity1, 120, 50+time, 500, 200, this);
        g.drawImage(selectP1, 280, 284+time+cursor, 20, 20, this);
        g.drawImage(Dteam, 320, 380+time, 120, 30, this);
        g.setColor(Color.white);
        //
        g.setFont(f);
        g.drawString("I-\t \t \t"+String.format("%04d ", gamescore), 100, 30+time);
        g.drawString("HI-\t \t \t"+maxscore, 250, 30+time);
        //
        g.setFont(new Font("Consolas", Font.BOLD, 22));
        if(time>0){
            g.drawString("1  PLAYER", 330, 300+time);
        }else if(cursor == 0 && shine1 % 2 == 1){
            g.drawString("1  PLAYER", 330, 300+time);
            shine1--;
        }else if(cursor == 0 && shine1 % 2 == 0&&shine1>0){

            shine1 --;
        }else if(cursor == 50){
            g.drawString("1  PLAYER", 330, 300+time);
            shine1 = 20;
        }else{
            g.drawString("1  PLAYER", 330, 300+time);
        }
        if(cursor == 50 && shine2 % 2 == 1){
            g.drawString("2  PLAYERS", 330, 350+time);
            shine2 --;
        }else if(cursor == 50 && shine2 % 2 == 0&&shine2>0){
            shine2 --;;
        }else if(cursor == 0){
            g.drawString("2  PLAYERS", 330, 350+time);
            shine2 = 20;
        }else{
            g.drawString("2  PLAYERS", 330, 350+time);
        }
        //
        g.setFont(f);
        g.drawString("? 1980-2018 NAMCO LTD.", 250, 450+time);
        g.drawString("ALL RIGHTS RESERVED", 276, 480+time);
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W){
            cursor = 0;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
            cursor = 50;
        }else if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enter = true;
        }else{
            time = 0;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    @Override
    public synchronized void run() {
        // TODO Auto-generated method stub
        while(true){
            try {
                //设置休眠时间
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(time>0){
                time -=3;
                cursor = 0;
            }
            //重绘WelcomePanel
            this.repaint();
            if(cursor ==0&&enter == true&&time == 0){
                break;
            }
        }
    }

}