package com.hyzcoding.tankGame.frame;

import com.hyzcoding.tankGame.panel.GamePanel;
import com.hyzcoding.tankGame.panel.ScorePanel;
import com.hyzcoding.tankGame.panel.StagePanel;
import com.hyzcoding.tankGame.panel.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author hyz
 * @since 1.0
 */
public class MainFrame extends JFrame implements Runnable {
    private WelcomePanel welcomePanel;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private StagePanel stagePanel;

    private JLabel boundsL;
    private JLabel boundsU;
    private JLabel boundsD;

    private Thread twp;
    private Thread tstp;
    private Thread tgp;
    private Thread tsp;
    @Override
    public void run() {
        addWelcomePanel();
        while (true){
            if(gamePanel == null&&scorePanel == null){
                if(!twp.isAlive()&&stagePanel == null ){
                    addStagePanel();
                }else if(welcomePanel == null && !tstp.isAlive()){
                    addGameAndScorePanel();
                }
            }
            if(gamePanel != null&&scorePanel != null){
                scorePanel.setEtNum(gamePanel.getEnemyNum());
                scorePanel.setScore(gamePanel.getEnSize()-gamePanel.getEnemyNum());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private void setProperties(){
        this.setSize(800,600);
        this.setTitle("Tank Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void addWelcomePanel(){
        welcomePanel =new WelcomePanel();
        this.setResizable(false);

        this.add(welcomePanel);
        this.addKeyListener(welcomePanel);

        setProperties();
        twp = new Thread(welcomePanel);
        twp.start();
    }
    private void addGameAndScorePanel(){
        gamePanel = new GamePanel();

        scorePanel = new ScorePanel();

        boundsL = new JLabel();
        boundsL.setOpaque(true);
        boundsL.setBackground(Color.gray);
        boundsU = new JLabel();
        boundsU.setOpaque(true);
        boundsU.setBackground(Color.gray);
        boundsD = new JLabel();
        boundsD.setOpaque(true);
        boundsD.setBackground(Color.gray);
        //添加Action
        this.addKeyListener(gamePanel);
        //移除stagePanel面板
        this.remove(this.stagePanel);
        //

        this.add(boundsL,BorderLayout.WEST);
        this.add(boundsU,BorderLayout.NORTH);
        this.add(boundsD,BorderLayout.SOUTH);
        this.add(gamePanel,BorderLayout.CENTER);
        this.add(scorePanel,BorderLayout.EAST);
        gamePanel.setPreferredSize(new Dimension(680,560));
        scorePanel.setPreferredSize(new Dimension(100,600));
        boundsL.setPreferredSize(new Dimension(20,600));
        boundsU.setPreferredSize(new Dimension(500,20));
        boundsD.setPreferredSize(new Dimension(500,20));

        //新建并启动gamePanel线程tgp。
        tgp = new Thread(gamePanel);
        tgp.start();
        //
        tsp = new Thread(scorePanel);
        tsp.start();

        setProperties();
    }
    private void addStagePanel(){
        stagePanel = new StagePanel();
        this.remove(welcomePanel);
        this.welcomePanel = null;
        this.add(stagePanel);
        setProperties();
        tstp = new Thread(stagePanel);
        tstp.start();
        //移除welcome面板
//        AePlayWave apw=new AePlayWave("voice/start.wav");
//        apw.start();
    }
}
