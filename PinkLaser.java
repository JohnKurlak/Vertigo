import java.awt.Image;

/**
 * This class represents a pink laser that enemies can fire.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class PinkLaser extends EnemyWeapon
{
	private static Image pinkLaserImage = Utilities.loadImage("pink-laser.png");
	
	/**
	 * This constructor creates a new pink laser that an enemy can fire.
	 * 
	 * @param x				The starting x position of the pink laser
	 * @param y				The starting y position of the pink laser
	 * @param xFunction		The parameterized x function for the pink laser
	 * @param yFunction		The parameterized y function for the pink laser
	 * @param seeksHeat		Whether the pink laser is heat-seeking
	 */
	public PinkLaser(int x, int y, String xFunction, String yFunction, boolean
		seeksHeat)
	{	
		super(x, y, new Image[] { pinkLaserImage } , 0, 0, 1, xFunction, yFunction,
			seeksHeat);
	}
}
