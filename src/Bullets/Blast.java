package Bullets;
import javax.swing.ImageIcon;

import GameEngine.Game;

public class Blast extends Bullet {

	public Blast(Game game, int xx, int yy, boolean dirr) {
		super(game, xx, yy, dirr);
		// TODO Auto-generated constructor stub
		ImageIcon ii = new ImageIcon("Resources/Blast.png");
        img = ii.getImage();
        WIDTH = 15;
        HEIGHT = 15;
        
	}

}
