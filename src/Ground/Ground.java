package Ground;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import GameEngine.Game;

public abstract class Ground {
	private int x = 0;
	private int y = 0;
	Game game;
	Image img;
	protected int WIDTH = 50;
	protected int HEIGHT = 25;
	
	
	public Ground(Game game, int xx, int yy){
		x=xx;
		y=yy;
		this.game=game;
	}
	public void paint(Graphics2D g) {
		g.drawImage(img,
				x, y,
				WIDTH, HEIGHT,
				null);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

}
