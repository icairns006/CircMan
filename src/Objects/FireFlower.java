package Objects;
import java.awt.Image;

import javax.swing.ImageIcon;

import Bullets.Fireball;
import GameEngine.Game;

public class FireFlower extends ShootingObject {
	Image imgGot;
	Image imgNotGot;

	public FireFlower(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii = new ImageIcon("Resources/Objects/fireflower.png");
        img = ii.getImage();
        imgNotGot = img;
        ii = new ImageIcon("Resources/Objects/fireflowerAct.png");
        imgGot= ii.getImage();
        baseImg =img;
	
	}
	@Override
	public void shoot(){
		game.bullets.add(new Fireball(game, getLoc(), this.y+(this.HEIGHT/4), dir));
	}
	@Override
	public void move(){
		super.move();
		if(got){
			img = imgGot;
			HEIGHT = 35;
			WIDTH = 35;
		}
		else{
			img = imgNotGot;
			HEIGHT = 25;
			WIDTH = 25;
		}
	}
	@Override
	public void setPosition(int xx, int yy, boolean dirr){
		super.setPosition(xx, yy, dirr);
		this.yy=this.yy -25;
		if(dir)this.xx=this.xx +9;
		else this.xx=this.xx -9;
	}
	
}
