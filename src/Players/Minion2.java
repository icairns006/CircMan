package Players;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import GameEngine.Game;

public class Minion2 extends Minion {

	public Minion2(Game game,int x, int y, int num, String colorPath) {
		super(game,x, y, num, colorPath);
    	name = "CircMan2";
        up = KeyEvent.VK_R;
    	down = KeyEvent.VK_F;
    	left = KeyEvent.VK_D;
    	right = KeyEvent.VK_G;
    	pickup = KeyEvent.VK_Q;
    	shootKey = KeyEvent.VK_Q;
    	through= KeyEvent.VK_W;
	}

}
