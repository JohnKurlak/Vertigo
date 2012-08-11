import java.awt.Image;

/**
 * This class represents plasma orbs that enemies can fire.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class PlasmaOrb extends EnemyWeapon
{
	private static Image plasmaOrbImage = Utilities.loadImage("plasma-orb.png");
	
	/**
	 * This constructor creates a new plasma orb that an enemy can fire.
	 * 
	 * @param x				The starting x position of the plasma orb
	 * @param y				The starting y position of the plasma orb
	 * @param xFunction		The parameterized x function for the plasma orb
	 * @param yFunction		The parameterized y function for the plasma orb
	 * @param seeksHeat		Whether the plasma orb is heat-seeking
	 */
	public PlasmaOrb(int x, int y, String xFunction, String yFunction, boolean
		seeksHeat)
	{	
		super(x, y, new Image[] { plasmaOrbImage } , 0, 0, 1, xFunction, yFunction,
			seeksHeat);
	}
}
