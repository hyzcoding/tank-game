package main.cn.hyzcoding.tankgame;

import main.cn.hyzcoding.tankgame.entity.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;


import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * tank-game
 * @author hyzcoding
 * @date 2018/06/07
 * @version 2.0.0
 * @since 1.0.0
 */
public class TankGame extends JFrame implements Runnable{



	/**
	 * 构建窗体，添加组件
	 * 组件1，GamePanel
	 * 组件2，ScorePanel--显示分数
	 * 组件3，LifePanel--显示生命值
	 * 组件4，MenuBar--菜单条,选择继续游戏或开始新游戏
	 */
	private static final long serialVersionUID = 2L;

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
	GamePanel gamePanel=null;
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
	Thread tsp =null;

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
	 *
	 * @param args 启动参数
	 */

	public static void main(String[] args) {

		TankGame2_0 tankgame = new TankGame2_0();
		Thread ttg = new Thread(tankgame);
		ttg.start();
	}
	/**
	 * 构造函数，新建组件，线程
	 */
	public TankGame2_0(){

	}
	public void addwelPanel(){
		welcomePanel = new WelcomePanel();

		this.setResizable(false);

		this.add(welcomePanel);
		this.addKeyListener(welcomePanel);
		this.setTitle("Tank Game");
		this.setSize(800,600);
		setProperties();

		//新建并启动welcomePanel线程tgp。
		twp = new Thread(welcomePanel);
		twp.start();
	}

	public void addstagePanel(){

		stagePanel = new StagePanel();
		this.remove(welcomePanel);
		this.welcomePanel = null;
		this.add(stagePanel);
		setProperties();
		tstp = new Thread(stagePanel);
		tstp.start();
		//移除welcome面板
		AePlayWave apw=new AePlayWave("voice/start.wav");
		apw.start();
	}

	public void addgasPanel(){

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
		//移除welcome面板
		this.addKeyListener(gamePanel);
		//移除welcome面板
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

		//移除welcome面板
		tgp = new Thread(gamePanel);
		tgp.start();
		//
		tsp = new Thread(scorePanel);
		tsp.start();

		setProperties();
	}

	public void setProperties(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void run() {
		// 启动欢迎页
		addwelPanel();
		while(true){
			if(gamePanel == null&&scorePanel == null){
				if(!twp.isAlive()&&stagePanel == null ){
					addstagePanel();
				}else if(welcomePanel == null && !tstp.isAlive()){
					addgasPanel();
				}

			}
			if(gamePanel != null&&scorePanel != null){
				scorePanel.etNum = gamePanel.ets.size();
				scorePanel.score = gamePanel.enSize-scorePanel.etNum;
			}
		}
	}
}