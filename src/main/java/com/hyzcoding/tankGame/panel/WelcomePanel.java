package com.hyzcoding.tankGame.panel;

import com.hyzcoding.tankGame.constant.FilePath;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class WelcomePanel extends JPanel implements Runnable, KeyListener {
    private Image battleCity;
    private Image dTeam;
    private Image selectP1;
    private int cursor = 0;
    private int time = 500;

    private int maxscore = 20000;
    private int gamescore = 0;

    private int shine1 = 10;
    private int shine2 = 10;

    private boolean enter = false;

    public WelcomePanel() {
        this.setBackground(Color.BLACK);
        try{
            battleCity= ImageIO.read(this.getClass().getResource(FilePath.BATTLE_CITY));
            dTeam=ImageIO.read(this.getClass().getResource(FilePath.D_TEAM));
            selectP1=ImageIO.read(this.getClass().getResource(FilePath.SELECT_P1));
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Font f = new Font("Consolas", Font.PLAIN, 22);
        g.drawImage(battleCity, 120, 50+time, 500, 200, this);
        g.drawImage(selectP1, 280, 284+time+cursor, 20, 20, this);
        g.drawImage(dTeam, 320, 380+time, 120, 30, this);
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
            shine2 --;
        }else if(cursor == 0){
            g.drawString("2  PLAYERS", 330, 350+time);
            shine2 = 20;
        }else{
            g.drawString("2  PLAYERS", 330, 350+time);
        }
        //
        g.setFont(f);
        g.drawString("© 1980-2018 NAMCO LTD.", 250, 450+time);
        g.drawString("ALL RIGHTS RESERVED", 276, 480+time);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode() ){
            case  KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                cursor = 0;
                return;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                cursor = 50;
                return;
            case KeyEvent.VK_ENTER:
                enter = true;
                return;
            default:
                time=0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
            this.repaint();
            if(time>0){
                time -=3;
                cursor = 0;
            }
            //重绘WelcomePanel
            this.repaint();
            if(cursor ==0&&enter&&time == 0){
                break;
            }
        }
    }
}
