import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class contains various utility methods that can be used by all of the
 * classes.
 *
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Utilities
{
	/**
	 * This corresponds to the delimiter of the file path for the current
	 * operating system.
	 */
	public static String slash = (System.getProperty("os.name")
		.indexOf("Windows") != -1) ? "\\" : "/";

	/**
	 * This method loads an image from a relative file path.
	 *
	 * @param relativeFilePath	The relative file path
	 * @return	Returns a reference to the image corresponding to the given
	 * 			file path.
	 */
	public static Image loadImage(String relativeFilePath)
	{
		BufferedImage img;

		try
		{
		    img = ImageIO.read(Utilities.loadFile(relativeFilePath));

		    return img;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);

			return null;
		}
	}

	/**
	 * This method loads a file from a relative file path.
	 *
	 * @param relativeFilePath	The relative file path
	 * @return	Returns a reference to the file corresponding to the given
	 * 			file path.
	 */
	public static File loadFile(String relativeFilePath)
	{
		String filePath = Utilities.getAppPath() + "includes" + slash +
			relativeFilePath;

		return new File(filePath);
	}

	/**
	 * This method loads a font from a relative file path.
	 *
	 * @param relativeFilePath	The relative file path
	 * @return	Returns a reference to the font corresponding to the given
	 * 			file path.
	 */
	public static Font loadFont(String relativeFilePath)
	{
		File fontFile = loadFile(relativeFilePath);

		try
		{
			FileInputStream fontStream = new FileInputStream(fontFile);

			try
			{
				Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
				return font;
			}
			catch (Exception e)
			{
				// Not needed
			}
		}
		catch (FileNotFoundException e)
		{
			// We will assume there is no corruption of our installation
		}

		return new Font("sansserif", Font.PLAIN, 10);
	}

	/**
	 * This method gets the path of the running application.
	 *
	 * @return	Returns the directory of the current application
	 */
	private static String getAppPath()
	{
		File currentDirectory = new File(".");

		try
		{
			return currentDirectory.getCanonicalPath() + slash;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			System.exit(1);

			return "";
		}
	}
}
