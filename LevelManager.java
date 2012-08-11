import java.awt.Image;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class manages global things related to loading and playing levels.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class LevelManager
{
	private static ArrayList<String> levelFiles = new ArrayList<String>();
	private static int currentLevelIndex = 0;
	
	/**
	 * This hash map maps hexadecimal block codes to their corresponding images.
	 */
	public static final HashMap<String, Image> BLOCK_MAPPINGS = new HashMap
		<String, Image>();
	static
	{	
		for (int i = 0; i <= 647; i++)
		{
			String hex = toHex(i);
			
			BLOCK_MAPPINGS.put(hex, Utilities.loadImage("tiles" + Utilities
				.slash + hex + ".bmp"));
		}
	}
	
	/**
	 * This hash map maps hexadecimal enemy and power-up codes to their
	 * corresponding images.
	 */
	public static final HashMap<String, Image[]> SPRITE_MAPPINGS = new HashMap
		<String, Image[]>();
	static
	{
		SPRITE_MAPPINGS.put("00",  new Image[] { Utilities
			.loadImage("enemy1.png"), Utilities.loadImage("enemy1-hit.png"),
			Utilities.loadImage("enemy1.png") });
		SPRITE_MAPPINGS.put("01",  new Image[] { Utilities
			.loadImage("boss1.png"), Utilities.loadImage("boss1-hit.png"),
			Utilities.loadImage("boss1.png") });
		SPRITE_MAPPINGS.put("02",  new Image[] { Utilities
			.loadImage("bomb-pickup.png"),
			Utilities.loadImage("bomb-pickup-light.png") });
		SPRITE_MAPPINGS.put("03",  new Image[] { Utilities
			.loadImage("missile-pickup.png"),
			Utilities.loadImage("missile-pickup-light.png") });
		SPRITE_MAPPINGS.put("04",  new Image[] { Utilities
			.loadImage("enemy2.png"), Utilities.loadImage("enemy2-hit.png"),
			Utilities.loadImage("enemy2.png") });
		SPRITE_MAPPINGS.put("05",  new Image[] { Utilities
			.loadImage("health-pickup.png"),
			Utilities.loadImage("health-pickup-light.png") });
		SPRITE_MAPPINGS.put("06",  new Image[] { Utilities
			.loadImage("life-pickup.png"),
			Utilities.loadImage("life-pickup-light.png") });
		SPRITE_MAPPINGS.put("07",  new Image[] { Utilities
			.loadImage("turret.png"), Utilities.loadImage("turret-hit.png"),
			Utilities.loadImage("turret.png") });
		SPRITE_MAPPINGS.put("08",  new Image[] { Utilities
			.loadImage("enemy3.png"), Utilities.loadImage("enemy3-hit.png"),
			Utilities.loadImage("enemy3.png") });
	}
	
	/**
	 * This hash map maps hexadecimal enemy codes to their corresponding crash
	 * damage amounts.
	 */
	public static final HashMap<String, Integer> ENEMY_CRASH_VALS = new
		HashMap<String, Integer>();
	static
	{
		ENEMY_CRASH_VALS.put("00",  10);
		ENEMY_CRASH_VALS.put("01",  30);
		ENEMY_CRASH_VALS.put("04",  30);
		ENEMY_CRASH_VALS.put("08",  10);
	}
	
	/**
	 * This method reads the level file so that it can keep track of what levels
	 * are in the game and what their order is.
	 */
	public static void readLevelFile()
	{
		try
		{
			Scanner sc = new Scanner(Utilities.loadFile("levels.txt"));
			
			while (sc.hasNext())
			{
				levelFiles.add(sc.nextLine());
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("b" + e.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * This method converts a base-10 number to hexadecimal.
	 */
	private static String toHex(int num)
	{
		String hex = Integer.toHexString(num) + "";
		hex = hex.toUpperCase();
		
		if (hex.length() == 1)
		{
			return "00" + hex;
		}
		else if (hex.length() == 2)
		{
			return "0" + hex;
		}
		
		return hex;
	}
	
	/**
	 * This method gets the index of the current level.
	 * 
	 * @return	Returns the index of the current level
	 */
	public static int getCurrentLevel()
	{
		return currentLevelIndex;
	}
	
	/**
	 * This method sets the current level to the first one.
	 */
	public static void reset()
	{
		currentLevelIndex = 0;
	}
	
	/**
	 * This method advances the level.
	 */
	public static void advanceLevel()
	{
		if (currentLevelIndex < levelFiles.size() - 1)
		{
			currentLevelIndex++;	
		}
		else
		{
			currentLevelIndex = -1;
		}
	}
	
	/**
	 * This method gets the filename corresponding to the given level index.
	 * 
	 * @param levelIndex	The index of the level for which we want the
	 * 						filename
	 * @return	Returns the filename corresponding to the given level index
	 */
	public static String getLevelFile(int levelIndex)
	{
		return levelFiles.get(levelIndex);
	}
	
	/**
	 * This method starts a level.
	 * 
	 * @return	Returns a reference to the Level object corresponding to the
	 * 			level that was just started.
	 */
	public static Level startLevel()
	{
		if (currentLevelIndex != -1)
		{
			return new Level(levelFiles.get(currentLevelIndex));
		}
		
		return null;
	}
}
