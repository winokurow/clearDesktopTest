package de.clearit.test.framework;

import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

/**
 * DriverInjectable
 */
public interface DriverInjectable {
	/**
	 * @param driver
	 *            - der verwendet werden soll
	 */
	void setDriver(WindowsDriver<WebElement> driver);
}
