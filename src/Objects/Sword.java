package Objects;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import GameEngine.Game;

public class Sword extends Object {
	ArrayList<Integer> heights = new ArrayList<Integer>();
	ArrayList<Integer> widths = new ArrayList<Integer>();
	ArrayList<Image> images = new ArrayList<Image>();
	private boolean chop =false;
	private int imagenum = 0;
	private float imgnum = 0;
	private int imgdirct = 1;
	public Sword(Game game, int xx, int yy) {
		super(game, xx, yy);
		ImageIcon ii ;
        Image temp;
        for(int i = 0; i<5; i++){
        	ii = new ImageIcon("Resources/Objects/Sword/Sword" + (i+1)+".png");
        	temp = ii.getImage();
        	images.add(temp);
        }
        img = images.get(0);
        
        heights.add(15);
        widths.add(40);
        heights.add(30);
        widths.add(40);
        heights.add(35);
        widths.add(47);
        heights.add(40);
        widths.add(45);
        heights.add(45);
        widths.add(43);
        HEIGHT = heights.get(0);
        WIDTH = widths.get(0);
        
	}

	@Override
	public void shoot() {
		chop = true;

	}
	@Override
	public void move(){
		super.move();
		if(got && chop){
			imgnum = imgnum+(float)(.1*imgdirct);
			imagenum = (int)imgnum;
			if(imagenum==4){
				imgdirct = -1;
			}
			if(imagenum ==0  && imgdirct==-1){
				chop = false;
				imgdirct = 1;
			}
			img = images.get(imagenum);
			HEIGHT= heights.get(imagenum);
			WIDTH= widths.get(imagenum);
			yy=yy-(heights.get(imagenum)-heights.get(0));
			
		}
	}

}
