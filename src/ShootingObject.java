import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public abstract class ShootingObject extends Object {
	public ShootingObject(Game game, int xx, int yy) {
		super(game, xx, yy);
	}
	@Override
	public void shoot(){
		int x;
		if(dir){
			x=this.x+this.WIDTH-5;
		}else
			x=this.x-5;
		
		game.bullets.add(new Fireball(game, x, this.y+(this.HEIGHT/4), dir));
	}
	
	public int getLoc(){
		int x;
		if(dir){
			x=this.x+this.WIDTH-5;
		}else
			x=this.x-5;
		return x;
	}
	
	@Override
	public void setPosition(int xx, int yy, boolean dirr){
		super.setPosition(xx, yy, dirr);
		
	}

}
