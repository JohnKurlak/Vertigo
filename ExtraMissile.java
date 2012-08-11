import java.awt.Image;

/**
 * This class represents an extra missile that the player can pick up.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class ExtraMissile extends PowerUp implements Collidable
{
	/**
	 * This constructor creates a new extra missile power-up.
	 * 
	 * @param x				The starting x position of the extra missile
	 * @param yBlock		The starting y block of the extra missile
	 * @param yOffset		The starting y offset into the block for the extra
	 * 							missile
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param xPath			The parameterized x function for the extra missile's
	 * 							path
	 * @param yPath			The parameterized y function for the extra missile's
	 * 							path
	 */
	public ExtraMissile(int x, int yBlock, int yOffset, Image[] spriteImages,
		int firstFrame, int lastFrame, String xPath, String yPath)
	{
		super(x, yBlock, yOffset, spriteImages, firstFrame, lastFrame, xPath,
			yPath);
	}
	
	/**
	 * This method handles what happens when the extra missile power-up collides
	 * with something else.
	 */
	public void collision(Sprite object)
	{
		if (object instanceof Player)
		{
			Player player = (Player) object;
			player.giveMissile();
			super.remove();
			Sound laserSound = new Sound("item-pickup.wav");
	        laserSound.playOnce();
		}
	}
}
