package de.clearit.test.framework;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * PropertyManager.
 * 
 * <P>
 * Property manager - notwendig um alle test Eigenschaften zu steuern.
 * 
 * @author Ilja Winokurow
 */
public class WinPropertyManager extends BasisPropertyManager
{

	/** Property manager instance. */
	private static WinPropertyManager instance = null;

	/** Lock Objekt. Notwendig für sync Ausführung. */
	private static final Object lock = new Object();

   /**
    * Constructor.
    * 
    * Wird nie ausgeführt. Um PropertyManager zu initialisieren rufen Method getInstance an.
    */
   protected WinPropertyManager()
   {
   }

	/**
	 * getInstance.
	 * 
	 * PropertyManager Objekt initialisieren.
	 * 
	 * @return PropertyManager Objekt.
	 */
	public static WinPropertyManager getInstance() {
		// if (instance == null)
		// {
		synchronized (lock) {
			if (instance == null) {
				instance = new WinPropertyManager();
				instance.loadProperties();
			}
		}
		// }
		return instance;
	}
   /**
    * loadProperties.
    * 
    * Eigenschaften laden.
    * 
    */
   @Override
   protected void loadProperties()
   {
	   super.loadProperties();
      
      final String umgebung = System.getProperty("umgebung", "local");

      logger.info("Umgebung: " + umgebung);
      loadPropertyFile("/umgebungen" + File.separator + umgebung + ".properties");

      
   }
   

   
   /**
    * loadPropertyFile.
    * 
    * Eigenschaften Datei laden.
    * 
    * @param fileName
    *           - der Name der Datei
    * 
    */
   private void loadPropertyFile(final String fileName)
   {
      final InputStream in = getClass().getResourceAsStream(fileName);
      if (in == null)
      {
         throw new RuntimeException(fileName + " not found");
      }
      try
      {
         defaultProps.load(in);
         in.close();
      }
      catch (final IOException e)
      {
         logger.error("IOException passiert " + e.getMessage());
      }
   }

}
