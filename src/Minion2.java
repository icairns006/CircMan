import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Minion2 extends Minion {

	public Minion2(Game game,int x, int y, int num) {
		super(game,x, y, num);
		ImageIcon ii = new ImageIcon("Resources/Players/minion_fire.png");
        img = ii.getImage();
        up = KeyEvent.VK_R;
    	down = KeyEvent.VK_F;
    	left = KeyEvent.VK_D;
    	right = KeyEvent.VK_G;
    	pickup = KeyEvent.VK_Q;
    	shootKey = KeyEvent.VK_W;
    	
    	name = "minion2";
	}

}
