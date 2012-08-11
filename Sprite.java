import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * This class represents a graphic or animation that can be drawn on the screen.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public abstract class Sprite
{
	private Point position;
	private Image[] images;
	private int firstIndex;
	private int lastIndex;
	private int currentIndex;
	private int width;
	private int height;
	private boolean isAlive = true;
	
	/**
	 * This constructor creates a new sprite that can be drawn on the screen.
	 * 
	 * @param x				The starting x position of the sprite
	 * @param y				The starting y position of the sprite
	 * @param spriteImages	The images representing the sprite
	 * @param firstFrame	The index of the first frame to be drawn during
	 * 							animation
	 * @param lastFrame		The index of the last frame to be drawn during
	 * 							animation
	 */
	public Sprite(int x, int y, Image[] spriteImages, int firstFrame, int
		lastFrame)
	{
		position = new Point(x, y);
		images = spriteImages;
		setRange(firstFrame, lastFrame);
		width = images[0].getWidth(null);
		height = images[0].getHeight(null);
	}
	
	/**
	 * This method iterates the sprite through its animation, if any.
	 * 
	 * @return	Returns true if there are following animations and false if it
	 * 			has completed its animation
	 */
	public boolean iterate()
	{
		if (currentIndex < lastIndex)
		{
			currentIndex++;
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method sets the range of animation for the sprite.
	 * 
	 * @param firstFrame	The start of the animation's range
	 * @param lastFrame		The end of the animation's range
	 */
	public void setRange(int firstFrame, int lastFrame)
	{
		firstIndex = firstFrame;
		lastIndex = lastFrame;
		currentIndex = firstIndex;
	}
	
	/**
	 * This method draws the sprite on the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public void draw(Graphics g)
	{
		if (GameState.getCurrentState() != GameState.GAME_PAUSED)
		{
			updatePosition();
		}
		
		g.drawImage(images[currentIndex], position.x, position.y, null);
	}
	
	/**
	 * This method sets the current index of the animation, if any.
	 * 
	 * @param index		The current index of the animation
	 */
	public void setCurrentIndex(int index)
	{
		currentIndex = index;
	}
	
	/**
	 * This method removes a sprite.
	 */
	public void remove()
	{
		isAlive = false;
	}
	
	/**
	 * This method determines whether or not a sprite still exists.
	 * 
	 * @return	Returns true if the sprite exists and false if not
	 */
	public boolean isAlive()
	{
		return isAlive;
	}
	
	/**
	 * This method resurrects a sprite. ZOMBIEZZZ!!!1!
	 */
	public void makeAlive()
	{
		isAlive = true;
	}
	
	/**
	 * This method offsets the x position of the sprite.
	 * 
	 * @param amount	The number of pixels to offset the sprite by
	 */
	public void offsetX(int amount)
	{
		position.x += amount;
	}
	
	/**
	 * This method offsets the y position of the sprite.
	 * 
	 * @param amount	The number of pixels to offset the sprite by
	 */
	public void offsetY(int amount)
	{
		position.y += amount;
	}
	
	/**
	 * This method sets the x position of the sprite.
	 * 
	 * @param xPos	The x position to set the sprite to
	 */
	public void setX(int xPos)
	{
		position.x = xPos;
	}
	
	/**
	 * This method sets the y position of the sprite.
	 * 
	 * @param yPos	The y position to set the sprite to
	 */
	public void setY(int yPos)
	{
		position.y = yPos;
	}
	
	/**
	 * This method gets the x position of the sprite.
	 * 
	 * @return	Returns the x position
	 */
	public int getX()
	{
		return position.x;	
	}
	
	/**
	 * This method gets the y position of the sprite.
	 * 
	 * @return	Returns the y position
	 */
	public int getY()
	{
		return position.y;
	}
	
	/**
	 * This method gets the width of the sprite.
	 * 
	 * @return	Returns the width in pixels
	 */
	public int getWidth()
	{
		return width;	
	}
	
	/**
	 * This method gets the height of the sprite.
	 * 
	 * @return	Returns the height in pixels
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * This method requires that children of Sprite implement an update position
	 * method.
	 */
	public abstract void updatePosition();
}
