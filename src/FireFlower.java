import javax.swing.ImageIcon;

public class FireFlower extends ShootingObject {

	public FireFlower(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii = new ImageIcon("Resources/Objects/fireflower.png");
        img = ii.getImage();
	
	}
	@Override
	public void shoot(){
		game.bullets.add(new Fireball(game, getLoc(), this.y+(this.HEIGHT/4), dir));
	}
}
