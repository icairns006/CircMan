package Background;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import GameEngine.Game;

public class Background {
	Image img;
	final static int Y=0;
	final static int X=0;
	Game game;
	public Background(Game game){
		this.game=game;
		ImageIcon ii = new ImageIcon(getClass().getResource("/Background/hills.jpg"));
        img = ii.getImage();
		
	}
	public void paint(Graphics2D g) {
		g.drawImage(img,
				X, Y,
				game.WIDTH, game.HEIGHT,
				null);
	}
}
