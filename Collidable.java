/**
 * This interface is for sprites that can collide with other sprites.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public interface Collidable
{
	/**
	 * This method gets called by the collision manager if there is a collision
	 * on a sprite that implements this method.
	 * 
	 * @param object	The sprite that collided with the sprite implementing
	 * 					this interface.
	 */
	public abstract void collision(Sprite object);
}