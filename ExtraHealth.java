import java.awt.Image;

/**
 * This class represents an extra life powerup that the player can pick up.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class ExtraHealth extends PowerUp implements Collidable
{
	/**
	 * This constructor creates a new extra health power-up.
	 * 
	 * @param x				The starting x position of the extra health
	 * @param yBlock		The starting y block of the extra health
	 * @param yOffset		The starting y offset into the block for the extra
	 * 							health
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param xPath			The parameterized x function for the extra health's
	 * 							path
	 * @param yPath			The parameterized y function for the extra health's
	 * 							path
	 */
	public ExtraHealth(int x, int yBlock, int yOffset, Image[] spriteImages, int
		firstFrame, int lastFrame, String xPath, String yPath)
	{
		super(x, yBlock, yOffset, spriteImages, firstFrame, lastFrame, xPath,
			yPath);
	}
	
	/**
	 * This method handles what happens when the extra health power-up collides
	 * with something else.
	 */
	public void collision(Sprite object)
	{
		if (object instanceof Player)
		{
			Player player = (Player) object;
			player.offsetHealth(20);
			
			if (player.getHealth() > 100)
			{
				player.setHealth(100);
			}
			
			super.remove();
			Sound laserSound = new Sound("item-pickup.wav");
	        laserSound.playOnce();
		}
	}
}
