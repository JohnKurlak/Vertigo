import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class represents a level in the game.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Level
{
	private static final int BLOCKS_PER_X = Global.WINDOW_WIDTH /
		Global.BLOCK_SIZE;
	private static final int BLOCKS_PER_Y = Global.WINDOW_HEIGHT /
		Global.BLOCK_SIZE;
	private static final int MAP_SPEED = 4;
	private String fileName;
	private ArrayList<ArrayList<String>> mapBlocks = new ArrayList<ArrayList
		<String>>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
	private String name;
	private String description;
	private int currentY = 0;
	private int blockYOffset = 0;
	private Image image;
	
	/**
	 * This constructor creates a new level.
	 * 
	 * @param levelFile		The relative file path of the level text file
	 */
	public Level(String levelFile)
	{
		fileName = levelFile.replace(".txt", "");
		image = Utilities.loadImage(fileName + ".png");
		readLevel();
	}
	
	/**
	 * This method returns the preview image for the level.
	 * 
	 * @return	A reference to the preview image for the level
	 */
	public Image getImage()
	{
		return image;
	}
	
	/**
	 * This method returns the name of the level.
	 * 
	 * @return	Returns the name of the level
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * This method returns the description of the level.
	 * 
	 * @return	Returns the description of the level
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * This method gets the filename corresponding to the level without any file
	 * extensions.
	 * 
	 * @return	Returns the filename of the level without any file extensions
	 */
	public String getFileName()
	{
		return fileName;
	}
	
	/**
	 * This method gets the current y position of the level.
	 * 
	 * @return	Returns the current y position of the level
	 */
	public int getCurrentY()
	{
		return currentY;
	}
	
	/**
	 * This method reads and parses a level text file
	 */
	public void readLevel()
	{
		try
		{
			Scanner sc = new Scanner(Utilities.loadFile(fileName + ".txt"));
			
			name = sc.nextLine();
			description = sc.nextLine();
			int numRows = sc.nextInt();
			int numEnemies = sc.nextInt();
			sc.nextLine();
			sc.nextLine();
	
			//Clear the previous map and enemy matrix
			mapBlocks.clear();
			enemies.clear();
			powerUps.clear();
			
			//Add second dimension to array lists
			for(int i = 0; i < numRows; i++)
			{
				mapBlocks.add(new ArrayList<String>());
			}
			
			String mapValue = "";
			int numCols = 0;
			
			for (int rowNum = 0; rowNum < numRows; rowNum++)
			{
				for (int colNum = 0; colNum < BLOCKS_PER_X; colNum++)
				{
					mapValue = sc.next();
					++numCols;
					
					if (numCols == BLOCKS_PER_Y)
					{
						numCols = 0;
					}
					
					mapBlocks.get(rowNum).add(mapValue);
				}
			}
			
			currentY = numRows - BLOCKS_PER_Y;
			
			sc.nextLine();
			sc.nextLine();
			
			for (int enemyNum = 0; enemyNum < numEnemies; enemyNum++)
			{
				String type = sc.next();
				String xFunction = sc.next();
				String yFunction = sc.next();
				int x = sc.nextInt();
				int yBlock = sc.nextInt() + BLOCKS_PER_Y;
				int yOffset = sc.nextInt();
				int health = sc.nextInt();
				int fireInterval = sc.nextInt();
				
				if (type.equals("02"))
				{
					ExtraBomb current = new ExtraBomb(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 1, xFunction,
						yFunction);
					
					powerUps.add(current);
					GamePanel.addSprite(current);
				}
				else if (type.equals("03"))
				{
					ExtraMissile current = new ExtraMissile(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 1, xFunction,
						yFunction);
					
					powerUps.add(current);
					GamePanel.addSprite(current);					
				}
				else if (type.equals("05"))
				{
					ExtraHealth current = new ExtraHealth(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 1, xFunction,
						yFunction);
					
					powerUps.add(current);
					GamePanel.addSprite(current);	
				}
				else if (type.equals("06"))
				{
					ExtraLife current = new ExtraLife(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 1, xFunction,
						yFunction);
					
					powerUps.add(current);
					GamePanel.addSprite(current);	
				}
				else if (type.equals("07"))
				{
					Enemy current = new Enemy(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 0, xFunction,
						yFunction, health, fireInterval, 0, type);
				
					enemies.add(current);
					GamePanel.addSprite(current);
				}
				else 
				{
					Enemy current = new Enemy(x, yBlock, yOffset,
						LevelManager.SPRITE_MAPPINGS.get(type), 0, 0, xFunction,
						yFunction, health, fireInterval,
						LevelManager.ENEMY_CRASH_VALS.get(type), type);
				
					enemies.add(current);
					GamePanel.addSprite(current);
				}
			}
			
			sc.close();
			GamePanel.setLevelComplete(false);
		}
		catch (FileNotFoundException e)
		{
			System.out.println("a" + e.getMessage());
			System.exit(1);
		}				
	}
	
	/**
	 * This method draws the current level, including its enemies and power-ups
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	public void draw(Graphics g)
	{	
		drawMap(g);
		drawEnemies(g);
		drawPowerUps(g);
	}
	
	/**
	 * This method draws the enemies onto the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	private void drawEnemies(Graphics g)
	{
		ArrayList<Sprite> sprites = GamePanel.getSprites();
		
		for (int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i) instanceof Enemy)
			{
				Enemy enemy = (Enemy) sprites.get(i);
				
				int currentYTop = currentY + BLOCKS_PER_Y - 1;
				boolean visibleX = enemy.getX() + enemy.getWidth() >= 0 && enemy
					.getX() <= Global.WINDOW_WIDTH;
				boolean yOnScreen = enemy.getY() <= Global.WINDOW_HEIGHT + 100;
				boolean visibleY = enemy.getYBlock() >= currentYTop &&
					yOnScreen;
				
				if (visibleX && visibleY && enemy.isAlive())
				{
					enemy.draw(g);
				}
				else if (!visibleX || !yOnScreen)
				{
					enemy.remove();
				}
			}
		}
	}
	
	/**
	 * This method draws the power-ups onto the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	private void drawPowerUps(Graphics g)
	{
		ArrayList<Sprite> sprites = GamePanel.getSprites();
		
		for (int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i) instanceof PowerUp)
			{
				PowerUp powerUp = (PowerUp) sprites.get(i);
				
				int currentYTop = currentY + BLOCKS_PER_Y - 1;
				boolean visibleX = powerUp.getX() + powerUp.getWidth() >= 0 &&
					powerUp.getX() <= Global.WINDOW_WIDTH;
				boolean yOnScreen = powerUp.getY() <= Global.WINDOW_HEIGHT +
					100;
				boolean visibleY = powerUp.getYBlock() >= currentYTop &&
					yOnScreen;
				
				if (visibleX && visibleY && powerUp.isAlive())
				{
					powerUp.draw(g);
				}
				else if (!yOnScreen)
				{
					powerUp.remove();
				}
			}
		}
	}
	
	/**
	 * This method draws the map onto the screen.
	 * 
	 * @param g		The graphics object to which we can draw
	 */
	private void drawMap(Graphics g)
	{
		int drawX = 0;
		int drawY = -Global.BLOCK_SIZE;
		
		for (int row = currentY - 1; row <= currentY + BLOCKS_PER_Y; row++)
		{
			drawX = 0;
			
			for (int col = 0; col < BLOCKS_PER_X; col++)
			{
				Image block = null;
				
				if (row >= 0 && row < mapBlocks.size())
				{
					block = LevelManager.BLOCK_MAPPINGS.get(mapBlocks.get(row)
						.get(col));
				}
				
				g.drawImage(block, drawX, drawY + blockYOffset, null);

				drawX += Global.BLOCK_SIZE;	
			}
			
			drawY += Global.BLOCK_SIZE;
		}
		
		// Don't move the map if the game is paused
		if (currentY > 0 && GameState.getCurrentState() != GameState
			.GAME_PAUSED)
		{
			blockYOffset += MAP_SPEED;
		}
		// Level done
		else if (!GamePanel.isLevelComplete() && currentY == 0 && GamePanel
			.getPlayer().isAlive() && !GameState.isLoading())
		{
			GamePanel.offsetNumLevelPoints(5000);
			GamePanel.offsetNumPoints(GamePanel.getNumLevelPoints());
			GamePanel.setLevelComplete(true);
		}
		
		if (blockYOffset == Global.BLOCK_SIZE)
		{
			blockYOffset = 0;
			currentY--;
		}	
	}
}