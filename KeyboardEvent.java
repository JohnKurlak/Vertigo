import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class handles key presses and releases. 
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class KeyboardEvent implements KeyListener
{
	private static boolean leftArrow = false;
	private static boolean rightArrow = false;
	private static boolean upArrow = false;
	private static boolean downArrow = false;
	private static boolean space = false;
	private static boolean shift = false;
	private static boolean control = false;
	private static boolean keyDown = false;
	
	/**
	 * This method is invoked when a key is typed.
	 */
	public void keyTyped(KeyEvent e)
	{
		// Method not needed
	}

	/**
	 * This method handles key presses.
	 */
    public void keyPressed(KeyEvent e)
    {
    	int key = e.getKeyCode();
    	
    	keyDown = true;
    	
    	if (!GameState.isLoading())
    	{
	    	if (GameState.getCurrentState() != GameState.GAME_PLAY)
	    	{
	    		GamePanel.keyPressed(key);
	    	}
	    	else if (key == KeyEvent.VK_SPACE && !space)
	    	{
	    		GamePanel.keyPressed(key);
	    	}
	    	else if (key == KeyEvent.VK_SHIFT && !shift)
	    	{
	    		GamePanel.keyPressed(key);
	    	}
	    	else if (key == KeyEvent.VK_CONTROL && !control)
	    	{
	    		GamePanel.keyPressed(key);
	    	}
	    	else if (key != KeyEvent.VK_SPACE && key != KeyEvent.VK_SHIFT
	    		&& key != KeyEvent.VK_CONTROL)
	    	{
	        	GamePanel.keyPressed(key);
	    	}
    	}
    		
    	switch (key)
    	{
    		case KeyEvent.VK_LEFT:
    			leftArrow = true;
    			break;
    		case KeyEvent.VK_RIGHT:
    			rightArrow = true;
    			break;
    		case KeyEvent.VK_UP:
    			upArrow = true;
    			break;
    		case KeyEvent.VK_DOWN:
    			downArrow = true;
    			break;
    		case KeyEvent.VK_SPACE:
    			space = true;
    			break;
    		case KeyEvent.VK_SHIFT:
    			shift = true;
    			break;
    		case KeyEvent.VK_CONTROL:
    			control = true;
    			break;
    	}
    }

    /**
     * This method handles key releases.
     */
    public void keyReleased(KeyEvent e)
    {	
    	int key = e.getKeyCode();
    	
    	keyDown = false;
    	
    	switch (key)
    	{
    		case KeyEvent.VK_LEFT:
    			leftArrow = false;
    			break;
    		case KeyEvent.VK_RIGHT:
    			rightArrow = false;
    			break;
    		case KeyEvent.VK_UP:
    			upArrow = false;
    			break;
    		case KeyEvent.VK_DOWN:
    			downArrow = false;
    			break;
    		case KeyEvent.VK_SPACE:
    			space = false;
    			break;
    		case KeyEvent.VK_SHIFT:
    			shift = false;
    			break;
    		case KeyEvent.VK_CONTROL:
    			control = false;
    			break;
    	}
    }

    /**
     * This method determines whether the left arrow is down.
     * 
     * @return	Returns true if the left arrow is down and false if the left
     * 			arrow is up
     */
    public static boolean getLeftArrow()
    {
    	return leftArrow;
    }
    
    /**
     * This method determines whether the right arrow is down.
     * 
     * @return	Returns true if the right arrow is down and false if the right
     * 			arrow is up
     */
    public static boolean getRightArrow()
    {
    	return rightArrow;
    }
    
    /**
     * This method determines whether the up arrow is down.
     * 
     * @return	Returns true if the up arrow is down and false if the up arrow
     * 			is up
     */
    public static boolean getUpArrow()
    {
    	return upArrow;
    }
    
    /**
     * This method determines whether the down arrow is down.
     * 
     * @return	Returns true if the down arrow is down and false if the down
     * 			arrow is up
     */
    public static boolean getDownArrow()
    {
    	return downArrow;
    }
    
    /**
     * This method determines whether the space bar is down.
     * 
     * @return	Returns true if the space bar is down and false if the space
     * 			bar is up
     */
    public static boolean getSpace()
    {
    	return space;
    }
    
    /**
     * This method determines whether the shift key is down.
     * 
     * @return	Returns true if the shift key is down and false if the shift key
     * 			is up
     */
    public static boolean getShift()
    {
    	return shift;
    }
    
    /**
     * This method determines whether the control key is down.
     * 
     * @return	Returns true if the control key is down and false if the control
     * 			key is up
     */
    public static boolean getControl()
    {
    	return control;
    }
    
    /**
     * This method determines whether a key is down.
     * 
     * @return	Returns true if a key is down and false if a key is not down
     */
    public static boolean isKeyDown()
    {
    	return keyDown;
    }
}
