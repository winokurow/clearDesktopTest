package de.clearit.test.helper;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import de.clearit.test.common.ScreenshotCreator;
import de.clearit.test.common.TestUtils;
import de.clearit.test.exceptions.AllgemeineTechnischeException;
import de.clearit.test.framework.AllTestListenerAdapters;
import de.clearit.test.framework.DriverCleanUp;
import de.clearit.test.framework.DriverHolder;
import de.clearit.test.framework.ExecutionTimer;
import de.clearit.test.framework.ExecutionTimerManager;
import de.clearit.test.framework.WinPropertyManager;
import de.clearit.test.framework.windowsdriver.WinDriverManager;
import io.appium.java_client.windows.WindowsDriver;

/**
 * <P>
 * Das abstrakte Elternklass für alle Tests.
 * 
 *
 * @implements WinDriverHolder - WinDriver Holder
 * 
 * @author Ilja Winokurow
 */
@Listeners(AllTestListenerAdapters.class)
public class WinTestHelper extends BaseTestHelper implements DriverHolder, DriverCleanUp {

	/** Webdriver Instance. */
	protected WindowsDriver<WebElement> driver;

	/** Alle im test erzeugten Webdriver */
	protected List<WindowsDriver<WebElement>> driversBeingUsedInTest = null;

	/** Die Eigenschaften des Tests. */
	protected WinPropertyManager properties;

	/**
	 * Windriver anhalten
	 */
	protected void stopWinDriver() {
		stopWinDriver(driver);
	}

	protected void stopWinDriver(WindowsDriver<WebElement> driver) {
		WinDriverManager.stopDriver(driver);
		driversBeingUsedInTest.remove(driver);
		this.driver = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WindowsDriver<WebElement> getWinDriver() {
		return driver;
	}

	/**
	 * Zentrale Erzeugung eines neuen Drivers, die Driver instanz darf nur hier
	 * neu zugewiesen werden!
	 *
	 * @param app
	 *            - App Identificator
	 * @throws MalformedURLException
	 */
	protected void erzeugeNeuenDriver(String app) throws MalformedURLException {

		// initialize Execution Timer
		if (ExecutionTimerManager.getExecutionTimer() == null) {
			executionTimer = new ExecutionTimer();
			executionTimer.init(TestUtils.getMethodName());
			ExecutionTimerManager.setExecutionTimer(executionTimer);
		}

		String grid = getProperties().getProperty("appium.url");

		driver = WinDriverManager.createDriver(grid, app);
		driversBeingUsedInTest.add(driver);
	}

	@Override
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	private WinPropertyManager getProperties() {
		if (properties == null) {
			throw new AllgemeineTechnischeException("Properties sind null. Start Test nicht aufgerufen?");
		}
		return properties;
	}

	/**
	 * @param dateiname
	 *            ohne Endung
	 */
	protected void screenshotErzeugen(String dateiname) {
		new ScreenshotCreator().takeScreenshot(getWinDriver(), dateiname);
	}

	@Override
	public void stopWinDrivers() {
		if (driversBeingUsedInTest == null) {
			return;
		}
		List<WindowsDriver<WebElement>> driversToClose = new ArrayList<>(driversBeingUsedInTest);
		for (WindowsDriver<WebElement> webDriverWrapper : driversToClose) {
			stopWinDriver(webDriverWrapper);
		}
		driversBeingUsedInTest.clear();

	}

}
