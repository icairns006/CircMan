package Bullets;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import GameEngine.Game;
import Ground.Ground;

public class Fireball extends Bullet {
	float ya = (float)-.5;
	float yy = 0;
	public Fireball(Game game, int xx, int yy, boolean dirr) {
		super(game, xx, yy, dirr);
		ImageIcon ii = new ImageIcon(getClass().getResource("/fireball1.png"));
        img = ii.getImage();
        damage = 10;
        this.yy=yy;
	}

	@Override
	public void Move(){
		super.Move();
		ya=ya+game.Gravity;
		if(ya>2)ya=2;
		yy=yy+ya;
		y=(int) yy;
		CheckGround();
	}
	
	public void CheckGround(){
		for(Ground x: game.grounds){
			Rectangle bounds = x.getBounds();
			if(bounds.intersects(getBounds())){
				if((this.y+HEIGHT)<(bounds.y+5) && ya > 0){
					ya=-(ya/2);
					if(ya>-.4)ya=(float)-.6;
					yy = bounds.y-HEIGHT;
				}
			}
		}
	}
}
