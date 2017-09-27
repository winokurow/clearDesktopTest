package de.clearit.test.helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import de.clearit.test.exceptions.ElementIsEnabledException;
import de.clearit.test.framework.WinPropertyManager;
import io.appium.java_client.windows.WindowsDriver;

/**
 * Hilfsmethoden, die WebDriver erweitern.
 * 
 * @author Ilja Winokurow
 */
public class WinDriverUtils {

	/* Logger */
	public static Logger logger = Logger.getLogger("WindriverUtils");

	/**
	 *
	 * Warten bis Element deaktiviert wird.
	 *
	 * @param element
	 *            -WebElement
	 */
	public static void waitForDisable(WindowsDriver<WebElement> driver, final WebElement element) {

		long startTime = System.currentTimeMillis();
		long timeout = Long.parseLong(WinPropertyManager.getInstance().getProperty("windriver.timeout"));
		boolean isDisabled = false;
		while (System.currentTimeMillis() - startTime < timeout)
			if (!(element.isEnabled())) {
				isDisabled = true;
				break;
			}
		if (!(isDisabled)) {
			throw new ElementIsEnabledException();
		}

	}
}
