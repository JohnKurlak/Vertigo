import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * This class represents an enemy to the player.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Enemy extends KillableSprite implements Collidable
{
	private String type;
	private Path path;
	private int t = 0;
	private int ti = 0;
	private int startX;
	private int startY;
	private int startYBlock;
	private int fireInterval;
	private int crashDamage = 0;
	private boolean hasCrashed = false;
	private Missile hitter;
	private Missile previousHitter;
	
	/**
	 * This constructor creates a new enemy.
	 * 
	 * @param x				The starting x position of the enemy
	 * @param yBlock		The starting y block of the enemy
	 * @param yOffset		The starting y offset into the block for the enemy
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param xPath			The parameterized x function for the enemy's path
	 * @param yPath			The parameterized y function for the enemy's path
	 * @param startHealth	The starting health of the enemy
	 * @param fireRate		The number of frames that must pass before the enemy
	 * 						can fire again
	 * @param crashAmount	The amount of damage that is inflicted if the enemy
	 * 						collides with the player
	 * @param enemyType		The hex code representing the type of the enemy
	 */
	public Enemy(int x, int yBlock, int yOffset, Image[] spriteImages, int
		firstFrame, int lastFrame, String xPath, String yPath, int startHealth,
		int fireRate, int crashAmount, String enemyType)
	{
		super(x - (spriteImages[0].getWidth(null) >> 1), -yOffset -
			spriteImages[0].getHeight(null), spriteImages, firstFrame,
			lastFrame, startHealth);
		type = enemyType;
		path = new Path(xPath, yPath);
		startX = x - (spriteImages[0].getWidth(null) >> 1);
		startY = yOffset - (Global.BLOCK_SIZE << 1) - 5;
		startYBlock = yBlock;
		fireInterval = fireRate;
		crashDamage = crashAmount;
	}
	
	/**
	 * Sets the enemy's crash status to true.
	 */
	public void setCrashed()
	{
		hasCrashed = true;
	}
	
	/**
	 * Checks to see if the enemy has collided with the player.
	 * 
	 * @return	Returns true if the enemy has collided with the player and false
	 * 			if the enemy has not
	 */
	public boolean hasCrashed()
	{
		return hasCrashed;
	}
	
	/**
	 * Gets the amount of damage that is inflicted if the enemy has collided
	 * with the player.
	 * 
	 * @return	Returns the amount of damage that is inflicted if the player
	 * 			collides with the enemy
	 */
	public int getCrashDamage()
	{
		return crashDamage;
	}
	
	/**
	 * Gets the y block that the enemy is at.
	 * 
	 * @return	Returns the y block that the enemy is at.
	 */
	public int getYBlock()
	{
		return startYBlock;
	}
	
	/**
	 * This method handles what happens when the enemy collides with something
	 * else.
	 */
	public void collision(Sprite object)
	{
		if ((object instanceof PlayerWeapon) && (getY() + getHeight()) > 10)
		{
			Weapon damager = (Weapon) object;
			
			if (damager.isAlive())
			{
				boolean allowDamage = true;
				
				if (object instanceof Missile)
				{
					if ((Missile) damager != hitter && (Missile) damager !=
						previousHitter)
					{
						previousHitter = hitter;
						hitter = (Missile) damager;
					}
					else
					{
						allowDamage = false;
					}
				}
				
				if (allowDamage)
				{
					offsetHealth(-damager.getDamageAmount());
					
					if (getHealth() <= 0)
					{
						super.remove();
						GamePanel.offsetNumLevelPoints(damager
							.getDamageAmount());
						GamePanel.makeExplosion(getX(), getY());
					}
					else
					{
						setRange(1, 2);
					}
					
					if (!(object instanceof Missile))
					{
						damager.remove();
					}
				}
			}
		}
	}
	
	/**
	 * Updates the position of the enemy (and makes it fire).
	 */
	public void updatePosition()
	{	
		Point nextPosition = path.getLocation(t++);
		setX(nextPosition.x + startX);
		setY(nextPosition.y + startY);
		
		fire();
	}
	
	/**
	 * Handles the enemy's weapon-firing capabilities.
	 */
	private void fire()
	{
		// If the enemy should fire during this frame
		if (fireInterval != 0 && t % fireInterval == 0 && isAlive())
		{
			++ti;
			
			int x = getX() + (getWidth() >> 1) - 10;
			int y = getY() + getHeight();
			
			if (type.equals("00"))
			{
				PlasmaOrb orb = new PlasmaOrb(x, y, "0", "t,1.3,^,2,*", true);
				GamePanel.getSprites().add(orb);
			}
			else if (type.equals("04"))
			{
				PlasmaOrb orb = new PlasmaOrb(x, y, "0", "t,1.3,^,2,*", true);
				GamePanel.getSprites().add(orb);
				
				orb = new PlasmaOrb(x, y, "0", "t,1.3,^,4,*", true);
				GamePanel.getSprites().add(orb);
			}
			else if (type.equals("07"))
			{
				int lx = getX() + (getWidth() >> 1) - 5;
				int ly = getY() + (getHeight() >> 1) - 5;
				
				FireOrb orb;
				
				orb = new FireOrb(lx, ly, "t", "20,t,*", false);
				GamePanel.getSprites().add(orb);
				
				orb = new FireOrb(lx, ly, "-t", "20,t,*", false);
				GamePanel.getSprites().add(orb);
				
				orb = new FireOrb(lx, ly, "0", "20,t,*", false);
				GamePanel.getSprites().add(orb);	
			}
			else if (type.equals("01"))
			{
				// Part 1 of weapon firing
				if (ti < 50)
				{
					int lx = getX() + (getWidth() >> 1) - 7;
					int ly = getY() + getHeight() - 10;
					
					PinkLaser laser;
					
					laser = new PinkLaser(lx, ly, "t,0.5,^,2,*", "t,1.6,^",
						true);
			        GamePanel.getSprites().add(laser);
			        
			        laser = new PinkLaser(lx, ly, "t,0.5,^,-2,*", "t,1.6,^",
			        	true);
			        GamePanel.getSprites().add(laser);
			        
			        laser = new PinkLaser(lx, ly, "t,0.5,^,1,*", "t,1.6,^",
			        	true);
			        GamePanel.getSprites().add(laser);
			        
			        laser = new PinkLaser(lx, ly, "t,0.5,^,-1,*", "t,1.6,^",
			        	true);
			        GamePanel.getSprites().add(laser);
			        
			        laser = new PinkLaser(lx, ly, "0", "t,1.6,^,1.3,*", true);
			        GamePanel.getSprites().add(laser);

				}
				// Part 2 of weapon firing
				else if (ti < 100)
				{
					PlasmaOrb orb = new PlasmaOrb(x, y, "0", "t,1.3,^,8,*",
						true);
					GamePanel.getSprites().add(orb);
				}
				// Part 3 of weapon firing
				else if (ti < 150)
				{
					PlasmaOrb orb = new PlasmaOrb(x, y, "0", "t,1.3,^,30,*",
						true);
					GamePanel.getSprites().add(orb);
				}
				else
				{
					ti = 0;
				}
			}
			else if (type.equals("08"))
			{
				int lx = getX() + (getWidth() >> 1) - 7;
				int ly = getY() + getHeight() - 10;
				
				PinkLaser laser;
				
				laser = new PinkLaser(lx, ly, "t,0.5,^,0.5,*", "t,1.8,^", true);
		        GamePanel.getSprites().add(laser);
		        
		        laser = new PinkLaser(lx, ly, "t,0.5,^,-0.5,*", "t,1.8,^",
		        	true);
		        GamePanel.getSprites().add(laser);
		        
		        laser = new PinkLaser(lx, ly, "0", "t,1.6,^", true);
		        GamePanel.getSprites().add(laser);
			}
		}
	}
	
	/**
	 * Draws the enemy.
	 */
	public void draw(Graphics g)
	{
		super.draw(g);
		super.iterate();
	}
}
