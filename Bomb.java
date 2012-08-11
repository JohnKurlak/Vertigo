import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This class represents a bomb that the player fires.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Bomb extends Weapon
{
	private static final int BLOCKS_PER_Y = Global.WINDOW_HEIGHT /
		Global.BLOCK_SIZE;
	private static Image bombImage = Utilities.loadImage("bomb.png");
	private int t = 0;
	private int startX;
	private int startY;
	private Sound bombSound;
	
	/**
	 * This constructor creates a new bomb.
	 * 
	 * @param x				The start x position of the bomb
	 * @param y				The start y position of the bomb
	 * @param xFunction		The parameterized x function for the bomb's path
	 * @param yFunction		The parameterized y function for the bomb's path
	 */
	public Bomb(int x, int y, String xFunction, String yFunction)
	{	
		super(x, y, new Image[] { bombImage } , 0, 0, 1000, xFunction, yFunction);
		startX = x;
		startY = y;
		
		// Play the bomb sound when a bomb is made
		bombSound = new Sound("bomb.wav");
        bombSound.playOnce();
	}
	
	/**
	 * Updates the position of the bomb for every new frame.
	 */
	public void updatePosition()
	{
		super.updatePosition();
		
		// Update the bomb's position
		Point nextPosition = getPath().getLocation(t++);
		setX(nextPosition.x + startX);
		int nextY = nextPosition.y + startY;
		setY(nextY);
		
		// Blow the bomb up after it reaches mid screen
		if (t > 120 || (nextY < (Global.WINDOW_HEIGHT >> 1) && t > 30))
		{
			bombSound.stop();
			super.remove();
			GamePanel.makeExplosion(getX(), getY());
			
			// Injure all the enemies on the screen
			
			ArrayList<Sprite> sprites = GamePanel.getSprites();
			
			for (int i = 0; i < sprites.size(); i++)
			{
				if (sprites.get(i) instanceof Enemy)
				{
					Enemy enemy = (Enemy) sprites.get(i);
					
					int currentYTop = GamePanel.getLevel().getCurrentY() +
						BLOCKS_PER_Y - 1;
					boolean visibleX = enemy.getX() + enemy.getWidth() >= 0 &&
						enemy.getX() <= Global.WINDOW_WIDTH;
					boolean yOnScreen = enemy.getY() <= Global.WINDOW_HEIGHT +
						100;
					boolean visibleY = enemy.getYBlock() >= currentYTop &&
						yOnScreen;
					
					if (visibleX && visibleY && enemy.isAlive())
					{
						enemy.offsetHealth(-300);
						
						if (enemy.getHealth() <= 0)
						{
							enemy.remove();
							GamePanel.offsetNumLevelPoints(100);
							GamePanel.makeExplosion(enemy.getX(), enemy.getY());
						}
					}
				}
			}
		}
	}
}
