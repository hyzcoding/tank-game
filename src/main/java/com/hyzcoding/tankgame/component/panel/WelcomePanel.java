package com.hyzcoding.tankgame.component.panel;

import com.hyzcoding.tankgame.TankGame;
import com.hyzcoding.tankgame.component.FileUtils;
import com.hyzcoding.tankgame.consts.FilePathConst;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.hyzcoding.tankgame.consts.FontConst.FONT_CONSOLE;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class WelcomePanel extends JPanel implements Runnable, KeyListener {
    public static final String STR_1_PLAYER = "1  PLAYER";
    public static final String STR_2_PLAYER = "2  PLAYERS";
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
    int time = 501;
    //
    int shine1 = 10;
    int shine2 = 10;
    //
    Image battlecity1 = null;
    Image dteam = null;
    Image selectP1 = null;

    //
    public WelcomePanel() {

        this.setBackground(Color.black);
        try {
            battlecity1 = ImageIO.read(FileUtils.getFilePath(FilePathConst.IMG_BATTLE_CITY));
            dteam = ImageIO.read(FileUtils.getFilePath(FilePathConst.IMG_D_TEAM));
            selectP1 = ImageIO.read(FileUtils.getFilePath(FilePathConst.IMG_SELECT_P1));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        //初始化界面，否则白屏。
    }

    public void paint(Graphics g) {
        super.paint(g);
        //
        Font f = new Font(FONT_CONSOLE, Font.PLAIN, 22);
        g.drawImage(battlecity1, 120, 50 + time, 500, 200, this);
        g.drawImage(selectP1, 280, 284 + time + cursor, 20, 20, this);
        g.drawImage(dteam, 320, 380 + time, 120, 30, this);
        g.setColor(Color.white);
        //
        g.setFont(f);
        g.drawString(String.format("I-\t \t \t%04d ", gamescore), 100, 30 + time);
        g.drawString(String.format("HI-\t \t \t%d", maxscore), 250, 30 + time);
        //
        g.setFont(new Font(FONT_CONSOLE, Font.BOLD, 22));
        if (time > 0) {
            g.drawString(STR_1_PLAYER, 330, 300 + time);
        } else if (cursor == 0 && shine1 % 2 == 1) {
            g.drawString(STR_1_PLAYER, 330, 300 + time);
            shine1--;
        } else if (cursor == 0 && shine1 % 2 == 0 && shine1 > 0) {

            shine1--;
        } else if (cursor == 50) {
            g.drawString(STR_1_PLAYER, 330, 300 + time);
            shine1 = 20;
        } else {
            g.drawString(STR_1_PLAYER, 330, 300 + time);
        }
        if (cursor == 50 && shine2 % 2 == 1) {
            g.drawString(STR_2_PLAYER, 330, 350 + time);
            shine2--;
        } else if (cursor == 50 && shine2 % 2 == 0 && shine2 > 0) {
            shine2--;
        } else if (cursor == 0) {
            g.drawString(STR_2_PLAYER, 330, 350 + time);
            shine2 = 20;
        } else {
            g.drawString(STR_2_PLAYER, 330, 350 + time);
        }
        //
        g.setFont(f);
        g.drawString("? 1980-2018 NAMCO LTD.", 250, 450 + time);
        g.drawString("ALL RIGHTS RESERVED", 276, 480 + time);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            cursor = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            cursor = 50;
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter = true;
        } else {
            time = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public synchronized void run() {
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            if (time > 0) {
                time -= 3;
                cursor = 0;
            }
            //重绘WelcomePanel
            this.repaint();
        } while (cursor != 0 || !enter || time != 0);
    }

}