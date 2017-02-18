import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

public abstract class Minion {
	protected Image img;
	int y = 0;
	protected int WIDTH = 60;
	protected int HEIGHT = 60;
	private int x = 0;
	private float xx =0;
	private float yy = 0;
	private float xa = 0;
	private float ya = 0;
	private Game game;
	private Object currentObject = null;
	private int objTime = 0;
	protected boolean dir = true;
	protected int up = KeyEvent.VK_I;
	protected int down = KeyEvent.VK_K;
	protected int left = KeyEvent.VK_J;
	protected int right = KeyEvent.VK_L;
	protected int pickup= KeyEvent.VK_A;
	protected int through= KeyEvent.VK_S;
	protected int shootKey= KeyEvent.VK_A;
	protected String name = "";
	private int shootcount=0;
	private int damage=0;
	private int damageLimit = 100;
	private boolean upTracker = false;
	private int playerNum=0;
	private boolean shootRelease = false;
	private int jump = 2;
	private String PlayerPath= "Resources/Players/";
	protected String ColorPath= "";
	private ArrayList<Image> Walking = new ArrayList<Image>();
	
	public Minion(Game game, int x,int y,int num) {
		this.game = game;
		WIDTH =WIDTH*game.scale;
		HEIGHT =HEIGHT*game.scale;
		playerNum=num;
		xx=x;
		yy=y;
		
	}
	public void createImages(){
		ImageIcon ii ;
        Image temp;
        for(int i = 0; i<5; i++){
        	ii = new ImageIcon(PlayerPath+ColorPath + (i)+".png");
        	temp = ii.getImage();
        	Walking.add(temp);
        }
		
		
		
		
	}

	public void move(Set<Integer> keys) {
		xa = 0;
		float dis = (float) .5;
		if(keys.contains(left)) xa =  xa-dis;
		if(keys.contains(right)) xa = xa+dis;
		
		Jump(keys);
		
		ya=ya+game.Gravity;
		
		if(ya>2)ya=2;
		
		if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
			xx = xx + xa;
		if (y + ya > 0 && y + ya < game.getHeight() - HEIGHT)
			yy = yy + ya;
		else 
			ya=(float) 0;
		CheckGround();
		x=(int)xx;
		y=(int)yy;
		if(xa>0)dir=true;
		else if(xa<0)dir=false;
		
		boolean gotobj = GetObject(keys);		
		MoveObject();
		TossObject(keys);
		if(gotobj)shootRelease=true;
		if(shootRelease){
			if(!keys.contains(shootKey)){
				shootRelease=false;
			}
		}else Shoot(keys);
		
		GotShot();
		
		
		
	}

	public void paint(Graphics2D g) {
		if(dir){
		g.drawImage(img,
				x, y,
				WIDTH, HEIGHT,
				null);
		}
		else{
			g.drawImage(img,
					x+WIDTH, y,
					-WIDTH, HEIGHT,
					null);
			
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, (int)yy, WIDTH, HEIGHT);
	}
	public Rectangle getBoundsSmall() {
		int offset = 3;
		return new Rectangle(x+(offset*game.scale), y+(offset*game.scale), WIDTH-(offset*game.scale), HEIGHT-(offset*game.scale));
	}

	public int getTopY() {
		return y;
	}
	
	public void ObjectAcquired(Object obj){
		this.currentObject = obj;
	}
	private Object HitObject(){
		for(Object x: game.objs){
			if(x.getBounds().intersects(getBounds()))
				return x;
		}
		return null;
		
	}
	private void MoveObject(){
		if(this.currentObject!= null){
			if(dir)this.currentObject.setPosition(this.x+WIDTH-(this.currentObject.WIDTH/2), this.y+5+(this.HEIGHT/2), dir);
			else this.currentObject.setPosition(this.x-(this.currentObject.WIDTH/2), this.y+5+(this.HEIGHT/2), dir);
			if(objTime<31)
				this.objTime++;
		}
		else if(objTime>-11)
			this.objTime--;
	}
	private void TossObject(Set<Integer> keys){
		if(this.currentObject!= null && keys.contains(through)&& objTime>30){
			if(dir)this.currentObject.setPosition(this.x+WIDTH+5, this.y+(this.HEIGHT/2), dir);
			else this.currentObject.setPosition(this.x-this.currentObject.WIDTH-5, this.y+(this.HEIGHT/2), dir);
			this.currentObject.toss();
			this.currentObject= null;
			this.objTime = 0;
		}
	}
	private boolean GetObject(Set<Integer> keys){
		Object hit = HitObject();
		if(hit!=null){
			if(hit.getTossed()){
				damage = damage + hit.getDamage();
				game.objs.remove(hit);
			}
			else if(this.currentObject==null && keys.contains(pickup) && objTime<-10){
				this.currentObject = hit;
				this.currentObject.got();
				this.objTime = 0;
				return true;
			}
			
			
		}
		return false;
	}
	private void GotShot(){
		Set<Bullet> temp = new HashSet<Bullet>();
		for(Bullet x: game.bullets){
			if(x.getBounds().intersects(getBoundsSmall())){
				System.out.println(this.name+" just got shot");
				damage = damage + x.getDamage();
				System.out.println(this.name+" has "+ damage + " damage.");
				temp.add(x);
			}
		}
		for(Bullet x: temp){
			game.bullets.remove(x);
		}
	}
	private void Shoot(Set<Integer> keys){
		int time=40;
		if(this.currentObject!=null){
			if(keys.contains(shootKey)&& shootcount >time) {
				currentObject.shoot();
				this.shootcount = 0;
			}
			else if(shootcount< time+1)shootcount++;
		}
	}
	public boolean GotKilled(){
		if(damage>damageLimit)return true;
		return false;
	}
	public int getDamage(){
		int x = (damage*100)/damageLimit;
		return x;
	}
	public int getPlayerNum(){
		return playerNum;
	}
	public void CheckGround(){
		for(Ground x: game.grounds){
			Rectangle bounds = x.getBounds();
			if(bounds.intersects(getBounds())){
				if((this.y+HEIGHT)<(bounds.y+5) && ya > 0){
					ya=0;
					yy = bounds.y-HEIGHT;
				}
			}
		}
	}
	public void Jump(Set<Integer> keys){
		if(keys.contains(up)){
			if(upTracker == false){
				ya = ya-jump;
				upTracker=true;
			}
		}
		else{
			upTracker = false;
		}
	}
}