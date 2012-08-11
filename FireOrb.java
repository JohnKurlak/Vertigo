import java.awt.Image;

/**
 * This class represents a fire orb that enemies can fire.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class FireOrb extends EnemyWeapon
{
	private static Image fireOrbImage = Utilities.loadImage("fire-orb.png");
	
	/**
	 * This constructor creates a new fire orb that an enemy can fire.
	 * 
	 * @param x				The starting x position of the fire orb
	 * @param y				The starting y position of the fire orb
	 * @param xFunction		The parameterized x function for the fire orb
	 * @param yFunction		The parameterized y function for the fire orb
	 * @param seeksHeat		Whether the fire orb is heat-seeking
	 */
	public FireOrb(int x, int y, String xFunction, String yFunction, boolean
		seeksHeat)
	{	
		super(x, y, new Image[] { fireOrbImage } , 0, 0, 1, xFunction, yFunction,
			seeksHeat);
	}
}
