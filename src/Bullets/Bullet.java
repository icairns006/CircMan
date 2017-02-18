package Bullets;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import GameEngine.Game;

public abstract class Bullet {
	Image img;
	private float xx;
	public int x;
	int y;
	protected int WIDTH = 10;
	protected int HEIGHT = 10;
	boolean dir;
	Game game;
	protected int damage = 5;
	public Bullet(Game game, int xx, int yy , boolean dirr){
		this.game = game;
		this.xx=xx;
		y = yy;
		dir=dirr;
		WIDTH = WIDTH*game.scale;
		HEIGHT=HEIGHT*game.scale;
	}
	
	public void Move(){
		float dis = (float) 1;
		if(dir) xx=xx+dis;
		else xx=xx-dis;
		x=(int)xx;
		
		
	}
	public void paint(Graphics2D g) {
		if(dir){
			g.drawImage(img,
					x, y,
					WIDTH, HEIGHT,
					null);
		}else{
			g.drawImage(img,
					x+this.WIDTH, y,
					-WIDTH, HEIGHT,
					null);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	public int getDamage(){
		return damage;
	}
}
