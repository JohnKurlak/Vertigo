import java.awt.Image;
import java.awt.Point;

/**
 * This class represents a power up that the player can pick up.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class PowerUp extends Sprite
{
	private Path path;
	private int t = 0;
	private int startX;
	private int startY;
	private int startYBlock;
	
	/**
	 * This constructor creates a new power-up that the player can pick up.
	 * 
	 * @param x				The starting x position of the power-up
	 * @param yBlock		The starting y block of the power-up
	 * @param yOffset		The starting y offset into the block for the
	 * 							power-up
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 * @param xPath			The parameterized x function for the power-up's path
	 * @param yPath			The parameterized y function for the power-up's path
	 */
	public PowerUp(int x, int yBlock, int yOffset, Image[] spriteImages, int
		firstFrame, int lastFrame, String xPath, String yPath)
	{
		super(x - (spriteImages[0].getWidth(null) >> 1), -yOffset -
			spriteImages[0].getHeight(null), spriteImages, firstFrame,
			lastFrame);
		path = new Path(xPath, yPath);
		startX = x - (spriteImages[0].getWidth(null) >> 1);
		startY = yOffset - (Global.BLOCK_SIZE << 1) - 5;
		startYBlock = yBlock;
		super.setRange(firstFrame, lastFrame);
	}

	/**
	 * This method updates the position (and image) of the power-up.
	 */
	public void updatePosition()
	{
		if (super.getY() > Global.WINDOW_HEIGHT + 100)
		{
			super.remove();
		}
		
		Point nextPosition = path.getLocation(t++);
		setX(nextPosition.x + startX);
		setY(nextPosition.y + startY);
		
		if ((t & 1) == 0)
		{
			iterate();
		}
	}
	
	/**
	 * This method returns the start y block of the power-up.
	 * 
	 * @return	Returns the start y block
	 */
	public int getYBlock()
	{
		return startYBlock;
	}
	
	/**
	 * This method advances the power-up through its flashing animation.
	 * 
	 * @return	Returns true
	 */
	public boolean iterate()
	{
		boolean iter = super.iterate();
		
		if (!iter)
		{
			setCurrentIndex(0);
		}
		
		return true;
	}
}
