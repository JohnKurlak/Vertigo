import java.awt.Graphics;
import java.awt.Image;

/**
 * This class represents an explosion.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Explosion extends Sprite
{
	private static final Image EXPLOSION1 = Utilities
	.loadImage("explosion1.png");
	private static final Image EXPLOSION2 = Utilities
	.loadImage("explosion2.png");
	private static final Image EXPLOSION3 = Utilities
	.loadImage("explosion3.png");
	private int iterator = 0;
	
	/**
	 * This constructor creates a new explosion.
	 * 
	 * @param x		The starting x position of the explosion
	 * @param y		The starting y position of the explosion
	 */
	public Explosion(int x, int y)
	{
		super(x, y, new Image[] { EXPLOSION1, EXPLOSION2, EXPLOSION3,
			EXPLOSION2, EXPLOSION1 }, 0, 4);
	}
	
	/**
	 * This method draws the explosion on the screen.
	 */
	public void draw(Graphics g)
	{
		super.draw(g);
		
		iterator++;
		
		if (iterator == 2)
		{
			boolean iterated = super.iterate();
			
			if (!iterated)
			{
				remove();
			}
			
			iterator = 0;
		}
	}
	
	/**
	 * This method updates the position of the explosion.
	 */
	public void updatePosition()
	{
		offsetX(1);
		offsetY(-1);
	}
}
