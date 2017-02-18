import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
	private static final int DIAMETER = 30;
	int x = 0;
	int y = 0;
	int xa = 1;
	int ya = 1;
	private Game game;

	public Ball(Game game) {
		this.game= game;
	}

	void move() {
		if (x + xa < 0)
			xa = 1;
		if (x + xa > game.getWidth() - DIAMETER)
			xa = -1;
		if (y + ya < 0)
			ya = 1;
		if (y + ya > game.getHeight() - DIAMETER){
			//game.gameOver();
		}
		Minion min = collision();
		if (min != null){
			ya = -1;
			y = min.getTopY() - DIAMETER;
		}
		x = x + xa;
		y = y + ya;
	}

	private Minion collision() {
		for(Minion x: game.minions){
			if(x.getBounds().intersects(getBounds()))return x;
		}
		return null;
	}

	public void paint(Graphics2D g) {
		g.fillOval(x, y, DIAMETER, DIAMETER);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, DIAMETER, DIAMETER);
	}
}