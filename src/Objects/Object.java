package Objects;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.ImageIcon;

import GameEngine.Game;
import Ground.Ground;


public abstract class Object{
	Image img;
	Image baseImg;
	float ya = 0;
	float yy = 0;
	int y;
	int x;
	double xa;
	double xx;
	boolean dir;
    Game game;
    public int WIDTH = 25;
	protected int HEIGHT = 25;
	protected int BASEHEIGHT = 25;
	protected int BASEWIDTH = 25;
	protected int TOSSEDHEIGHT = 25;
	protected int TOSSEDWIDTH = 25;
	
	public boolean got = false;
	private boolean tossed = false;
	private String objectPath = "/Objects/";
	protected String objectName = "";
	private int damage =10;
	private int numTossedImage = 11;
	private ArrayList<Image> TossedImages= new ArrayList<Image>();
	private int tossedItt = 0;
	private float tossedIttFloat = 0;

	public Object(Game game, int xx, int yy) {
		this.game = game;
		this.xx = xx;
		this.yy = yy;
		WIDTH = WIDTH*game.scale;
		HEIGHT = HEIGHT*game.scale;
	}
	public void getTossedImages(){
		ImageIcon ii;
		Image temp;
		for(int i = 0; i<numTossedImage; i++){
        	ii = new ImageIcon(getClass().getResource(objectPath+objectName+"/Tossed/Tossed"+(i+1)+".png"));
        	temp = ii.getImage();
        	TossedImages.add(temp);
        }
	}
	public void setPosition(int xx, int yy, boolean dirr){
		this.xx = xx;
		this.yy = yy;
		this.dir = dirr;
	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)xx, (int)yy, WIDTH, HEIGHT);
	}
	public void paint(Graphics2D g) {
		y=(int)yy;
		x=(int)xx;
		if(dir){
			g.drawImage(img,
					x, y,
					WIDTH, HEIGHT,
					null);
		}else{
			g.drawImage(img,
					x+this.WIDTH, y,
					-WIDTH, HEIGHT,
					null);
		}
			
	}
	public abstract void shoot();
	public void got(){
		got=true;
	}
	public void toss(){
		got=false;
		ya=-1;
		if(dir){
			xa = 2;
			xx=xx+WIDTH/2+5;
		}
		else{
			xa =-2;
			xx = xx-WIDTH/2-5;
		}
		tossed = true;
	}
	public void move(){
		if(!got){
			ya=ya+game.Gravity;
			if(ya>2)ya=2;
			yy=yy+ya;
			y=(int) yy;
			CheckGround();
			if(!TossedImages.isEmpty()){
				this.TossedAnimation();
			}
			xa=(xa/1.005);
			if(xa<.01&& xa>-.01) xa=0;
			xx = xx+xa;
		}
		
	}
	private void TossedAnimation(){
		float aniSpeed = (float).1;
		if(tossed){
			tossedIttFloat+=aniSpeed;
			tossedItt = (int)tossedIttFloat;
			WIDTH = TOSSEDWIDTH;
			HEIGHT= TOSSEDHEIGHT;
			if(tossedItt == numTossedImage){
				tossedIttFloat = 0;
				tossedItt = 0;
			}
			img = TossedImages.get(tossedItt);
		}
	}
	public void CheckGround(){
		for(Ground x: game.grounds){
			Rectangle bounds = x.getBounds();
			if(bounds.intersects(getBounds())){
				if((this.y+HEIGHT)<(bounds.y+5) && ya > 0){
					
					if(ya<.3){ya=0;
						yy = bounds.y-HEIGHT;
						tossed = false;
						xa=0;
						img = baseImg;
						WIDTH = BASEWIDTH;
						HEIGHT = BASEHEIGHT;
					}else{
						ya=-ya/3;
						xa = xa/2;
					}
				}
			}
		}
	}
	public boolean getTossed(){
		return tossed;
	}
	public int getDamage(){
		return damage;
	}
	public boolean getGot(){
		return got;
	}
	
	public boolean offScreen(){
		if(this.x<(0-WIDTH)|| this.x> game.WIDTH || this.y> game.HEIGHT){
			return true;
		}
		return false;
	}
}
