package com.tankgame2_0.hyz;
/**
 * 存放面板组件
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

//定义 GamePanel类用于绘图
class GamePanel extends JPanel implements Runnable,KeyListener{
	//版本号2.0
	private static final long serialVersionUID = 2L;
	//创建玩家坦克对象
	PlayerTank p1 = null;
	//创建敌人坦克vector数组+设置初始敌人坦克数量
	Vector <EnemyTank> ets = new Vector <EnemyTank>();
	int enSize = 8;
	//
	Born bornP = null;
	//创建出生vector数组
	Vector <Born> borns = new Vector <Born>();
	//创建爆炸vector数组
	Vector <Blast> blasts = new Vector <Blast>();
	//创建图片对象
		//玩家坦克
	Image image_p1tankU = null;
	Image image_p1tankR = null;
	Image image_p1tankD = null;
	Image image_p1tankL = null;
		//敌人坦克
	Image enemy3U = null;
	Image enemy3R = null;
	Image enemy3D = null;
	Image enemy3L = null;
		//出生
	Image born1 = null;
	Image born2 = null;
	Image born3 = null;
	Image born4 = null;
	
	Image bornP1 = null;
	Image bornP2 = null;
		//爆炸
	Image blast1 = null;
	Image blast2 = null;
	Image blast3 = null;
	Image blast4 = null;
	Image blast5 = null;
	Image blast6 = null;
	Image blast7 = null;
	Image blast8 = null;
		//
	Image baseFine = null;
	
	
	public GamePanel(){
		//初始化玩家坦克
		p1=new PlayerTank(350,470,'U',true,true);
		bornP = new Born(p1.x,p1.y);
		this.setBackground(Color.BLACK);
		//初始化敌人坦克,添加至面板
		for(int i=0;i<enSize;i++){
			EnemyTank et=new EnemyTank(80*i,0,'D',false,true);
			Born born = new Born(et.x,et.y);
			borns.add(born);
			
			Thread tet=new Thread(et);
			tet.start();
			ets.add(et);	
		}
		
		//导入图片
			//导入玩家坦克图片
			image_p1tankU=Toolkit.getDefaultToolkit().createImage("images/player/p1tankU.gif");
			image_p1tankR=Toolkit.getDefaultToolkit().createImage("images/player/p1tankR.gif");
			image_p1tankD=Toolkit.getDefaultToolkit().createImage("images/player/p1tankD.gif");
			image_p1tankL=Toolkit.getDefaultToolkit().createImage("images/player/p1tankL.gif");
			//导入敌人坦克图片
			enemy3U=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3U.gif");
			enemy3R=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3R.gif");
			enemy3D=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3D.gif");
			enemy3L=Toolkit.getDefaultToolkit().createImage("images/enemy/enemy3L.gif");
			//出生图片
			born1=Toolkit.getDefaultToolkit().createImage("images/born/born1.gif");
			born2=Toolkit.getDefaultToolkit().createImage("images/born/born2.gif");
			born3=Toolkit.getDefaultToolkit().createImage("images/born/born3.gif");
			born4=Toolkit.getDefaultToolkit().createImage("images/born/born4.gif");
			
			bornP1 = Toolkit.getDefaultToolkit().createImage("images/born/bornP1.png");
			bornP2 = Toolkit.getDefaultToolkit().createImage("images/born/bornP2.png");
			//爆炸图片
			blast1=Toolkit.getDefaultToolkit().createImage("images/blast/blast1.gif");
			blast2=Toolkit.getDefaultToolkit().createImage("images/blast/blast2.gif");
			blast3=Toolkit.getDefaultToolkit().createImage("images/blast/blast3.gif");
			blast4=Toolkit.getDefaultToolkit().createImage("images/blast/blast4.gif");
			blast5=Toolkit.getDefaultToolkit().createImage("images/blast/blast5.gif");
			blast6=Toolkit.getDefaultToolkit().createImage("images/blast/blast6.gif");
			blast7=Toolkit.getDefaultToolkit().createImage("images/blast/blast7.gif");
			blast8=Toolkit.getDefaultToolkit().createImage("images/blast/blast8.gif");
			//障碍物图片
			
			
	}
	//碰撞
	public void collideEt(){
		for(int i = 0; i < ets.size();i ++){
			for(int j = 0; j < i;j ++){
				ets.get(i).collide(ets.get(j));
			}
		}
	}
	//绘制图像
	public void paint(Graphics g){
		super.paint(g);
		
		//绘制玩家坦克
		if(p1.isLive){
			this.drawtank(p1.x, p1.y, p1.width,p1.height, p1.direction, g, p1.type);
			for(int i = 0;i<p1.bls.size();i++){
				Bullet p1bl=p1.bls.get(i);
				if(p1bl.isLive){
					//绘制子弹
					g.setColor(Color.YELLOW);
					g.fillRect(p1bl.x, p1bl.y, 4, 4);
					
				}else{
					p1.bls.remove(i);
				}
			}
		}
		//绘制出生
		for(int i=0;i<borns.size();i++){
			Born born = borns.get(i);
			if(born.times%4 == 0){
				g.drawImage(born1, born.x, born.y,30,40, this);
			}else if(born.times%4 == 1){
				g.drawImage(born2, born.x, born.y,30,40, this);
			}else if(born.times%4 == 2){
				g.drawImage(born3, born.x, born.y,30,40, this);
			}else{
				g.drawImage(born4, born.x, born.y,30,40, this);
			}
			born.lifeDown();
			
			if(born.times == 0){
				borns.remove(born);
			}	
		}
		//
		if(bornP.isLive){
			if(bornP.times%2 == 0){
				g.drawImage(bornP1, p1.x-5, p1.y+10,40,30, this);
			}else{
				g.drawImage(bornP2, p1.x-5, p1.y+10,40,30, this);
			}
			bornP.lifeDown();
		}
		
		//绘制敌人坦克
		for(int i=0;i<ets.size();i++){
			EnemyTank et=ets.get(i);
			if(et.isLive&&borns.size() == 0){
				this.drawtank(et.x, et.y, et.width, et.height, et.direction, g, et.type);
				for(int j = 0;j<et.bls.size();j++){
					Bullet etbl=et.bls.get(j);
					if(etbl.isLive){
						//绘制子弹
						g.setColor(Color.white);
						g.fillRect(etbl.x, etbl.y, 4, 4);
						
					}else{
						et.bls.remove(j);
					}
				}
			}
		}
		//绘制炸弹
		for(int i=0;i<ets.size();i++){//------可以尝试使用迭代器
			Blast blastP1 = ets.get(i).hit(p1);
			if(blastP1 != null){
				blasts.add(blastP1);
			}
			Blast blastEt = p1.hit(ets.get(i));
			if(blastEt != null){
				blasts.add(blastEt);
				ets.remove(i);
			}
		}
		drawBlast(blasts, g);
		//绘制障碍物
		
	}
	//
	public void drawBlast(Vector<Blast> blasts,Graphics g){
		for(int i=0;i<blasts.size();i++){
			Blast blast = blasts.get(i);
			if(blast.times%8 == 0){
				g.drawImage(blast1, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 1){
				g.drawImage(blast2, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 2){
				g.drawImage(blast3, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 3){
				g.drawImage(blast4, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 4){
				g.drawImage(blast5, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 5){
				g.drawImage(blast6, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 6){
				g.drawImage(blast7, blast.x, blast.y,30,40, this);
			}else if(blast.times%8 == 7){
				g.drawImage(blast3, blast.x, blast.y,30,40, this);
			}else{
				g.drawImage(blast8, blast.x, blast.y,30,40, this);
			}
			blast.lifeDown();
			if(blast.times == 0){
				blasts.remove(blast);
				
			}	
		}
	}
	//创建绘制坦克方法--（敌人和玩家坦克）false敌人，true玩家
	public void drawtank(int x,int y,int width,int height,char direction,Graphics g,boolean type){
		switch(direction){
		case 'U':
			if(type){
				g.drawImage(image_p1tankU, x, y, width, height, this);
			}else{
				g.drawImage(enemy3U, x, y, width, height, this);
			}
			break;
		case 'R':
			if(type){
				g.drawImage(image_p1tankR, x, y, height, width, this);
			}else{
				g.drawImage(enemy3R, x, y, height, width, this);
			}
			break;
		case 'D':
			if(type){
				g.drawImage(image_p1tankD, x, y, width, height, this);
			}else{
				g.drawImage(enemy3D, x, y, width, height, this);
			}
			break;
		case 'L':
			if(type){
				g.drawImage(image_p1tankL, x, y, height, width, this);
			}else{
				g.drawImage(enemy3L, x, y, height, width, this);
			}
			break;
		}
		
		
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
			//重绘GamePanel
			this.repaint();
			collideEt();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP||e.getKeyCode() == KeyEvent.VK_W){
			if(p1.direction != 'U') p1.move('U');
			p1.move('U');
		}else if(e.getKeyCode() == KeyEvent.VK_RIGHT||e.getKeyCode() == KeyEvent.VK_D){
			if(p1.direction != 'R') p1.move('R');
			p1.move('R');
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN||e.getKeyCode() == KeyEvent.VK_S){
			if(p1.direction != 'D') p1.move('D');
			p1.move('D');
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_A){
			if(p1.direction != 'L') p1.move('L');
			p1.move('L');
		}else if(e.getKeyCode() == KeyEvent.VK_SPACE||e.getKeyCode() == KeyEvent.VK_J||e.getKeyCode() == KeyEvent.VK_ENTER){
			p1.shot();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
class ScorePanel extends JPanel implements Runnable{
	
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
			etIcon=ImageIO.read(new File("images/score/etIcon.png"));
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
class LifePanel{

}
class WelcomePanel extends JPanel implements Runnable,KeyListener{
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
			battlecity1=ImageIO.read(new File("images/welcome/battlecity1.png"));
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
		g.drawString("© 1980-2018 NAMCO LTD.", 250, 450+time);
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
class StagePanel extends JPanel implements Runnable{
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
				//设置休眠时间
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
class GameoverPanel{
	
}

class AePlayWave extends Thread{
	private String fileName;
	public AePlayWave(String fileName){
		this.fileName = fileName;
	}
	public void run(){
		File voiceFile = new File(fileName);
		AudioInputStream ais = null;
		try {
			
			ais = AudioSystem.getAudioInputStream(voiceFile);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		AudioFormat afm = ais.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, afm);
		
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(afm);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		auline.start();
		
		int r = 0;
		byte[] b = new byte[1024];
		
			try {
				while(r != -1){
					r = ais.read(b, 0, b.length);
					if(r >= 0){
						auline.write(b, 0, r);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
}