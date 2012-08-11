import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * This class draws the window and starts the game.
 *
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Game
{
	/**
	 * The main entry point of the program, which creates the window and
	 * initializes everything.
	 *
	 * @param args		Command line arguments (irrelevant)
	 */
	public static void main(String[] args)
	{
		System.out.println("Loading...");
		JFrame game = new JFrame("VERTIGO");
	    GamePanel window = new GamePanel();
		System.out.println("Loading complete!  Enjoy :)");

	    // Get the screen dimensions
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int windowX = (screen.width - Global.WINDOW_WIDTH) >> 1;
	    int windowY = (screen.height - Global.WINDOW_HEIGHT) >> 1;

	    // Set window properties and contents
	    game.setLocation(windowX, windowY);
	    game.setResizable(false);
	    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    game.getContentPane().add(window);
	    game.pack();
	    game.setVisible(true);
	}
}
