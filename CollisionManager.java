import java.util.ArrayList;

/**
 * This class handles all collisions among sprites.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class CollisionManager
{
	/**
	 * This method detects whether the first sprite is overtop of the second
	 * sprite.
	 * 
	 * @param one	The first sprite
	 * @param two	The second sprite
	 * @return		Returns true if the two sprites are overtop of each other
	 * 				and false if they are not
	 */
	public static boolean detectCollision(Sprite one, Sprite two)
	{
		// To prevent errors concerning concurrent modification
		if (one != null && two != null)
		{
			// X coordinate detection
			int oneLeft = one.getX();
			int oneRight = one.getX() + one.getWidth();
			int twoLeft = two.getX();
			int twoRight = two.getX() + two.getWidth();
			boolean overLeftSide = (oneRight >= twoLeft) && (oneRight <=
				twoRight);
			boolean overRightSide = (oneLeft <= twoRight) && (oneLeft >=
				twoLeft);
			boolean overHoriz = (oneLeft <= twoLeft && oneRight
				>= twoRight);
			
			// Y coordinate detection
			int oneTop = one.getY();
			int oneBottom = one.getY() + one.getHeight();
			int twoTop = two.getY();
			int twoBottom = two.getY() + two.getHeight();
			boolean overTopSide = (oneBottom >= twoTop) && (oneBottom <=
				twoBottom);
			boolean overBottomSide = (oneTop <= twoBottom) && (oneTop >=
				twoTop);
			boolean overVert = (oneTop <= twoTop && oneBottom >=
				twoBottom);
			
			if ((overLeftSide || overRightSide || overHoriz) &&
				(overTopSide || overBottomSide || overVert))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method checks to see if there are collisions among sprites and calls
	 * their respective collision() methods if there is.
	 */
	public static void handleCollisions()
	{
		ArrayList<Sprite> sprites = GamePanel.getSprites();
		
		// Loop through all of the sprites
		for (int i = 0; i < sprites.size(); ++i)
		{
			Sprite one = sprites.get(i);
			
			// Only compare sprites with which we are concerned
			if ((one instanceof Collidable && one.isAlive()) && (one instanceof
				KillableSprite || one instanceof Weapon || one instanceof
				PowerUp))
			{
				for (int j = 0; j < sprites.size(); ++j)
				{
					try
					{
						Sprite two = sprites.get(j);
						
						// If one is a Player, Enemy, Weapon, or PowerUp and two
						// is not the same and there is a collision
						if (((one instanceof Player && !(two instanceof Player))
							|| (one instanceof Enemy && !(two instanceof Enemy))
							|| (one instanceof Weapon && !(two instanceof
							Weapon)) || (one instanceof PowerUp && !(two
							instanceof PowerUp))) && detectCollision(one, two))
						{
							Collidable collided = (Collidable) one;
							collided.collision(two);
						}
					}
					catch (IndexOutOfBoundsException e)
					{
						// Happens due to concurrency issues... and can be
						// ignored
					}
				}
			}
		}
	}
}
