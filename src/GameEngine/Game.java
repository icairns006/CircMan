package GameEngine;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Background.Background;
import Bullets.Bullet;
import Ground.Grass;
import Ground.Ground;
import Objects.BlueGun;
import Objects.Candy;
import Objects.FireFlower;
import Objects.Sword;
import Players.Minion;
import Players.Minion1;
import Players.Minion2;
import Objects.Object;

@SuppressWarnings("serial")
public class Game extends JPanel {
	Set<Integer> keysActive = new HashSet<Integer>();
	//Ball ball = new Ball(this);
	public Set<Minion> minions = new HashSet<Minion>();
	public Set<Object> objs = new HashSet<Object>();
	public Set<Bullet> bullets = new HashSet<Bullet>();
	public Set<Ground> grounds= new HashSet<Ground>();
	Background background = new Background(this);
	public static int scale = 1;
	public static int HEIGHT;
	public static int WIDTH;
	public final static float Gravity = (float).015; 
	final static int HealthWidth= 200;
	private int minionCount=0;
	

	public Game(int width,int height) {
		
		WIDTH=width;
		HEIGHT=height;
		objs.add(new Candy(this, 100, 200));
		objs.add(new Candy(this, 300, 400));
		objs.add(new FireFlower(this, 600, 300));
		objs.add(new FireFlower(this, 800, 100));
		objs.add(new BlueGun(this, 500, 100));
		objs.add(new Sword(this, 400, 100));
		//minions.add(new Minion1(this,1));
		//minions.add(new Minion2(this,2));
		minions.add(new Minion1(this, WIDTH/3, 300,1, "Blue"));
		minions.add(new Minion2(this, 2*WIDTH/3, 300,2, "Green"));
		//minions.add(new Minion2(this,2*WIDTH/3,300, 2));
		for(int i=0; i< 20;i++){
			grounds.add(new Grass(this,(i*50),HEIGHT-150));
			grounds.add(new Grass(this,200+(i*50),HEIGHT-450));
		}
		minionCount = minions.size();
		
		/*addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if(keysActive.contains(e.getKeyCode())){
					keysActive.remove(e.getKeyCode());
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				keysActive.add(e.getKeyCode());
			}
		});
		//setFocusable(true);*/
	}
	
	protected void move(Set<Integer> keysActive) {
		//ball.move();
		this.keysActive=keysActive;
		Set<Minion> tempMin = new HashSet<Minion>();
		for(Minion x: minions){
			x.move(keysActive);
			if(x.GotKilled()){
				tempMin.add(x);
				System.out.println(x.name + " Just Got Killed");
			}
		}
		Set<Object> tempObj = new HashSet<Object>();
		for(Object x: objs){
			x.move();
			if(x.offScreen())tempObj.add(x);
		}
		for(Object x: tempObj){
			objs.remove(x);
		}
		
		for(Minion x: tempMin){
			Object minObj = x.getCurrentObj();
			if(minObj!=null) minObj.got = false;
			minions.remove(x);
		}
		Set<Bullet> temp = new HashSet<Bullet>();
		for(Bullet x: bullets){
			if(x.x> getWidth() || x.x< 0){
					temp.add(x);
			}
			x.Move();
			
		}
		for(Bullet x: temp){
			bullets.remove(x);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		background.paint(g2d);
		
		int offset = 3;
		for(Minion min : minions){
			g.setColor(Color.BLACK);
			g.fillRect(((WIDTH*min.getPlayerNum())/(minionCount+1))-(HealthWidth/2)-offset, 10-offset, HealthWidth+(2*offset), 20+(2*offset));
			g.setColor(Color.RED);
			g.fillRect(((WIDTH*min.getPlayerNum())/(minionCount+1))-(HealthWidth/2), 10, HealthWidth, 20);
			g.setColor(Color.GREEN);
			g.fillRect(((WIDTH*min.getPlayerNum())/(minionCount+1))-(HealthWidth/2), 10, (HealthWidth-((min.getDamage()*HealthWidth)/100)), 20);
			g.setColor(Color.BLACK);
			g.drawString( min.name , ((WIDTH*min.getPlayerNum())/(minionCount+1))-(HealthWidth/2) , 25 ); 
		}
		//ball.paint(g2d);
		for(Minion x: minions){
			x.paint(g2d);
		}
		for(Object x : objs){
			x.paint(g2d);
		}
		for(Bullet x: bullets){
			x.paint(g2d);
		}
		for(Ground x: grounds){
			x.paint(g2d);
		}
	}
	
	public void gameOver() {
		JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
	

	
}