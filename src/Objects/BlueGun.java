package Objects;
import javax.swing.ImageIcon;

import Bullets.Blast;
import GameEngine.Game;

public class BlueGun extends ShootingObject {
	public BlueGun(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii = new ImageIcon(getClass().getResource("/Objects/BlueGun.png"));
		objectName = "BlueGun";
        img = ii.getImage();
        baseImg =img;
        WIDTH=65;
        BASEWIDTH = WIDTH;
        TOSSEDHEIGHT = WIDTH;
        TOSSEDWIDTH= WIDTH;
        getTossedImages();
        
        
	
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		game.bullets.add(new Blast(game, getLoc(), this.y+(this.HEIGHT/4), dir));
	}
	@Override
	public void setPosition(int xx, int yy, boolean dirr){
		super.setPosition(xx, yy, dirr);
		if(dir)this.xx=this.xx -20;
		else this.xx=this.xx +20;
	}
	

}
