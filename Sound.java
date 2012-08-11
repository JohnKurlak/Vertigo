import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/**
 * This class represents a sound that can be played.
 * 
 * @author		John Kurlak		(kurlak)
 *				Weston Thayer	(weston5)
 *				Panhavorn Hok	(onehok1)
 * @version		12.07.09
 */
public class Sound implements Runnable
{
	private Thread thread;
	private static final int BUFFER_SIZE = 128000;
	private SourceDataLine dataLine;
	private AudioInputStream stream;
	private AudioFormat format;
	private File file;
	private boolean alive = true;
	private String path;
	private boolean loop = true;
	
	/**
	 * This constructor creates a new sound that can be played.
	 * 
	 * @param relativePath		The relative file path of the sound
	 */
	public Sound(String relativePath)
	{
		path = relativePath;
		file = Utilities.loadFile(path);
	}
	
	/**
	 * This method prepares the stream of the sound for reading.
	 */
	private void prepareStream()
	{
		try
		{
			stream = AudioSystem.getAudioInputStream(file);
		}
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
        
		format = stream.getFormat();
    	
        if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED)
    	{
        	format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format
        		.getSampleRate(), format.getSampleSizeInBits() << 1, format
        		.getChannels(), format.getFrameSize() << 1, format
        		.getFrameRate(), true);
    		stream = AudioSystem.getAudioInputStream(format, stream);
    	}
	}
	
	/**
	 * This method prepares the data line of the sound.
	 */
	private void prepareDataLine()
	{
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, stream
        	.getFormat(), ((int) stream.getFrameLength() *
        	format.getFrameSize()));
        
        try
		{
			dataLine = (SourceDataLine) AudioSystem.getLine(info);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
        
        try
		{
			dataLine.open(stream.getFormat());
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * This method stops the sound from playing.
	 */
	@SuppressWarnings("deprecation")
	public void stop()
	{
		thread.stop();
		thread = null;
	}
	
	/**
	 * This method plays the sound once.
	 */
	public void playOnce()
	{
		loop = false;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * This method loops the sound.
	 */
	public void play()
	{
		loop = true;
		thread = new Thread(this);
		thread.start();
    }
	
	/**
	 * This method plays the sound in its own thread.
	 */
	public void run()
	{
		while (alive)
		{
			prepareStream();
			prepareDataLine();
			dataLine.start();
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int numBytes = 0;
	        
			try
			{
				numBytes = stream.read(buffer, 0, BUFFER_SIZE);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
	        while (numBytes !=-1 && Thread.currentThread() == thread)
	        {
	            if (numBytes > 0)
	            {
	                dataLine.write(buffer, 0, numBytes);
	            }
	            
	            try
				{
	            	numBytes = stream.read(buffer, 0, BUFFER_SIZE);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
	        }
	        
	        dataLine.drain();
	        dataLine.close();
	        
	        if (!loop)
	        {
	        	alive = false;
	        }
		}
	}
	
	/**
	 * This method gives a string representation of the sound.
	 * 
	 * @return	Returns the sound's path
	 */
	public String toString()
	{
		return path;
	}
}