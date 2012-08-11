import java.awt.Graphics;
import java.awt.Image;

/**
 * This class helps to control the state of game and can handle switching
 * between game states.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class GameState
{
	static final int TITLE_SCREEN = 0;
	static final int GAME_PLAY = 1;
	static final int DEAD = 2;
	static final int LEVEL_COMPLETE = 3;
	static final int GAME_PAUSED = 4;
	static final int GAME_OVER = 5;
	static final int WINNER = 6;
	static final int CREDITS = 7;
	static final int CONTROLS = 8;
	static final int CONTROLS_TWO = 9;
	static final int STORY = 10;
	static final int STORY_TWO = 11;
	static final int MENU = 12;
	static final int EXIT = 13;
	static final int LEVEL_STARTING = 14;
	
	private static int currentState = TITLE_SCREEN;
	private static int loading = 0;
	private static int nextState = currentState;
	private static int loadingTop = 0;
	private static int loadingBottom = 0;
	private static Image topImage = Utilities
		.loadImage("loading-top.png");
	private static Image bottomImage = Utilities
		.loadImage("loading-bottom.png");

	/**
	 * This method gets the current state of the game.
	 * 
	 * @return	Returns the current state of the game as an integer
	 */
	public static int getCurrentState()
	{
		return currentState;
	}
	
	/**
	 * This method loads a different state with a transition.
	 * 
	 * @param state		The new state to load
	 */
	public static void loadState(int state)
	{
		loadingTop = -topImage.getHeight(null);
		loadingBottom = Global.WINDOW_HEIGHT;
		loading = 1;
		nextState = state;
	}
	
	/**
	 * This method loads a different state instantly without a transition.
	 * 
	 * @param state		The new state to load
	 */
	public static void switchState(int state)
	{
		currentState = state;
	}
	
	/**
	 * This method determines whether the game is currently transitioning
	 * between states.
	 * 
	 * @return	Returns true if the game is currently transitioning and false if
	 * 			it is not
	 */
	public static boolean isLoading()
	{
		return (loading != 0);
	}
	
	/**
	 * This method draws the transition animation.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public static void drawLoad(Graphics g)
	{	
		if (loading == 1 || loading == 2)
		{
			if (loading == 1)
			{
				loadingTop += 60;
				loadingBottom -= 60;
			
				if (loadingTop >= 0)
				{
					currentState = nextState;
					loading = 2;
				}
			}
			else
			{
				loadingTop -= 60;
				loadingBottom += 60;
				
				if (loadingBottom > Global.WINDOW_HEIGHT)
				{
					loading = 0;
				}
			}
			
			g.drawImage(topImage, 0, loadingTop, null);
			g.drawImage(bottomImage, 0, loadingBottom, null);
		}
	}
}
