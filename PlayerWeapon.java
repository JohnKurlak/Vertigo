import java.awt.Image;

/**
 * This class represents a weapon that the player can use.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public abstract class PlayerWeapon extends Weapon
{	
	/**
	 * This constructor creates a new weapon for the player.
	 * 
	 * @param x				The starting x position of the player
	 * @param y				The starting y position of the player
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param damageValue	The amount of damage that this weapon inflicts
	 * @param xPath			The parameterized x function for the player's path
	 * @param yPath			The parameterized y function for the player's path
	 */
	public PlayerWeapon(int x, int y, Image[] spriteImages, int firstFrame, int
		lastFrame, int damageValue, String xPath, String yPath)
	{
		super(x, y, spriteImages, firstFrame, lastFrame, damageValue, xPath,
			yPath);
	}
}