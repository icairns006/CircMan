package Ground;
import javax.swing.ImageIcon;

import GameEngine.Game;

public class Grass extends Ground {

	public Grass(Game game, int xx, int yy) {
		super(game, xx, yy);
		// TODO Auto-generated constructor stub\
		ImageIcon ii = new ImageIcon(getClass().getResource("/ground.png"));
        img = ii.getImage();
	}

}
