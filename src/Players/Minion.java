package Players;
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

import Bullets.Bullet;
import GameEngine.Game;
import Ground.Ground;
import Objects.Object;


public abstract class Minion {
	protected Image img;
	protected Image baseimg;
	int y = 0;
	protected int WIDTH = 60;
	protected int BASEWIDTH = 60;
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
	public String name = "";
	private int shootcount=0;
	private int damage=0;
	private int damageLimit = 100;
	private boolean upTracker = false;
	private int playerNum=0;
	private boolean actionRelease = false;
	private int jump = 2;
	private String PlayerPath= "Resources/Players/";
	protected String ColorPath= "";
	private ArrayList<Image> Walking = new ArrayList<Image>();
	private ArrayList<Image> Jumping = new ArrayList<Image>();
	private ArrayList<Image> Punching = new ArrayList<Image>();
	private int numWalkingPhotos = 5;
	private int numJumpingPhotos = 6;
	private int numPunchingPhotos = 7;
	private int walkingPhotoItt	=0;
	private float  walkingPhotoIttFloat = 0;
	private int JumpingPhotoItt	=0;
	private float  JumpingPhotoIttFloat = 0;
	private int PunchingPhotoItt	=0;
	private float  PunchingPhotoIttFloat = 0;
	private boolean onGround = false;
	private boolean jumped = false;
	private boolean punch = false;
	private boolean punchfwd = true;
	private int addedWIDTH=0;
	
	public Minion(Game game, int x,int y,int num) {
		this.game = game;
		WIDTH =WIDTH*game.scale;
		HEIGHT =HEIGHT*game.scale;
		playerNum=num;
		xx=x;
		yy=y;
		createImages();
		
	}
	public void createImages(){
		ImageIcon ii ;
        Image temp;
        for(int i = 0; i<numWalkingPhotos; i++){
        	ii = new ImageIcon(PlayerPath+"Blue"+"/Walk/CircManWalk"+(i+1)+".png");
        	temp = ii.getImage();
        	Walking.add(temp);
        }
        
        for(int i = 0; i<numJumpingPhotos; i++){
        	ii = new ImageIcon(PlayerPath+"Blue"+"/Jump/CircManJump"+(i+1)+".png");
        	temp = ii.getImage();
        	Jumping.add(temp);
        }
        for(int i = 0; i<numPunchingPhotos; i++){
        	ii = new ImageIcon(PlayerPath+"Blue"+"/Punch/CircManPunch"+(i+1)+".png");
        	temp = ii.getImage();
        	Punching.add(temp);
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
		walkAnimation();
		//System.out.println(onGround);
		
		x=(int)xx;
		y=(int)yy;
		if(xa>0)dir=true;
		else if(xa<0)dir=false;
		boolean gotobj =false;
		if(!punch)gotobj = GetObject(keys);		
		MoveObject();
		TossObject(keys);
		if(gotobj)actionRelease=true;
		if(actionRelease){
			if(!keys.contains(shootKey)){
				actionRelease=false;
			}
		}else Action(keys);
		
		GotShot();
		
		
		
	}
	public void walkAnimation(){
		float aniSpeed = (float).1;
		if(onGround){
			if(xa!=0){
				walkingPhotoIttFloat = walkingPhotoIttFloat+aniSpeed;
				if(walkingPhotoIttFloat> numWalkingPhotos-1)walkingPhotoIttFloat = 0;
				walkingPhotoItt = (int)walkingPhotoIttFloat;
				img = Walking.get(walkingPhotoItt);
			}else img = baseimg;
		}
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
					x+WIDTH-addedWIDTH, y,
					-WIDTH, HEIGHT,
					null);
			
		}
	}

	public Rectangle getBounds() {
		if(dir)return new Rectangle(x, (int)yy, WIDTH, HEIGHT);
		return new Rectangle(x-addedWIDTH, (int)yy, WIDTH, HEIGHT);
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
	private void Shoot(){
		int time=40;
		if(shootcount >time) {
			this.shootcount = 0;
		}
		else if(shootcount< time+1){
			if(shootcount == 0)currentObject.shoot();
			shootcount++;
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
					onGround= true;
					jumped = false;
				}
				
			}
		}
		
	}
	public void Jump(Set<Integer> keys){
		if(keys.contains(up)){
			if(upTracker == false){
				ya = ya-jump;
				upTracker=true;
				onGround=false;
				jumped = true;
			}
		}
		else{
			upTracker = false;
		}
		JumpAnimation();
	}
	public void JumpAnimation(){
		float aniSpeed = (float).3;
		if(jumped){
				JumpingPhotoIttFloat = JumpingPhotoIttFloat+aniSpeed;
				if(JumpingPhotoIttFloat> numJumpingPhotos-1){
					JumpingPhotoIttFloat = 0;
					jumped = false;
				}
				else{
					JumpingPhotoItt = (int)JumpingPhotoIttFloat;
					img = Jumping.get(JumpingPhotoItt);
				}
		}
		
	}
	public void Action(Set<Integer> keys){
		int time=40;
		if(this.currentObject!=null){
			if(keys.contains(shootKey)){
				this.Shoot();
			}
			else shootcount = 0;
		}
		else{
			if(keys.contains(shootKey)){
				punch=true;
			}
			if(punch){
				this.Punch();
			}
		}
		
		
	}
	public void Punch(){
		float aniSpeed = (float).15;
		if(punchfwd) PunchingPhotoIttFloat = PunchingPhotoIttFloat+aniSpeed;
		else PunchingPhotoIttFloat = PunchingPhotoIttFloat-aniSpeed;
		PunchingPhotoItt = (int)PunchingPhotoIttFloat;
		if(PunchingPhotoItt== numPunchingPhotos-1)punchfwd=false;
		if(PunchingPhotoItt>4){
			addedWIDTH = (PunchingPhotoItt-4)*12;
			
		}else addedWIDTH = 0;
		WIDTH = BASEWIDTH+ addedWIDTH;
		img = Punching.get(PunchingPhotoItt);
		if(PunchingPhotoItt== numPunchingPhotos-1){
			for(Minion x: game.minions){
				if(x!=this){
					Rectangle bounds = x.getBounds();
					Rectangle mybox;
					if(dir) mybox = new Rectangle(this.x+this.BASEWIDTH - 5,y,40,HEIGHT);
					else mybox = new Rectangle(this.x-35,y,40,HEIGHT);
				
					if(bounds.intersects(mybox)){
						x.giveDamage(10);
					};
				}
			}
		}
		
		
		if(PunchingPhotoItt==0 && !punchfwd){
			punchfwd=true;
			punch = false;
		}
	}
	public void giveDamage(int damage){
		this.damage =damage+this.damage;
	}
}