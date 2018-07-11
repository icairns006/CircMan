package Objects;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import GameEngine.Game;

public class Candy extends Object {
	public Candy(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii = new ImageIcon(getClass().getResource("/Objects/friend.png"));
        img = ii.getImage();
        baseImg =img;
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

}
