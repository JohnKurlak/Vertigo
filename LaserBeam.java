import java.awt.Image;
import java.awt.Point;

/**
 * This class represents a laser beam that the player can fire.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class LaserBeam extends PlayerWeapon
{
	private static Image laserBeamImage = Utilities.loadImage("laser-beam.png");
	private int t = 0;
	private int startX;
	private int startY;
	
	/**
	 * This constructor creates a new laser beam that the player can fire.
	 * 
	 * @param x				The starting x position of the laser beam
	 * @param y				The starting y position of the laser beam
	 * @param xFunction		The parameterized x function for the enemy's path
	 * @param yFunction		The parameterized y function for the enemy's path
	 */
	public LaserBeam(int x, int y, String xFunction, String yFunction)
	{	
		super(x, y, new Image[] { laserBeamImage } , 0, 0, 10, xFunction, yFunction);
		startX = x;
		startY = y;
	}
	
	/**
	 * The method updates the position of the laser beam.
	 */
	public void updatePosition()
	{
		super.updatePosition();
		
		Point nextPosition = getPath().getLocation(t++);
		
		setX(nextPosition.x + startX);
		setY(nextPosition.y + startY);
	}
}
