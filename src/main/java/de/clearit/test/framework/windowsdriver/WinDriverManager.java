package de.clearit.test.framework.windowsdriver;

import java.net.MalformedURLException;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

/**
 * Windriver Instance steuern.
 */
public final class WinDriverManager {

	private static String DEFAULT_VALUE = "false";

	/** Logger */
	private static final Logger logger = Logger.getLogger("WinDriverManager");

	/**
	 * Constructor.
	 * 
	 * Private constructor to hide the implicit public one
	 */
	private WinDriverManager() {
	}

	/**
	 * Start WinDriver.
	 * 
	 * @param appium
	 *            - Appium URL
	 * @param appIdentifier
	 *            - app Identifier
	 * @return den gestarteten WinDriver
	 * @throws MalformedURLException
	 * 
	 */
	public static WindowsDriver<WebElement> createDriver(String appiumUrl, String appIdentifier)
			throws MalformedURLException {
		WindowsDriver<WebElement> driver = startDriver(appIdentifier, appiumUrl);
		logger.info("App (" + appIdentifier + ") ");
		return driver;
	}

	/**
	 * Stoppt den WinDriver
	 * 
	 * @param winDriver
	 */
	public static void stopDriver(WindowsDriver<WebElement> driver) {
		try {
			stopDriverOrFail(driver);
		} catch (Exception e) {
			logger.error("Fehler beim stoppen des WinDrivers: " + e.getMessage(), e);
		}
	}

	private static WindowsDriver<WebElement> startDriver(String app, String appiumUrl) throws MalformedURLException {
		Validate.notEmpty(app, "App Indeficator ist nicht gesetzt.");

		return (WindowsDriver<WebElement>) WinDriverCreator.createWinDriver(app, appiumUrl);
	}

	private static void stopDriverOrFail(WindowsDriver<WebElement> driver) {
		driver.quit();
		loggeBrowserSchliessenWennMoeglich(driver);
	}

	private static void loggeBrowserSchliessenWennMoeglich(WindowsDriver<WebElement> driver) {
		if (driver instanceof WindowsDriver) {
			logger.info("App ist geschlossen.");
		}
	}

}
