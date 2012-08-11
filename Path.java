import java.awt.Point;

/**
 * This class stores and processes paths that sprites can follow.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Path
{
	private String xFunction;
	private String yFunction;
	
	/**
	 * This constructor creates a new path for a sprite to follow.
	 * 
	 * @param xPath		A parameterized function of t representing the x
	 * 					coordinate
	 * @param yPath		A parameterized function of t representing the y
	 * 					coordinate
	 */
	public Path(String xPath, String yPath)
	{
		xFunction = xPath;
		yFunction = yPath;
	}
	
	/**
	 * This method retrieves the location of the sprite for a given value of t.
	 * 
	 * @param t		The time at which you would like to get the location
	 * @return		Returns a point corresponding to the sprite's location
	 */
	public Point getLocation(int t)
	{	
		int x = ExpressionParser.evaluate(xFunction, t);
		int y = ExpressionParser.evaluate(yFunction, t);
		
		return new Point(x, y);
	}
}
