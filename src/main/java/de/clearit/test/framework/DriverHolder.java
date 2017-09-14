package de.clearit.test.framework;

import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

/**
 * WebDriverHolder
 */
public interface DriverHolder {

	/**
	 * @return Die WinDriver Instanz des Holders
	 */
	WindowsDriver<WebElement> getWinDriver();
}
