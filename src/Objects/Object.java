package Objects;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Set;

import GameEngine.Game;
import Ground.Ground;


public abstract class Object{
	Image img;
	float ya = 0;
	float yy = 0;
	int y;
	int x;
	double xa;
	double xx;
	boolean dir;
    Game game;
    public int WIDTH = 25;
	protected int HEIGHT = 25;
	protected boolean got = false;
	private boolean tossed = false;
	private int damage =10;

	public Object(Game game, int xx, int yy) {
		this.game = game;
		this.xx = xx;
		this.yy = yy;
		WIDTH = WIDTH*game.scale;
		HEIGHT = HEIGHT*game.scale;
	}
	public void setPosition(int xx, int yy, boolean dirr){
		this.xx = xx;
		this.yy = yy;
		this.dir = dirr;
	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)xx, (int)yy, WIDTH, HEIGHT);
	}
	public void paint(Graphics2D g) {
		y=(int)yy;
		x=(int)xx;
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
	public abstract void shoot();
	public void got(){
		got=true;
	}
	public void toss(){
		got=false;
		ya=-1;
		if(dir){
			xa = 2;
			xx=xx+WIDTH/2+5;
		}
		else{
			xa =-2;
			xx = xx-WIDTH/2-5;
		}
		tossed = true;
	}
	public void move(){
		if(!got){
			ya=ya+game.Gravity;
			if(ya>2)ya=2;
			yy=yy+ya;
			y=(int) yy;
			CheckGround();
			xa=(xa/1.005);
			if(xa<.01&& xa>-.01) xa=0;
			xx = xx+xa;
		}
		
	}
	public void CheckGround(){
		for(Ground x: game.grounds){
			Rectangle bounds = x.getBounds();
			if(bounds.intersects(getBounds())){
				if((this.y+HEIGHT)<(bounds.y+5) && ya > 0){
					ya=-ya/2;
					if(ya<.1)ya=0;
					yy = bounds.y-HEIGHT;
					xa=0;
					tossed = false;
				}
			}
		}
	}
	public boolean getTossed(){
		return tossed;
	}
	public int getDamage(){
		return damage;
	}
	public boolean getGot(){
		return got;
	}
}
