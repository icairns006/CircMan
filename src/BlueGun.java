import javax.swing.ImageIcon;

public class BlueGun extends ShootingObject {
	public BlueGun(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii = new ImageIcon("Resources/Objects/BlueGun.png");
        img = ii.getImage();
        WIDTH=50;
	
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		game.bullets.add(new Blast(game, getLoc(), this.y+(this.HEIGHT/4), dir));
	}
	

}
