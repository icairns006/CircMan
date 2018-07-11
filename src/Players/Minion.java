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
	private enum ActionStatus {PUNCH,KICK,SHOOT,GETOBJECT,DOWNKICK,SPINPUNCH, THROUGH, NONE};
	private ActionStatus actionStatus = ActionStatus.NONE;
	protected Image img;
	protected Image baseimg;
	int y;
	protected int WIDTH = 60;
	protected int BASEWIDTH = 60;
	protected int HEIGHT = 60;
	private int x;
	private float xx;
	private float yy;
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
	private String PlayerPath= "/Players/";
	protected String ColorPath= "Blue";
	private ArrayList<Image> Walking = new ArrayList<Image>();
	private ArrayList<Image> Jumping = new ArrayList<Image>();
	private ArrayList<Image> Punching = new ArrayList<Image>();
	private ArrayList<Image> Kicking = new ArrayList<Image>();
	private ArrayList<Image> DownKicking = new ArrayList<Image>();
	private ArrayList<Image> SpinPunching= new ArrayList<Image>();
	private int numWalkingPhotos = 5;
	private int numJumpingPhotos = 6;
	private int numPunchingPhotos = 7;
	private int numSpinPunchPhotos = 11;
	private int numKickingPhotos = 7;
	private int numDownKickingPhotos = 7;
	private int walkingPhotoItt	=0;
	private float  walkingPhotoIttFloat = 0;
	private int JumpingPhotoItt	=0;
	private float  JumpingPhotoIttFloat = 0;
	private int PunchingPhotoItt	=0;
	private float  PunchingPhotoIttFloat = 0;
	private int DownKickingPhotoItt	=0;
	private float  DownKickingPhotoIttFloat = 0;
	private int KickingPhotoItt	=0;
	private float  KickingPhotoIttFloat = 0;
	private int SpinPunchPhotoItt = 0;
	private float SpinPunchingPhotoIttFloat = 0;
	private int negOffset = 0;
	private boolean onGround = false;
	private boolean jumped = false;
	private boolean punch = false;
	private boolean punchfwd = true;
	private boolean kick = false;
	private boolean kickfwd = true;
	private boolean downkick = false;
	private boolean downkickfwd = true;
	private int addedWIDTH=0;
	private int numJumps = 2;
	private int numJumpsCount = 0;
	private int actionItterator = 0;
	private boolean actionDone = false;
	private boolean actionKeyUp = false;
	
	public Minion(Game game, int x,int y,int num , String colorPath) {
		this.game = game;
		WIDTH =WIDTH*game.scale;
		HEIGHT =HEIGHT*game.scale;
		playerNum=num;
		xx=x;
		this.x = x;
		yy=y;
		this.y = y;
		this.ColorPath = colorPath;
		createImages();
		
	}
	public void createImages(){
		ImageIcon ii ;
        Image temp;
        
        ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/CircManStand1.png"));
        img = ii.getImage();
        baseimg = img;
        for(int i = 0; i<numWalkingPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/Walk/CircManWalk"+(i+1)+".png"));
        	temp = ii.getImage();
        	Walking.add(temp);
        }
        
        for(int i = 0; i<numJumpingPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/Jump/CircManJump"+(i+1)+".png"));
        	temp = ii.getImage();
        	Jumping.add(temp);
        }
        for(int i = 0; i<numPunchingPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/Punch/CircManPunch"+(i+1)+".png"));
        	temp = ii.getImage();
        	Punching.add(temp);
        }
        for(int i = 0; i<numKickingPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/Kick/CircManKick"+(i+1)+".png"));
        	temp = ii.getImage();
        	Kicking.add(temp);
        }
        for(int i = 0; i<numDownKickingPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/DownKick/CircManDownKick"+(i+1)+".png"));
        	temp = ii.getImage();
        	DownKicking.add(temp);
        }
        
        for(int i = 0; i<numSpinPunchPhotos; i++){
        	ii = new ImageIcon(getClass().getResource(PlayerPath+ColorPath+"/SpinPunch/SpinPunch"+(i+1)+".png"));
        	temp = ii.getImage();
        	SpinPunching.add(temp);
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
		dontWalkOnPlayer();
		if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
			xx = xx + xa;
		
		yy = yy + ya;
		
		CheckGround();
		walkAnimation();
		x=(int)xx;
		y=(int)yy;
		if(xa>0)dir=true;
		else if(xa<0)dir=false;
		Action(keys);
		MoveObject();
		GotShot();
		
		
		
	}
	public void dontWalkOnPlayer(){
		for(Minion x: game.minions){
			if(x != this){
				if(x.getBounds().intersects(getBounds())){
					if(x.getBounds().x>this.x && xa>0){
						xa=0;
						dir = true;
					}
					else if(x.getBounds().x<this.x && xa<0){
						xa=0;
						dir = false;
					}
				}
			}
		}
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
				x-negOffset, y,
				WIDTH, HEIGHT,
				null);
		}
		else{
			g.drawImage(img,
					x+WIDTH-addedWIDTH+negOffset, y,
					-WIDTH, HEIGHT,
					null);
			
		}
	}

	public Rectangle getBounds() {
		if(dir)return new Rectangle(x, (int)yy, BASEWIDTH, HEIGHT);
		return new Rectangle(x, (int)yy, BASEWIDTH, HEIGHT);
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
			if(!x.getGot() && x.getBounds().intersects(getBounds()))
				return x;
		}
		return null;
		
	}
	private void MoveObject(){
		if(this.currentObject!= null){
			if(dir)this.currentObject.setPosition(this.x+BASEWIDTH-(this.currentObject.WIDTH/2), this.y+(this.HEIGHT/2), dir);
			else this.currentObject.setPosition(this.x-(this.currentObject.WIDTH/2), this.y+(this.HEIGHT/2), dir);
			if(objTime<31)
				this.objTime++;
		}
		else if(objTime>-11)
			this.objTime--;
	}
	private boolean TossObject(){
		if(this.currentObject!= null && objTime>30){
			if(dir)this.currentObject.setPosition(this.x+WIDTH+5, this.y+(this.HEIGHT/2), dir);
			else this.currentObject.setPosition(this.x-this.currentObject.WIDTH-5, this.y+(this.HEIGHT/2), dir);
			this.currentObject.toss();
			this.currentObject= null;
			this.objTime = 0;
		}
		return true;
	}
	private boolean GetObject(Set<Integer> keys){
		Object hit = HitObject();
		if(hit!=null){
			if(!hit.getTossed() && this.currentObject==null && keys.contains(pickup) && objTime<-10){
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
		Set<Object> temp1 = new HashSet<Object>();
		for(Bullet x: game.bullets){
			if(x.getBounds().intersects(getBoundsSmall())){
				//System.out.println(this.name+" just got shot");
				damage = damage + x.getDamage();
				System.out.println(this.name+" has "+ damage + " damage.");
				temp.add(x);
			}
		}
		for(Bullet x: temp){
			game.bullets.remove(x);
		}
		for(Object x: game.objs){
			if(x.getTossed() && x.getBounds().intersects(getBoundsSmall())){
				//System.out.println(this.name+" just got shot");
				damage = damage + x.getDamage();
				System.out.println(this.name+" has "+ damage + " damage.");
				temp1.add(x);
			}
		}
		for(Object x: temp1){
			game.objs.remove(x);
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
		if(y + ya > game.getHeight()) return true;
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
					numJumpsCount = 0;
				}
				
			}
		}
		
	}
	public void Jump(Set<Integer> keys){
		if(keys.contains(up)){
			if(upTracker == false && numJumpsCount< numJumps){
				ya = ya-jump;
				if(ya<(-jump*1.5)) ya=(float) (-jump*1.5);
				upTracker=true;
				onGround=false;
				jumped = true;
				numJumpsCount++;
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
		switch(actionStatus){
		case PUNCH:
			if(!actionDone)actionDone =this.Punch();
			if(!keys.contains(shootKey))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
		case SPINPUNCH:
			if(!actionDone)actionDone = this.SpinPunch();
			if(!keys.contains(through))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
		case KICK:
			if(!actionDone)actionDone = this.Kick();
			if(!keys.contains(shootKey))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
			
		case GETOBJECT:
			if(!keys.contains(shootKey)){
				actionStatus=ActionStatus.NONE;
			}
			break;
				
		case SHOOT:
			if(!actionDone){
				actionDone =true;
				currentObject.shoot();
			}
			if(!keys.contains(shootKey))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
		case DOWNKICK:
			if(!actionDone)actionDone = this.DownKick();
			if(!keys.contains(shootKey))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
		case THROUGH:
			if(!actionDone)actionDone = this.TossObject();
			if(!keys.contains(through))actionKeyUp=true;
			if(actionDone && actionKeyUp){
				actionStatus=ActionStatus.NONE;
			}
			break;
		case NONE:
			if(!onGround) img = Jumping.get(numJumpingPhotos-1);
			if(keys.contains(down) && keys.contains(shootKey) ){
				if(onGround){
					actionStatus = ActionStatus.KICK;
					actionDone=false;
					actionKeyUp=false;
				}
				else{
					actionStatus = ActionStatus.DOWNKICK;
					ya=(float) (1);
					actionDone=false;
					actionKeyUp=false;
						
				}
			}
			else if(keys.contains(through)){
				if(this.currentObject==null){
				actionStatus = ActionStatus.SPINPUNCH;
				actionDone=false;
				actionKeyUp=false;
				}
				else{
					actionStatus = ActionStatus.THROUGH;
					actionDone=false;
					actionKeyUp=false;
				}
			}
			
			else if(keys.contains(shootKey)){
				if(this.currentObject!=null){		
					actionStatus = ActionStatus.SHOOT;
					actionDone=false;
					actionKeyUp=false;
				}
				else{
					if(GetObject(keys)){
						actionStatus = ActionStatus.GETOBJECT;	
					}
					else{
						punch=true;
						actionStatus = ActionStatus.PUNCH;
						actionDone=false;
						actionKeyUp=false;
					}
					shootcount = 0;
				}
				
			}
			else{
				shootcount = 0;
			}
			break;
			
		default:
			break;
		
		
		}
		
		
		
	}
	public boolean SpinPunch(){
		float aniSpeed = (float).1;
		SpinPunchingPhotoIttFloat = SpinPunchingPhotoIttFloat+aniSpeed;
		SpinPunchPhotoItt = (int) SpinPunchingPhotoIttFloat;
		addedWIDTH = 30;
		WIDTH = BASEWIDTH+addedWIDTH;
		negOffset = 15;
		img = SpinPunching.get(SpinPunchPhotoItt);
		if(SpinPunchPhotoItt== numSpinPunchPhotos-1){
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
			SpinPunchingPhotoIttFloat=0;
			SpinPunchPhotoItt=0;
			addedWIDTH = 0;
			WIDTH = BASEWIDTH;
			negOffset = 0;
			img = baseimg;
			return true;
		}
		return false;
	}
	
	
	public boolean Punch(){
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
			return true;
		}
		return false;
	}
	
	public boolean Kick(){
		float aniSpeed = (float).15;
		if(kickfwd) KickingPhotoIttFloat = KickingPhotoIttFloat+aniSpeed;
		else KickingPhotoIttFloat = KickingPhotoIttFloat-aniSpeed;
		KickingPhotoItt = (int)KickingPhotoIttFloat;
		if(KickingPhotoItt== numKickingPhotos-1)kickfwd=false;
		addedWIDTH = 20;
		WIDTH = BASEWIDTH+ addedWIDTH;
		img = Kicking.get(KickingPhotoItt);
		if(KickingPhotoItt== numKickingPhotos-1){
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
		
		
		if(KickingPhotoItt==0 && !kickfwd){
			kickfwd=true;
			kick = false;
			addedWIDTH = 0;
			WIDTH = BASEWIDTH;
			img = baseimg;
			//actionStatus=ActionStatus.NONE;
			return true;
		}
		return false;
	}
	
	
	public boolean DownKick(){
		float aniSpeed = (float).15;
		if(downkickfwd) DownKickingPhotoIttFloat = DownKickingPhotoIttFloat+aniSpeed;
		else DownKickingPhotoIttFloat = DownKickingPhotoIttFloat-aniSpeed;
		DownKickingPhotoItt = (int)DownKickingPhotoIttFloat;
		if(DownKickingPhotoItt== numDownKickingPhotos-1)downkickfwd=false;
		addedWIDTH = 0;
		WIDTH = BASEWIDTH+ addedWIDTH;
		img = DownKicking.get(DownKickingPhotoItt);
		if(DownKickingPhotoItt== numDownKickingPhotos-1){
			for(Minion x: game.minions){
				if(x!=this){
					Rectangle bounds = x.getBounds();
					Rectangle mybox;
					if(dir) mybox = new Rectangle(this.x+this.BASEWIDTH - 5,y,40,HEIGHT);
					else mybox = new Rectangle(this.x-35,y,40,HEIGHT);
				
					if(bounds.intersects(mybox)){
						x.giveDamage(5);
					};
				}
			}
		}
		
		
		if(DownKickingPhotoItt==0 && !downkickfwd){
			downkickfwd=true;
			downkick = false;
			addedWIDTH = 0;
			WIDTH = BASEWIDTH;
			img = baseimg;
			for(Minion x: game.minions){
				if(x!=this){
					Rectangle bounds = x.getBounds();
					Rectangle mybox;
					if(dir) mybox = new Rectangle(this.x+this.BASEWIDTH/2,y+20,this.BASEWIDTH,HEIGHT);
					else mybox = new Rectangle(this.x-this.BASEWIDTH/2,y+20,this.BASEWIDTH,HEIGHT);
				
					if(bounds.intersects(mybox)){
						x.giveDamage(5);
					};
				}
			}
			//actionStatus=ActionStatus.NONE;
			return true;
		}
		return false;
	}
	
	public void giveDamage(int damage){
		this.damage =damage+this.damage;
	}
	public Object getCurrentObj(){
		return currentObject;
	}
	
}