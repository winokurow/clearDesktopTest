package de.clearit.test.framework.windowsdriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.windows.WindowsDriver;

/**
 * Creator für WinDriver (WinDriver initialisieren).
 */
public final class WinDriverCreator {

	/**
	 * Constructor.
	 * 
	 * Private constructor to hide the implicit public one
	 */
	private WinDriverCreator() {
	}

	/**
	 * Create WinDriver.
	 * 
	 * @param appIdentifier
	 *            - app Identifier
	 * @param appiumUrl
	 *            - Appium Url
	 * 
	 * @return das Treiber
	 * @throws MalformedURLException
	 */
	protected static WindowsDriver<WebElement> createWinDriver(String appIdentifier, final String appiumUrl)
			throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("app", appIdentifier);
		RemoteWebDriver driver = new WindowsDriver<WebElement>(new URL(appiumUrl), capabilities);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		return (WindowsDriver<WebElement>) driver;
	}

}
