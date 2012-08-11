import java.awt.Image;
import java.awt.Point;

/**
 * This class represents a missile that the player can fire.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Missile extends PlayerWeapon
{
	private static Image missileImage = Utilities.loadImage("missile.png");
	private int t = 0;
	private int startX;
	private int startY;
	private Sound missileSound;
	
	/**
	 * This constructor creates a new missle that the player can fire.
	 * 
	 * @param x				The starting x position of the missile
	 * @param y				The starting y position of the missile
	 * @param xFunction		The parameterized x function for the missile's path
	 * @param yFunction		The parameterized y function for the missile's path
	 */
	public Missile(int x, int y, String xFunction, String yFunction)
	{	
		super(x, y, new Image[] { missileImage } , 0, 0, 100, xFunction, yFunction);
		startX = x;
		startY = y;
		missileSound = new Sound("missile.wav");
        missileSound.playOnce();
	}
	
	/**
	 * This method updates the position of the missile.
	 */
	public void updatePosition()
	{
		super.updatePosition();
		
		Point nextPosition = getPath().getLocation(t++);
		
		setX(nextPosition.x + startX);
		setY(nextPosition.y + startY);
	}
}
