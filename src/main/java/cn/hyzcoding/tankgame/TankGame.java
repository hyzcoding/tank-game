package cn.hyzcoding.tankgame;

import cn.hyzcoding.tankgame.component.panel.GamePanel;
import cn.hyzcoding.tankgame.component.panel.ScorePanel;
import cn.hyzcoding.tankgame.component.panel.StagePanel;
import cn.hyzcoding.tankgame.component.panel.WelcomePanel;
import cn.hyzcoding.tankgame.component.wave.AePlayWave;
import cn.hyzcoding.tankgame.consts.FilePathConst;
import cn.hyzcoding.tankgame.consts.PaintConst;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * tank-game
 *
 * @author hyzcoding
 * @version 2.0.0
 * @date 2018/06/07
 * @since 1.0.0
 */
public class TankGame extends JFrame implements Runnable {

    /**
     * 构建窗体，添加组件
     * 组件1，GamePanel
     * 组件2，ScorePanel--显示分数
     * 组件3，LifePanel--显示生命值
     * 组件4，MenuBar--菜单条,选择继续游戏或开始新游戏
     */
    private static final long serialVersionUID = 2L;
    public static boolean gameTrigger = true;
    public static int score = 0;

    /**
     * welcome --欢迎，选择界面
     */
    WelcomePanel welcomePanel = null;
    /**
     *
     */
    StagePanel stagePanel = null;
    /**
     * GamePanel--坦克运行界面
     */
    GamePanel gamePanel = null;
    /**
     * scorePanel --成绩界面，以及其中包含的成绩数据
     */
    ScorePanel scorePanel = null;
    Thread twp = null;

    Thread tstp = null;

    Thread tgp = null;

    /**
     *
     */
    Thread tsp = null;

    /**
     *
     */
    JLabel boundsL = null;
    /**
     *
     */
    JLabel boundsU = null;

    JLabel boundsD = null;

    /**
     * 构造函数，新建组件，线程
     */
    public TankGame() {

    }

    /**
     * @param args 启动参数
     */

    public static void main(String[] args) {

        TankGame tankgame = new TankGame();
        Thread ttg = new Thread(tankgame);
        ttg.start();
    }

    public void addWelPanel() throws InterruptedException {

        welcomePanel = new WelcomePanel();

        this.setResizable(false);
        this.add(welcomePanel);
        this.addKeyListener(welcomePanel);
        this.setTitle(PaintConst.TANK_TITLE);
        this.setSize(PaintConst.WIN_WIDTH, PaintConst.WIN_HEIGHT);
        setProperties();
        //新建并启动welcomePanel线程tgp。
        twp = new Thread(welcomePanel);
        twp.start();
        twp.join();
        //executorService.schedule(twp,50L, TimeUnit.MILLISECONDS);
    }

    public void addStagePanel() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        stagePanel = new StagePanel(latch);
        //移除welcome面板
        this.remove(welcomePanel);
        this.welcomePanel = null;
        this.add(stagePanel);
        setProperties();


        tstp = new Thread(stagePanel);

        //移除welcome面板
        AePlayWave apw = new AePlayWave(Objects.requireNonNull(this.getClass().getResource(FilePathConst.WAV_START)).getPath(), latch);
        tstp.start();
        apw.start();
        latch.await();
        this.remove(this.stagePanel);
    }

    public void addGasPanel() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        gamePanel = new GamePanel(latch);
        scorePanel = new ScorePanel(latch);
        boundsL = new JLabel();
        boundsL.setOpaque(true);
        boundsL.setBackground(Color.gray);
        boundsU = new JLabel();
        boundsU.setOpaque(true);
        boundsU.setBackground(Color.gray);
        boundsD = new JLabel();
        boundsD.setOpaque(true);
        boundsD.setBackground(Color.gray);
        this.addKeyListener(gamePanel);
        this.add(boundsL, BorderLayout.WEST);
        this.add(boundsU, BorderLayout.NORTH);
        this.add(boundsD, BorderLayout.SOUTH);
        this.add(gamePanel, BorderLayout.CENTER);
        this.add(scorePanel, BorderLayout.EAST);
        gamePanel.setPreferredSize(new Dimension(680, 560));
        scorePanel.setPreferredSize(new Dimension(100, 600));
        boundsL.setPreferredSize(new Dimension(20, 600));
        boundsU.setPreferredSize(new Dimension(500, 20));
        boundsD.setPreferredSize(new Dimension(500, 20));
        setProperties();
        tgp = new Thread(gamePanel);
        tgp.start();
        //
        tsp = new Thread(scorePanel);
        tsp.start();
        latch.await();
    }

    public void setProperties() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void run() {
        // 启动欢迎页

        try {
            while (true){
                addWelPanel();
                addStagePanel();
                addGasPanel();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}