import java.awt.Graphics;
import java.awt.Image;

/**
 * This class represents the player in the game.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Player extends KillableSprite implements Collidable
{
	private static final int PLANE_SPEED = 8;
	private static final Image PLANE = Utilities.loadImage("plane.png");
	private static final Image PLANE_HIT = Utilities.loadImage("plane-hit.png");
	private static final Image PLANE_LEFT = Utilities
		.loadImage("plane-left.png");
	private static final Image PLANE_RIGHT = Utilities
		.loadImage("plane-right.png");
	private static final int X = (Global.WINDOW_WIDTH >> 1) - (PLANE
		.getWidth(null) >> 1);
	private static final int Y = Global.WINDOW_HEIGHT - PLANE.getHeight(null) -
		20;
	private int numLives = 5;
	private int numMissiles = 10;
	private int numBombs = 1;
	private int t = 0;
	private boolean isDodgingLeft = false;
	private boolean isDodgingRight = false;
	
	/**
	 * This constructor creates a new player.
	 */
	public Player()
	{	
		super(X, Y, new Image[] { PLANE, PLANE_HIT, PLANE, PLANE_RIGHT,
			PLANE_LEFT } , 0, 0, 100);
	}
	
	/**
	 * This method subtracts 1 from the number of lives the player has.
	 */
	public void takeLife()
	{
		--numLives;
	}
	
	/**
	 * This method retrieves the number of lives the player has.
	 * 
	 * @return	Returns the number of lives
	 */
	public int getNumLives()
	{
		return numLives;
	}
	
	/**
	 * This method gets the number of missiles that the player has.
	 * 
	 * @return	Returns the number of missiles
	 */
	public int getNumMissiles()
	{
		return numMissiles;
	}
	
	/**
	 * This method gets the number of bombs that the player has.
	 * 
	 * @return	Returns the number of bombs
	 */
	public int getNumBombs()
	{
		return numBombs;
	}
	
	/**
	 * This method attempts to let the player dodge (if allowed).
	 * 
	 * @param isLeft	True if the player is currently dodging left and false
	 * 					if he/she is not
	 */
	public void doDodge(boolean isLeft)
	{
		if (t == 0)
		{
			if (isLeft)
			{
				setRange(3, 3);	
				isDodgingLeft = true;
			}
			else
			{
				setRange(4, 4);
				isDodgingRight = true;
				
			}
			
			t = 20;
		}
	}
	
	/**
	 * This method determines whether or not a player is dodging.
	 * 
	 * @return	Returns true if the player is currently dodging and false if
	 * 			he/she is not
	 */
	public boolean isDodging()
	{
		return (t > 0);
	}
	
	/**
	 * This method resets the player back to life.
	 */
	public void reset()
	{
		setHealth(100);
		setX(X);
		setY(Y);
		setRange(0, 0);
	}
	
	/**
	 * This method handles whenever a player collides with another sprite.
	 */
	public void collision(Sprite object)
	{
		if (object instanceof EnemyWeapon && !isDodging() &&
			!GamePanel.isLevelComplete())
		{
			Weapon damager = (Weapon) object;
			
			if (damager.isAlive())
			{
				offsetHealth(-damager.getDamageAmount());
				
				if (getHealth() <= 0)
				{
					super.remove();
					GamePanel.makeExplosion(getX(), getY());
					GameState.switchState(GameState.DEAD);
				}
				else
				{		
					setRange(1, 2);
				}
				
				damager.remove();
			}
		}
		else if (object instanceof Enemy && !isDodging() &&
			!GamePanel.isLevelComplete())
		{
			Enemy enemy = (Enemy) object;
			
			if (enemy.isAlive() && !enemy.hasCrashed())
			{
				enemy.setCrashed();
				offsetHealth(-enemy.getCrashDamage());
				
				if (getHealth() <= 0)
				{
					super.remove();
					GamePanel.makeExplosion(getX(), getY());
					GameState.switchState(GameState.DEAD);
				}
				else
				{	
					setRange(1, 2);
				}
								
				enemy.offsetHealth(-enemy.getCrashDamage());
				enemy.setRange(1, 2);
				
				if (enemy.getHealth() <= 0)
				{
					enemy.remove();
					GamePanel.makeExplosion(getX(), getY());
				}
			}
		}
	}
	
	/**
	 * This method gives the player an extra bomb.
	 */
	public void giveBomb()
	{
		++numBombs;
	}
	
	/**
	 * This method gives the player an extra missile.
	 */
	public void giveMissile()
	{
		++numMissiles;
	}
	
	/**
	 * This method gives the player an extra life.
	 */
	public void giveLife()
	{
		++numLives;
	}
	
	/**
	 * This method updates the position of the player based on keyboard events.
	 */
	public void updatePosition()
	{
		if (isAlive() && !GamePanel.isLevelComplete())
		{
			if ((KeyboardEvent.getLeftArrow() || isDodgingLeft) &&
				!isDodgingRight && getX() > PLANE_SPEED - 3)
			{
				if (isDodgingLeft)
				{
					offsetX(-PLANE_SPEED);
				}
				else
				{
					offsetX(-PLANE_SPEED);
				}
			}
			
			if ((KeyboardEvent.getRightArrow() || isDodgingRight) &&
				!isDodgingLeft && (getX() + getWidth()) < Global.WINDOW_WIDTH)
			{
				if (isDodgingRight)
				{
					offsetX(PLANE_SPEED);
				}
				else
				{
					offsetX(PLANE_SPEED);
				}
			}
			
			if (KeyboardEvent.getUpArrow() && getY() > 200)
			{
				offsetY(-PLANE_SPEED);
			}
			
			if (KeyboardEvent.getDownArrow() && (getY() + getHeight()) <
				Global.WINDOW_HEIGHT)
			{
				offsetY(PLANE_SPEED);
			}
			
			if (t != 0)
			{
				if (t > 0)
				{
					--t;
					
					if (t == 0)
					{
						setRange(0, 0);
						isDodgingLeft = false;
						isDodgingRight = false;
						t = -1;
					}
				}
				else
				{
					++t;
				}
			}
		}
		else if (GamePanel.isLevelComplete())
		{
			offsetY(-(PLANE_SPEED >> 1));
			 
			if ((getY() + getHeight()) < 0 && !GameState.isLoading() &&
				isAlive())
			{
				GameState.loadState(GameState.LEVEL_COMPLETE);
			}
		}
	}
	
	/**
	 * This method fires the player's laser weapon.
	 */
	public void fireLasers()
	{
		if (isAlive() && !GamePanel.isLevelComplete() && !isDodging())
		{
			int lx = getX() + (getWidth() >> 1) - 5;
			int ly = getY() - 10;
			
			LaserBeam laser;
			
			laser = new LaserBeam(lx, ly, "t,0.5,^,10,*", "t,1.6,^,-1,*");
	        GamePanel.getSprites().add(laser);
	        
	        laser = new LaserBeam(lx, ly, "t,0.5,^,-10,*", "t,1.6,^,-1,*");
	        GamePanel.getSprites().add(laser);
	        
	        laser = new LaserBeam(lx, ly, "t,0.5,^,5,*", "t,1.6,^,-1.2,*");
	        GamePanel.getSprites().add(laser);
	        
	        laser = new LaserBeam(lx, ly, "t,0.5,^,-5,*", "t,1.6,^,-1.2,*");
	        GamePanel.getSprites().add(laser);
	        
	        laser = new LaserBeam(lx, ly, "0", "t,1.6,^,-1.3,*");
	        GamePanel.getSprites().add(laser);
	        
	        Sound laserSound = new Sound("laser.wav");
	        laserSound.playOnce();
		}
	}
	
	/**
	 * This method fires a player's missile.
	 */
	public void fireMissile()
	{
		if (isAlive() && !GamePanel.isLevelComplete() && !isDodging() &&
			numMissiles != 0 && numMissiles-- > 0)
		{
			int lx = getX() + (getWidth() >> 1) - 11;
			int ly = getY() - 5;
			
			Missile missile = new Missile(lx, ly, "0", "t,1.6,^,-1.5,*,2,*");
	        GamePanel.getSprites().add(missile);
		}
	}
	
	/**
	 * This method fires a player's bomb.
	 */
	public void fireBomb()
	{
		if (isAlive() && !GamePanel.isLevelComplete() && !isDodging() &&
			numBombs != 0 && numBombs-- > 0)
		{
			int lx = getX() + (getWidth() >> 1) - 16;
			int ly = getY() - 20;
			
			Bomb bomb = new Bomb(lx, ly, "0", "-7,t,0.7,^,*,2,*");
	        GamePanel.getSprites().add(bomb);
		}
	}
	
	/**
	 * This method draws the player on the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public void draw(Graphics g)
	{
		super.draw(g);
		super.iterate();
	}
}
