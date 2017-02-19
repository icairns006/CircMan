package Objects;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import GameEngine.Game;
import Players.Minion;

public class Sword extends Object {
	ArrayList<Integer> heights = new ArrayList<Integer>();
	ArrayList<Integer> widths = new ArrayList<Integer>();
	ArrayList<Image> images = new ArrayList<Image>();
	private boolean chop =false;
	private int imagenum = 0;
	private float imgnum = 0;
	private float imgdirct = (float).9;
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
        
        heights.add(80);
        widths.add(80);
        heights.add(80);
        widths.add(80);
        heights.add(80);
        widths.add(80);
        heights.add(80);
        widths.add(80);
        heights.add(80);
        widths.add(80);
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
				imgdirct = (float) -1.5;
				for(Minion x: game.minions){
					if(x.getCurrentObj()!=this){
						if(x.getBounds().intersects(this.x,this.y, this.HEIGHT,this.WIDTH))
							x.giveDamage(10);
					}
				}
				
			}
			if(imagenum ==0  && imgdirct<-.1){
				chop = false;
				imgdirct = (float) .9;
			}
			img = images.get(imagenum);
			HEIGHT= heights.get(imagenum);
			WIDTH= widths.get(imagenum);
			yy=yy-(heights.get(imagenum)-heights.get(0));
			
		}
	}
	@Override
	public void setPosition(int xx, int yy, boolean dirr){
		super.setPosition(xx, yy, dirr);
		this.yy=this.yy -55;
		if(dir)this.xx=this.xx - 17 ;
		else this.xx=this.xx +17 ;
	}

}
