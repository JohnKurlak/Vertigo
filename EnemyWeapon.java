import java.awt.Image;
import java.awt.Point;

/**
 * This class represents an enemy's weapon.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public abstract class EnemyWeapon extends Weapon
{	
	private int t = 0;
	
	private int startY;
	private boolean heatSeeking;

	/**
	 * This constructor creates a new weapon for an enemy.
	 * 
	 * @param x				The starting x position of the enemy
	 * @param y				The starting y position of the enemy
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param damageValue	The amount of damage that this weapon inflicts
	 * @param xPath			The parameterized x function for the weapon's path
	 * @param yPath			The parameterized y function for the weapon's path
	 * @param seeksHeat		Whether the weapon is heat-seeking
	 */
	public EnemyWeapon(int x, int y, Image[] spriteImages, int firstFrame, int
		lastFrame, int damageValue, String xPath, String yPath, boolean
		seeksHeat)
	{
		super(x, y, spriteImages, firstFrame, lastFrame, damageValue, xPath,
			yPath);
		startY = y;
		heatSeeking = seeksHeat;
	}
	
	/**
	 * Updates the position of the enemy.
	 */
	public void updatePosition()
	{
		// Remove the enemy when it goes off screen
		if (super.getY() > Global.WINDOW_HEIGHT + 100)
		{
			super.remove();
		}
		
		super.updatePosition();
		
		Player player = GamePanel.getPlayer();
		Point nextPosition = getPath().getLocation(t++);
		int nextX = nextPosition.x + getX();
		
		if (heatSeeking)
		{
			int playerX = player.getX();
			int offsetX = playerX - nextX;
			
			if (offsetX > 5)
			{
				nextX += 1;
			}
			else if (offsetX < -5)
			{
				nextX -= 1;
			}
		}
		
		setX(nextX);
		setY(nextPosition.y + startY);
	}
}