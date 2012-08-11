import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.JPanel;

/**
 * This class represents the main panel onto which everything is drawn.  Most of
 * the game processing originates from this class.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable
{
	private static final int FPS = 50;
	private static final int FRAME_DELAY = (1000 / FPS);
	private Thread thread;
	private static Level currentLevel;
	private static ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private static LinkedQueue<Sprite> deadSprites = new LinkedQueue<Sprite>();
	private static Player player = new Player();
	private static Sound backgroundMusic;
	private static Image titleImage = Utilities.loadImage("title.png");
	private static Image characters = Utilities.loadImage("characters.png");
	private static Image backgroundImage = Utilities
		.loadImage("starry-background.png");
	private static Image creditOverlay = Utilities
		.loadImage("credit-overlay.png");
	private static Image arrowKeys = Utilities.loadImage("arrow-keys.png");
	private static Image shiftKey = Utilities.loadImage("shift-key.png");
	private static Image spaceKey = Utilities.loadImage("space-key.png");
	private static Image pKey = Utilities.loadImage("p-key.png");
	private static Image aKey = Utilities.loadImage("a-key.png");
	private static Image dKey = Utilities.loadImage("d-key.png");
	private static Image controlKey = Utilities.loadImage("control-key.png");
	private static Image largePlane = Utilities.loadImage("plane-large.png");
	private static Font daysLaterFont = Utilities.loadFont("28DaysLater.ttf");
	private static Font defusedFont = Utilities.loadFont("Defused.ttf");
	private static Font viperNoraFont = Utilities.loadFont("ViperNora.ttf");
	private static Font ankFont = Utilities.loadFont("Ank.ttf");
	private static Color gray = new Color(200, 200, 200);
	private static Color darkPurple = new Color(104, 25, 127);
	private static Color lightPurple = new Color(211, 143, 231);
	private static int currentMenuItem = 0;
	private static int lastMenuIndex = 3;
	private static int levelTicks = 0;
	private static int levelTransTicks = 0;
	private static boolean levelComplete = false;
	private static int numPoints = 0;
	private static int numLevelPoints = 0;
	private static boolean changeLevel = false;
	private static int creditScroll = -300;
	
	/**
	 * This constructor creates the main panel for the game.
	 */
	public GamePanel()
	{
		setLayout(null);
		setBackground(Color.WHITE);
		setFocusable(true);
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(Global.WINDOW_WIDTH, Global
			.WINDOW_HEIGHT));
		addKeyListener(new KeyboardEvent());
		
		LevelManager.readLevelFile();
		
		start();	
		switchMusic("themeSong.wav");	
		
		currentLevel = LevelManager.startLevel();
		sprites.add(player);
	}
	
	/**
	 * This method advances levels.
	 */
	public void advanceLevel()
	{
		sprites.clear();
		deadSprites.clear();
		levelComplete = false;
		LevelManager.advanceLevel();
		player.reset();
		sprites.add(player);
		currentLevel = LevelManager.startLevel();
		levelComplete = false;
		levelTicks = 0;	
		
		if (currentLevel != null)
		{
			GameState.loadState(GameState.LEVEL_STARTING);
		}
		else
		{
			levelTicks = 0;
			GameState.loadState(GameState.WINNER);
		}
	}
	
	/**
	 * This method resets the game after the user wins.
	 */
	public void resetGame()
	{
		numLevelPoints = 0;
		numPoints = 0;
		sprites.clear();
		deadSprites.clear();
		levelComplete = false;
		player = new Player();
		levelTicks = 0;
		sprites.add(player);
		switchMusic("themeSong.wav");
		LevelManager.reset();
		currentLevel = LevelManager.startLevel();
		GameState.loadState(GameState.TITLE_SCREEN);
	}
	
	/**
	 * This method restarts the level and is called when the player dies.
	 */
	public void restartLevel()
	{	
		sprites.clear();
		deadSprites.clear();
		player.reset();
		sprites.add(player);
		currentLevel = LevelManager.startLevel();
		levelComplete = false;
		player.makeAlive();
	}
	
	/**
	 * This method gets the current level.
	 * 
	 * @return	Returns a Level object corresponding to the current level
	 */
	public static Level getLevel()
	{
		return currentLevel;
	}
	
	/**
	 * This method offsets the number of points that the player has accumulated
	 * for the current level.
	 * 
	 * @param offset	The number of points to add to the level score
	 */
	public static void offsetNumLevelPoints(int offset)
	{
		numLevelPoints += offset;
	}
	
	/**
	 * This method gets the number of points that the player has for the current
	 * level.
	 * 
	 * @return	Returns the number of points that the player has for the current
	 * 			level
	 */
	public static int getNumLevelPoints()
	{
		return numLevelPoints;
	}
	
	/**
	 * This method offsets the number of points that the player has accumulated
	 * for the current game.
	 * 
	 * @param offset	The number of points to add to the overall score
	 */
	public static void offsetNumPoints(int offset)
	{
		numPoints += offset;
	}
	
	/**
	 * This method gets the number of points that the player has for the current
	 * game.
	 * 
	 * @return	Returns the number of points that the player has for the current
	 * 			game
	 */
	public static int getNumPoints()
	{
		return numPoints;
	}
	
	/**
	 * This method lets the computer change whether the level has been finished.
	 * 
	 * @param value		Whether the level is complete
	 */
	public static void setLevelComplete(boolean value)
	{
		levelComplete = value;
	}
	
	/**
	 * This method checks to see if the current level is still in progress.
	 * 
	 * @return	Returns true if the level is complete and false if the level is
	 * 			not
	 */
	public static boolean isLevelComplete()
	{
		return levelComplete;
	}
	
	/**
	 * This method returns a reference to the player.
	 * 
	 * @return	Returns a Player object referring to the player
	 */
	public static Player getPlayer()
	{
		return player;
	}
	
	/**
	 * This method returns a reference to the ArrayList of game sprites.
	 * 
	 * @return	Returns an ArrayList of Sprite objects referring to the sprites
	 * 			currently on the screen
	 */
	public static ArrayList<Sprite> getSprites()
	{
		return sprites;
	}
	
	/**
	 * This method adds a sprite to the list of sprites to be displayed.
	 * 
	 * @param sprite	The sprite to be displayed
	 */
	public static void addSprite(Sprite sprite)
	{
		sprites.add(sprite);
	}
	
	/**
	 * This method removes the sprites that have died from the sprites to be
	 * drawn.
	 */
	public static void removeDeadSprites()
	{
		try
		{
			sprites.removeAll(deadSprites);
			deadSprites.clear();
		}
		catch (ConcurrentModificationException e)
		{
			// We can ignore errors concerning concurrent modification
		}
	}
	
	/**
	 * This method makes an explosion on the screen.
	 * 
	 * @param x		The starting x position of the explosion.
	 * @param y		The starting y position of the explosion.
	 */
	public static void makeExplosion(int x, int y)
	{
		Explosion exp = new Explosion(x, y);
		
		Sound explosionSound = new Sound("explosion.wav");
        explosionSound.playOnce();
		
		addSprite(exp);
	}
	
	/**
	 * This method draws a string of horizontally-centered text on the screen.
	 * 
	 * @param g			The graphics object to which we can paint
	 * @param text		The text to be drawn on the screen
	 * @param font		The font of the text
	 * @param color		The color of the text
	 * @param size		The size of the text
	 * @param y			The y position of the text
	 */
	public void drawString(Graphics g, String text, Font font, Color color, int
		size, int y)
	{
		drawString(g, text, font, color, size, 0, y);
	}
	
	/**
	 * This method draws a string of horizontally-centered text on the screen]
	 * with a horizontal offset.
	 * 
	 * @param g			The graphics object to which we can paint
	 * @param text		The text to be drawn on the screen
	 * @param font		The font of the text
	 * @param color		The color of the text
	 * @param size		The size of the text
	 * @param xOffset	The horizontal offset from center
	 * @param y			The y position of the text
	 */
	public void drawString(Graphics g, String text, Font font, Color color, int
		size, int xOffset, int y)
	{
		Font derivedFont = font.deriveFont((float) size);
		FontMetrics fm = getFontMetrics(derivedFont);
		int xCenter = (Global.WINDOW_WIDTH >> 1) - (fm.stringWidth(text) >> 1);
		int x = xCenter + xOffset;
		
		drawStringAbsolute(g, text, derivedFont, color, size, x, y);
	}
	
	/**
	 * This method draws a string of text on the screen.
	 * 
	 * @param g			The graphics object to which we can paint
	 * @param text		The text to be drawn on the screen
	 * @param font		The font of the text
	 * @param color		The color of the text
	 * @param size		The size of the text
	 * @param x			The x position of the text
	 * @param y			The y position of the text
	 */
	public void drawStringAbsolute(Graphics g, String text, Font font, Color
		color, int size, int x, int y)
	{
		Font derivedFont = font.deriveFont((float) size);
		g.setFont(derivedFont);
		g.setColor(color);
		g.drawString(text, x, y);		
	}
	
	/**
	 * This method gets the color of the given menu item.
	 * 
	 * @param index		The index of the menu item to find the color of
	 * @return			Returns the color of the menu item at the given index
	 */
	public static Color getMenuItemColor(int index)
	{
		if (index == currentMenuItem)
		{
			return Color.YELLOW;
		}
		
		return Color.WHITE;
	}
	
	/**
	 * This method tells Java to draw the text onto the screen with antialias.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public static void useAntialiasText(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g = g2d;
	}
	
	/**
	 * This method switches the background music for the game.
	 * 
	 * @param relativePath		The relative path of the music file.
	 */
	public static void switchMusic(String relativePath)
	{
		if (backgroundMusic == null)
		{
			backgroundMusic = new Sound(relativePath);
			backgroundMusic.play();
		}
		
		if (!backgroundMusic.toString().equals(relativePath))
		{
			backgroundMusic.stop();
			backgroundMusic = new Sound(relativePath);
			backgroundMusic.play();	
		}
	}
	
	/**
	 * This method handles anything that is drawn onto the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		switch (GameState.getCurrentState())
		{
			case GameState.TITLE_SCREEN:
				g.drawImage(titleImage, 0, 0, null);
				break;
			case GameState.MENU:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				drawString(g, "MENU", defusedFont, darkPurple, 90, -4, 186);
				drawString(g, "MENU", defusedFont, lightPurple, 90, 190);
				drawString(g, "START GAME", daysLaterFont, getMenuItemColor(0),
					48, 320);
				drawString(g, "STORY", daysLaterFont, getMenuItemColor(1), 48,
					380);
				drawString(g, "CONTROLS", daysLaterFont, getMenuItemColor(2),
					48, 440);
				drawString(g, "EXIT", daysLaterFont, getMenuItemColor(3), 48,
					500);
				
				break;
			case GameState.STORY:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				drawString(g, "STORY", defusedFont, darkPurple, 56, -4, 86);
				drawString(g, "STORY", defusedFont, lightPurple, 56, 90);
				
				drawStringAbsolute(g, "Long ago, on the planet Java there" +
						" lived a virtual", viperNoraFont, gray, 20, 15, 160);
				drawStringAbsolute(g, "machine, Eli.  He had a homie named" +
						" JUnit who", viperNoraFont, gray, 20, 15, 190);
				drawStringAbsolute(g, "often tested his warrior-like methods" +
						" as both", viperNoraFont, gray, 20, 15, 220);
				drawStringAbsolute(g, "strove to uphold the principals of OOP" +
						" in their", viperNoraFont, gray, 20, 15, 250);
				drawStringAbsolute(g, "everyday lives.", viperNoraFont, gray,
					20, 15, 280);
				drawStringAbsolute(g, "Things were wonderful on Java until" +
						" one day...", viperNoraFont, gray, 20, 15, 340);
				drawStringAbsolute(g, "Eli gazed up at the Sun Microsystem" +
						" and beheld", viperNoraFont, gray, 20, 15, 370);
				drawStringAbsolute(g, "an eclipse.  His world went dark.  And" +
						" out of", viperNoraFont, gray, 20, 15, 400);
				drawStringAbsolute(g, "the darkness came invaders.  They were" +
						" a race", viperNoraFont, gray, 20, 15, 430);
				drawStringAbsolute(g, "who lived by the savage principles of" +
						" functional", viperNoraFont, gray, 20, 15, 460);
				drawStringAbsolute(g, "programming.  Among those leading the" +
						" force were", viperNoraFont, gray, 20, 15, 490);
				drawStringAbsolute(g, "WebCat and JavaDoc, once allies of" +
						" Java.", viperNoraFont, gray, 20, 15, 520);
				
				drawStringAbsolute(g, "Eli was stricken with fear.  His well" +
						" organized", viperNoraFont, gray, 20, 15, 580);
				
				break;
			case GameState.STORY_TWO:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				drawString(g, "STORY", defusedFont, darkPurple, 56, -4, 86);
				drawString(g, "STORY", defusedFont, lightPurple, 56, 90);
				
				drawStringAbsolute(g, "life was in peril!  Together, he and" +
						" JUnit invented", viperNoraFont, gray, 20, 15, 160);
				drawStringAbsolute(g, "a spaceship that was both robust and" +
						" secure. The", viperNoraFont, gray, 20, 15, 190);
				drawStringAbsolute(g, "architecture was neutral and highly" +
						" portable.", viperNoraFont, gray, 20, 15, 220);
				drawStringAbsolute(g, "They got on board, and began their" +
						" adventure, not", viperNoraFont, gray, 20, 15, 280);
				drawStringAbsolute(g, "knowing what would lie ahead...",
					viperNoraFont, gray, 20, 15, 310);
				
				g.drawImage(characters, (Global.WINDOW_WIDTH >> 1) -
					(characters.getWidth(null) >> 1), (Global.WINDOW_HEIGHT
					- characters.getHeight(null) + 40), null);
				
				break;
			case GameState.CONTROLS:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				drawString(g, "CONTROLS", defusedFont, darkPurple, 56, -4, 86);
				drawString(g, "CONTROLS", defusedFont, lightPurple, 56, 90);
				
				g.drawImage(arrowKeys, 60, 140, null);
				drawString(g, "Move Plane", daysLaterFont, gray, 48, 105, 210);
				
				g.drawImage(spaceKey, 60, 280, null);
				drawString(g, "Fire Laser", daysLaterFont, gray, 48, 110, 325);
				
				g.drawImage(shiftKey, 75, 370, null);
				drawString(g, "Fire Missile", daysLaterFont, gray, 48, 108,
					415);
				
				g.drawImage(controlKey, 100, 460, null);
				drawString(g, "Fire Bomb", daysLaterFont, gray, 48, 110, 505);
				
				g.drawImage(pKey, 113, 550, null);
				drawString(g, "Pause Game", daysLaterFont, gray, 48, 108, 595);
				
				break;
			case GameState.CONTROLS_TWO:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				drawString(g, "CONTROLS", defusedFont, darkPurple, 56, -4, 86);
				drawString(g, "CONTROLS", defusedFont, lightPurple, 56, 90);
				
				g.drawImage(aKey, 113, 140, null);
				drawString(g, "Dodge Left", daysLaterFont, gray, 48, 108, 185);
				
				g.drawImage(dKey, 113, 230, null);
				drawString(g, "Dodge Right", daysLaterFont, gray, 48, 108, 275);
				
				break;
			case GameState.LEVEL_STARTING:
				useAntialiasText(g);
				
				if (levelTicks == 0)
				{
					restartLevel();
					
					String path = currentLevel.getFileName() + ".wav";
					
					switchMusic(path);
				}
				
				g.drawImage(currentLevel.getImage(), 0, 0, null);
				
				drawString(g, currentLevel.getName(), daysLaterFont, darkPurple,
					72, -2, 108);
				drawString(g, currentLevel.getName(), daysLaterFont,
					lightPurple, 72, 110);
				
				drawString(g, currentLevel.getDescription(), daysLaterFont,
					Color.WHITE, 48, -2, 178);
				drawString(g, currentLevel.getDescription(), daysLaterFont,
					Color.BLACK, 48, 180);
				
				if (++levelTicks > 40 && !GameState.isLoading())
				{
					levelTransTicks = 0;
					levelTicks = 0;
					numLevelPoints = 0;
					GameState.loadState(GameState.GAME_PLAY);
				}
					
				break;
			case GameState.GAME_OVER:
			case GameState.LEVEL_COMPLETE:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				int levelAt = 0;
				int totalAt = 0;
				int stageTicks = levelTransTicks *
					(GamePanel.getNumLevelPoints() / 100);
				int gameTicks = levelTransTicks *
					(GamePanel.getNumPoints() / 100);
				
				if (gameTicks < GamePanel.getNumPoints())
				{
					levelTransTicks++;
					totalAt = gameTicks;
				}
				else
				{
					totalAt = GamePanel.getNumPoints();
				}
				
				if (stageTicks <= GamePanel.getNumLevelPoints())
				{
					levelTransTicks++;
					levelAt = stageTicks;
				}
				else
				{
					levelAt = GamePanel.getNumLevelPoints();
				}
				
				if (GameState.getCurrentState() != GameState.GAME_OVER)
				{
					drawString(g, "STAGE", defusedFont, darkPurple, 56, -4, 86);
					drawString(g, "STAGE", defusedFont, lightPurple, 56, 90);
					drawString(g, "COMPLETE", defusedFont, darkPurple, 56, -4,
						146);
					drawString(g, "COMPLETE", defusedFont, lightPurple, 56,
						150);
				}
				else
				{
					drawString(g, "GAME", defusedFont, darkPurple, 56, -4, 86);
					drawString(g, "GAME", defusedFont, lightPurple, 56, 90);
					drawString(g, "OVER", defusedFont, darkPurple, 56, -4, 146);
					drawString(g, "OVER", defusedFont, lightPurple, 56, 150);
				}
				
				drawString(g, "Level Score", daysLaterFont, gray, 48, 300);
				drawString(g, levelAt + "", daysLaterFont, Color.WHITE, 54,
					360);
				drawString(g, "Total Score", daysLaterFont, gray, 48, 500);
				drawString(g, totalAt + "", daysLaterFont, Color.WHITE, 54,
					560);
				
				if (changeLevel && !GameState.isLoading())
				{
					changeLevel = false;
					advanceLevel();
				}
				
				break;
			case GameState.DEAD:
				if (++levelTransTicks > 150)
				{
					levelTransTicks = 0;
					
					player.takeLife();
					
					if (player.getNumLives() >= 0)
					{
						//restartLevel();
						levelTicks = 0;
						GameState.loadState(GameState.LEVEL_STARTING);
					}
					else
					{
						GamePanel.offsetNumPoints(GamePanel
							.getNumLevelPoints());
						GameState.loadState(GameState.GAME_OVER);
					}
				}
			case GameState.GAME_PAUSED:
			case GameState.GAME_PLAY:
				currentLevel.draw(g);
			
				for (int i = 0; i < sprites.size(); i++)
				{
					// Draw all alive sprites that aren't enemies and power-ups
					if (!(sprites.get(i) instanceof Enemy) && !(sprites.get(i)
						instanceof PowerUp) && sprites.get(i).isAlive())
					{
						sprites.get(i).draw(g);
					}
					// If a sprite is dead, queue it for deletion
					else if (!sprites.get(i).isAlive())
					{
						deadSprites.add(sprites.get(i));
					}
				}
				
				GamePanel.removeDeadSprites();
				
				if (GameState.getCurrentState() == GameState.GAME_PAUSED)
				{
					useAntialiasText(g);
					drawString(g, "GAME PAUSED", daysLaterFont, Color.BLACK, 90,
						-4, Global.WINDOW_HEIGHT - 124);
					drawString(g, "GAME PAUSED", daysLaterFont, Color.WHITE, 90,
						Global.WINDOW_HEIGHT - 120);
				}
				
				// Draw the heads up display
				Font font = daysLaterFont;
				font = font.deriveFont((float) 20);
				HeadsUpDisplay.draw(g, player.getHealth(), player.getNumLives(),
					player.getNumMissiles(), player.getNumBombs(),
					getFontMetrics(font));
				
				break;
			case GameState.CREDITS:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				if (creditScroll == -300)
				{
					switchMusic("end-game.wav");
				}
				
				creditScroll += KeyboardEvent.isKeyDown() ? 50 : 5;
				
				drawString(g, "Programming", ankFont, gray, 28, 300 -
					creditScroll);
				drawString(g, "Weston Thayer", ankFont, Color.WHITE, 60, 380 -
					creditScroll);
				drawString(g, "John Kurlak", ankFont, Color.WHITE, 60, 460 -
					creditScroll);
				drawString(g, "Panhavorn Hok", ankFont, Color.WHITE, 60, 540 -
					creditScroll);
				
				drawString(g, "Graphic Design", ankFont, gray, 28, 700 -
					creditScroll);
				drawString(g, "Weston Thayer", ankFont, Color.WHITE, 60, 780 -
					creditScroll);
				drawString(g, "John Kurlak", ankFont, Color.WHITE, 60, 860 -
					creditScroll);
				drawString(g, "Panhavorn Hok", ankFont, Color.WHITE, 60, 940 -
					creditScroll);
				
				drawString(g, "Music and Sound Effects", ankFont, gray, 28, 1100
					- creditScroll);
				drawString(g, "Weston Thayer", ankFont, Color.WHITE, 60, 1180 -
					creditScroll);
				
				drawString(g, "Thanks for playing", daysLaterFont, Color.YELLOW,
					54, 1500 - creditScroll);
				
				if (creditScroll > 1380 && !GameState.isLoading())
				{
					resetGame();
				}
				
				g.drawImage(creditOverlay, 0, 0, null);
				drawString(g, "CREDITS", defusedFont, darkPurple, 56, -4, 86);
				drawString(g, "CREDITS", defusedFont, lightPurple, 56, 90);
				
				break;
			case GameState.WINNER:
				useAntialiasText(g);
				g.drawImage(backgroundImage, 0, 0, null);
				
				if (levelTicks == 0)
				{
					switchMusic("end-game.wav");
				}
				
				if (levelTicks > 0 || !GameState.isLoading())
				{
					if (++levelTicks < 35)
					{
						int rotate = (int) (levelTicks * -1.3);
						
						Path planePath = new Path("3,t,*,5.8,^,1000,t,*,2,^,/",
							"-1,t,*,5,^,2,t,*,2.6,^,/,-1,*");
						Point location = planePath.getLocation(levelTicks);
						
						drawTransformedImage(g, largePlane, location.x + 100,
							location.y + 100, 0.05 * Math.pow(levelTicks, 1.8)
							/ 10, rotate);
					}
					else
					{
						drawString(g, "YOU WIN", defusedFont, darkPurple, (int)
							(levelTicks * 1.5 - 35), -4, 300);
						drawString(g, "YOU WIN", defusedFont, lightPurple, (int)
							(levelTicks * 1.5 - 35), 304);
					}
					
					if (!GameState.isLoading() && levelTicks > 70)
					{
						creditScroll = -300;
						GameState.loadState(GameState.CREDITS);
					}
				}
				
				break;
		}
		
		GameState.drawLoad(g);
	}
	
	/**
	 * This method draws an image after resizing and rotating it.
	 * 
	 * @param g					The graphics object to which we can draw
	 * @param img				The image to draw
	 * @param x					The x position at which the image is to be drawn
	 * @param y					The y position at which the image is to be drawn
	 * @param percentOldSize	The percent of the original size
	 * @param rotation			The rotation angle in degrees
	 */
	public static void drawTransformedImage(Graphics g, Image img, int x, int y,
		double percentOldSize, int rotation)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints
			.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints
			.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints
			.VALUE_ANTIALIAS_ON);
		g2d.rotate(Math.toRadians(rotation), img.getWidth(null) >> 1,
			img.getHeight(null) >> 1);
		g2d.drawImage(img, x, y, (int)(img.getWidth(null) * percentOldSize),
			(int)(img.getHeight(null) * percentOldSize), null);
	}
	
	/**
	 * This method starts the game thread.
	 */
	public void start()
	{
		//If the game thread doesn't exist, create and start it
		if (thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * This method handles key presses (the implementations in here aren't in
	 * the KeyboardEvent class because things needs to happen just after the key
	 * is pressed).
	 * 
	 * @param keyCode	The numerical number representing the key
	 */
	public static void keyPressed(int keyCode)
	{
		switch (GameState.getCurrentState())
		{
			case GameState.GAME_OVER:
				creditScroll = -300;
				GameState.loadState(GameState.CREDITS);
				
				break;
			case GameState.LEVEL_COMPLETE:
				changeLevel = true;
				
				break;
			case GameState.STORY:
				if (!GameState.isLoading())
				{
					GameState.switchState(GameState.STORY_TWO);
				}
				
				break;
			case GameState.CONTROLS:
				if (!GameState.isLoading())
				{
					GameState.switchState(GameState.CONTROLS_TWO);
				}
				
				break;
			case GameState.TITLE_SCREEN:
			case GameState.CONTROLS_TWO:
			case GameState.STORY_TWO:
				if (!GameState.isLoading())
				{
					GameState.loadState(GameState.MENU);	
				}
				
				break;
			case GameState.MENU:
				switch (keyCode)
				{
					case KeyEvent.VK_SPACE:
					case KeyEvent.VK_ENTER:
						if (currentMenuItem == 0)
						{
							levelTicks = 0;
							GameState.loadState(GameState.LEVEL_STARTING);
						}
						else if (currentMenuItem == 1)
						{
							GameState.loadState(GameState.STORY);
						}
						else if (currentMenuItem == 2)
						{
							GameState.loadState(GameState.CONTROLS);
						}
						else if (currentMenuItem == 3)
						{
							GameState.loadState(GameState.EXIT);
						}
						
						break;
					case KeyEvent.VK_UP:
						currentMenuItem = (currentMenuItem > 0) ?
							currentMenuItem - 1 : lastMenuIndex;
						break;
					case KeyEvent.VK_DOWN:
						currentMenuItem = (currentMenuItem < lastMenuIndex) ?
							currentMenuItem + 1 : 0;
						break;
				}
				
				break;
			case GameState.GAME_PAUSED:
				if (keyCode == KeyEvent.VK_P)
				{
					GameState.switchState(GameState.GAME_PLAY);
				}
				
				break;
			case GameState.GAME_PLAY:
				if (keyCode == KeyEvent.VK_SPACE)
				{
					GamePanel.getPlayer().fireLasers();
				}
				else if (keyCode == KeyEvent.VK_SHIFT)
				{
					GamePanel.getPlayer().fireMissile();
				}
				else if (keyCode == KeyEvent.VK_CONTROL)
				{
					GamePanel.getPlayer().fireBomb();
				}
				else if (keyCode == KeyEvent.VK_P)
				{
					GameState.switchState(GameState.GAME_PAUSED);
				}
				else if (keyCode == KeyEvent.VK_A)
				{
					player.doDodge(true);
				}
				else if (keyCode == KeyEvent.VK_D)
				{
					player.doDodge(false);
				}
				// Hack for skipping to next level
				else if (keyCode == KeyEvent.VK_Y)
				{
					GamePanel.offsetNumLevelPoints(5000);
					GamePanel.offsetNumPoints(GamePanel.getNumLevelPoints());
					GamePanel.setLevelComplete(true);
				}
				
				break;
		}
	}

	/**
	 * This method handles frame advancement.
	 */
	public void run()
	{
		//Get the current time in milliseconds
		long timeMillis = System.currentTimeMillis();

		int i = 0;
		
		//If the current thread is the game thread
		while (Thread.currentThread() == thread)
		{
			//Redraw objects
			repaint();
			
			switch (GameState.getCurrentState())
			{
				case GameState.GAME_PLAY:
					// Keep firing if space is held down
					if (KeyboardEvent.getSpace())
					{
						if (++i >= 20)
						{
							i = 0;
							player.fireLasers();
						}
					}
					else if (KeyboardEvent.getShift())
					{
						if (++i >= 50)
						{
							i = 0;
							player.fireMissile();
						}
					}
					else if (KeyboardEvent.getControl() && ++i >= 100)
					{
						i = 0;
						player.fireBomb();
					}
		
					//Handle Collisions
					CollisionManager.handleCollisions();
					break;
				case GameState.EXIT:
					System.exit(1);
					break;					
			}
			
			//Keep a consistent frame rate
			try
			{
				//Increase the current time in milliseconds by the frame delay
				timeMillis += FRAME_DELAY;

				//Sleep for the frame delay if the thread is not behind schedule
				Thread.sleep(Math.max(0, timeMillis - System
					.currentTimeMillis()));
			}
			//Catch any exceptions
			catch(InterruptedException e)
			{
				break;
			}
		}
	}
}