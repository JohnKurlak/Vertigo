import java.awt.Image;

/**
 * This class represents a weapon that the player or an enemy can use.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public abstract class Weapon extends Sprite
{
	private int damageAmount;
	private Path path;
	
	/**
	 * This constructor creates a new weapon that a player or enemy may fire.
	 * 
	 * @param x				The starting x position of the weapon
	 * @param y				The starting y position of the weapon
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param damageValue	The amount of damage that this weapon inflicts
	 * @param xPath			The parameterized x function for the player's path
	 * @param yPath			The parameterized y function for the player's path
	 */
	public Weapon(int x, int y, Image[] spriteImages, int firstFrame, int
		lastFrame, int damageValue, String xPath, String yPath)
	{
		super(x, y, spriteImages, firstFrame, lastFrame);
		
		damageAmount = damageValue;
		path = new Path(xPath, yPath);
	}
	
	/**
	 * This method gets a reference to a path object.
	 * 
	 * @return	Returns a path
	 */
	public Path getPath()
	{
		return path;
	}
	
	/**
	 * This method removes a sprite if it is not yet visible.
	 */
	public void updatePosition()
	{
		if (super.getY() < -100)
		{
			super.remove();
		}
	}
	
	/**
	 * This method gets the damage amount prescribed by the weapon.
	 * 
	 * @return	Returns the damage amount of the weapon
	 */
	public int getDamageAmount()
	{
		return damageAmount;
	}
}