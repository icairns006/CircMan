import javax.swing.ImageIcon;

public class Grass extends Ground {

	public Grass(Game game, int xx, int yy) {
		super(game, xx, yy);
		// TODO Auto-generated constructor stub\
		ImageIcon ii = new ImageIcon("Resources/ground.png");
        img = ii.getImage();
	}

}
