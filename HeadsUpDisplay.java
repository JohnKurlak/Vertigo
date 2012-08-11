import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

/**
 * This class draws the heads up display for game play.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class HeadsUpDisplay
{
	private static final Image PLANE = Utilities.loadImage("plane-small.png");
	private static final Image BOMB = Utilities.loadImage("bomb-small.png");
	private static final Image MISSILE = Utilities
		.loadImage("missile-small.png");
	private static Font daysLaterFont = Utilities.loadFont("28DaysLater.ttf");
	
	/**
	 * This method draws the heads up display on the screen.
	 * 
	 * @param g				The graphics object to which we can draw
	 * @param healthLevel	The current health of the player
	 * @param numLives		The current number of lives that the player has
	 * @param numMissiles	The current number of missiles that the player has
	 * @param numBombs		The current number of bombs that the player
	 * @param fm			A FontMetrics object we can use to use to size the
	 * 						font
	 */
	public static void draw(Graphics g, int healthLevel, int numLives, int
		numMissiles, int numBombs, FontMetrics fm)
	{
		int health = healthLevel;
		
		if (health < 0)
		{
			health = 0;
		}
		
		int healthFactor = (int) Math.ceil(health * 2.55);
		int deathFactor = 255 - healthFactor;
		Color color;
		
		if (deathFactor > healthFactor)
		{
			if (healthFactor < 64)
			{
				color = Color.RED;
			}
			else
			{
				color = Color.YELLOW;
			}
		}
		else
		{	
			color = Color.GREEN;
		}
		
		// Draw the health meter
		g.setColor(Color.WHITE);
		g.drawRect(5, 5, 103, 9);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(6, 6, 102, 8);
		g.setColor(color);
		g.fillRect(7, 7, health, 6);
		
		// Draw the icons
		g.drawImage(PLANE, 5, 20, null);
		g.drawImage(MISSILE, 9, 50, null);
		g.drawImage(BOMB, 8, 80, null);

		// Set the font
		GamePanel.useAntialiasText(g);
		Font font = daysLaterFont;
		font = font.deriveFont((float) 20);
		g.setFont(font);
		
		// Draw the number of lives
		g.setColor(Color.BLACK);
		g.drawString(numLives + "", 32, 41);
		g.setColor(Color.WHITE);
		g.drawString(numLives + "", 33, 42);
		
		// Draw the number of missiles
		g.setColor(Color.BLACK);
		g.drawString(numMissiles + "", 32, 71);
		g.setColor(Color.WHITE);
		g.drawString(numMissiles + "", 33, 72);
		
		// Draw the number of bombs
		g.setColor(Color.BLACK);
		g.drawString(numBombs + "", 32, 101);
		g.setColor(Color.WHITE);
		g.drawString(numBombs + "", 33, 102);
		
		// Draw the number of points
		int points = GamePanel.getNumLevelPoints();
		int x = Global.WINDOW_WIDTH - fm.stringWidth(points + "") - 5;
		g.setColor(Color.BLACK);
		g.drawString(points + "", x, 23);
		g.setColor(Color.WHITE);
		g.drawString(points + "", x + 1, 24);	
	}
}
