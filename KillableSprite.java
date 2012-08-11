import java.awt.Image;

/**
 * This class represents a sprite that can be killed.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public abstract class KillableSprite extends Sprite
{
	private int health;
	
	/**
	 * This constructor creates a new "killable" sprite.
	 * 
	 * @param x				The starting x position of the sprite
	 * @param y				The starting y position of the sprite
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param startHealth	The starting health of the enemy
	 */
	public KillableSprite(int x, int y, Image[] spriteImages, int firstFrame,
		int lastFrame, int startHealth)
	{
		super(x, y, spriteImages, firstFrame, lastFrame);
		health = startHealth;
	}
	
	/**
	 * This method offsets the health of the player.
	 * 
	 * @param offset	The value by which to offset the player's health
	 */
	public void offsetHealth(int offset)
	{
		health += offset;
	}
	
	/**
	 * This method method gets the amount of health that the player has.
	 * 
	 * @return	Returns the amount of health that the player has.
	 */
	public int getHealth()
	{
		return health;
	}
	
	/**
	 * This method sets the health of the player to a given value.
	 * 
	 * @param healthLevel	The value to set the player's health to
	 */
	public void setHealth(int healthLevel)
	{
		health = healthLevel;
	}
}
