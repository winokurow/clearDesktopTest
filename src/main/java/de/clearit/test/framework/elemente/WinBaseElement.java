package de.clearit.test.framework.elemente;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.clearit.test.common.TestUtils;
import de.clearit.test.data.LocatorTyp;
import de.clearit.test.exceptions.DriverNotSetException;
import de.clearit.test.framework.DriverInjectable;
import de.clearit.test.framework.WinPropertyManager;
import de.clearit.test.helper.WinDriverUtils;
import io.appium.java_client.windows.WindowsDriver;

/**
 * Framework GUI Element.
 *
 * @author Ilja Winokurow
 */
public class WinBaseElement implements DriverInjectable {

	private static final Logger logger = Logger.getLogger("WinBaseElement Klass");

	protected static final long TIMEOUT_IN_SECONDS_DEFAULT = 30;

	public static final int DEFAULT_VERZOEGERUNG_IN_MILLISEKUNDEN = 100;

	/* WebElement */
	protected WebElement element;

	/* WebDriver */
	protected WindowsDriver<WebElement> driver;

	/* Element locator */
	protected String locator;

	/* Eine Beschreibung */
	protected String description;

	/* Element finding timeout */
	protected long timeOutInSeconds = TIMEOUT_IN_SECONDS_DEFAULT;

	/* Element locator */
	protected LocatorTyp locatorTyp;

	/**
	 * Constructor.
	 *
	 * @param locator
	 *            - Locator
	 * @param locatorTyp
	 *            - Locator Typ
	 */
	public WinBaseElement(final String locator, LocatorTyp locatorTyp) {
		this(locator, locatorTyp, null);

	}

	/**
	 * Constructor.
	 *
	 * @param locator
	 *            - Locator
	 * @param locatorTyp
	 *            - Locator Typ
	 * @param driver
	 *            - Driver instance to use.
	 */
	public WinBaseElement(final String locator, LocatorTyp locatorTyp, final WindowsDriver<WebElement> driver) {
		this(locator, locatorTyp, driver, Long.parseLong(WinPropertyManager.getInstance()
				.getProperty("win.element.timeout", String.valueOf(TIMEOUT_IN_SECONDS_DEFAULT))));
	}

	/**
	 * Constructor.
	 *
	 * @param locator
	 *            - Locator
	 * @param locatorTyp
	 *            - Locator Typ
	 * @param driver
	 *            - Driver instance to use.
	 * @param timeOutInSeconds
	 *            - Driver timeout for this element.
	 */
	public WinBaseElement(final String locator, LocatorTyp locatorTyp, final WindowsDriver<WebElement> driver,
			final long timeOutInSeconds) {
		this();
		this.locator = locator;
		this.locatorTyp = locatorTyp;
		this.driver = driver;
		this.timeOutInSeconds = timeOutInSeconds;
	}

	/**
	 * Constructor.
	 *
	 * Default constructor ohne Parameters ist verboten.
	 *
	 */
	private WinBaseElement() {
	}

	/**
	 * Die Beschreibung zurückliefern.
	 *
	 * return die Beschreibung
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * isPresent
	 * <p>
	 *
	 * Is element present.
	 *
	 * @return is element present
	 *
	 */
	public boolean isPresent() {
		return !findElements().isEmpty();
	}

	/**
	 * waitForVisible
	 *
	 * Warten bis Element angezeigt ist.
	 *
	 */
	public void waitForVisible() {
		waitForVisible(timeOutInSeconds);
	}

	/**
	 * waitForVisible
	 *
	 * Warten bis Element angezeigt ist.
	 *
	 * @param timeOutInSeconds
	 *            -wie lange wird gewartet bis Element sichtbar wird
	 */
	public void waitForVisible(Long timeOutInSeconds) {
		checkElement();
		final WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for element is present.
	 */
	public void waitForPresent() {
		checkElement();
	}

	/**
	 * waitForEnable
	 * <p>
	 *
	 * Wait for element is enable.
	 */
	public void waitForEnable() {
		waitForPresent();
		Long timeout = timeOutInSeconds;
		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * waitForDisable
	 *
	 * Warten bis Element deaktiviert wird.
	 *
	 */
	public void waitForDisable() {
		waitForPresent();
		WinDriverUtils.waitForDisable(driver, element);
	}

	/**
	 * Das Element betätigen.
	 */
	public void click() {
		checkElement();
		waitForVisible();
		element.click();
	}

	/**
	 * Text hinzufügen in ein Textfeld.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 */
	public void sendkeys(final CharSequence... text) {
		if (text != null) {
			checkElement();
			waitForVisible();
			element.sendKeys(text);
		}
	}

	/**
	 * Text hinzufügen in ein Textfeld.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 */
	public void typeWithoutClear(final String text) {
		checkElement();
		element.sendKeys(text);
	}

	/**
	 * Text hinzufügen in ein Textfeld.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 */
	public void typeSlowWithoutClear(final String text) {
		typeSlowWithoutClear(text, 50);
	}

	/**
	 * Text hinzufügen in ein Textfeld.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 */
	public void typeSlowWithoutClear(final String text, int sleepInMillis) {
		long time = System.currentTimeMillis();
		waitForEnable();
		for (String s : text.split("")) {
			element = findElement();
			element.sendKeys(s);
			TestUtils.sleep(sleepInMillis);
		}
		logWennMehrAlsEineSekunde(time);
	}

	private void logWennMehrAlsEineSekunde(long time) {
		long sekunden = ((System.currentTimeMillis() - time)) / 1000;
		if (sekunden > 0) {
			logger.info(String.format("Langsame Eingabe dauerte %s Sekunden", sekunden + ""));
		}
	}

	/**
	 * Inhalt eines Feldes löschen. <br>
	 * Text langsam (eine Pause zwischen jeder Buchstabe) im Textfeld
	 * hinzufügen. <br>
	 * Danach wird gewartet bis Seite geladen wird.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 * @param sleepInMillis
	 *            - die Pause zwischen Inhalt löschen und Inhalt eingeben
	 *            (Millisekunden)
	 * @param waitForPageLoaded
	 *            - ob nach Seite Laden gewartet soll
	 * @param waitForPageLoadedSeconds
	 *            - wie lange wird es gewartet, bis Seite geladen wird
	 *            (Sekunden)
	 */
	private void typeSlowWithClearAndWithWaitForPage(final String text, int sleepInMillis, boolean waitForPageLoaded,
			int waitForPageLoadedSeconds) {
		waitForEnable();
		element.click();
		clearInput();
		TestUtils.sleep(sleepInMillis);
		for (String s : text.split("")) {
			element = findElement();
			element.sendKeys(s);
		}
	}

	/**
	 * Text in ein Textfeld langsam hinzufügen (0.5 sek. Pause zwischen
	 * Buchstaben) und die Taste 'Enter' betätigen.
	 *
	 * @param text
	 *            - Text zu hinzufügen
	 */
	public void typeSlowWithClearAndWithEnter(final String text) {
		typeSlowWithClearAndWithWaitForPage(text, 500, false, 0);
		click();
		element.sendKeys(Keys.ENTER);

	}

	/**
	 * Eingabefeld leeren mit ganz vielen DELETE und BackSpace
	 *
	 */
	public void clearInput() {
		clearInput(50);
	}

	/**
	 * Eingabefeld leeren mit ganz vielen DELETE und BackSpace
	 *
	 * @param anzahlBackspaceUndDelete
	 *            Anzahl der Backspace und Delete Tastendrücke
	 * @return this GuiElement für Builder Pattern
	 */
	public WinBaseElement clearInput(int anzahlBackspaceUndDelete) {
		checkElement();
		element.sendKeys(createArray(Keys.BACK_SPACE, anzahlBackspaceUndDelete));
		element.sendKeys(createArray(Keys.DELETE, anzahlBackspaceUndDelete));
		return this;
	}

	/**
	 * Zur Nutzung in sendKeys, deutlich schneller als die Keys einzeln zu
	 * senden!
	 * 
	 * @param key
	 * @param keyAnzahl
	 * @return Keys[]
	 */
	private Keys[] createArray(Keys key, int keyAnzahl) {
		List<Keys> keys = new ArrayList<>();
		for (int i = 0; i < keyAnzahl; i++) {
			keys.add(key);
		}
		return keys.toArray(new Keys[0]);
	}

	/**
	 * Get value.
	 *
	 * return den ausgelesenen Wert
	 */
	public String getValue() {
		checkElement();
		waitForVisible();
		return element.getAttribute("value");
	}

	/**
	 * Mouse over.
	 */
	public void mouseOverClick() {
		checkElement();
		final Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}

	/**
	 * Get Element Text.
	 *
	 * @return Elements Text
	 */
	public String getText() {
		checkElement();
		String value = "";
		if (this.isVisible()) {
			value = element.getText();
		}
		waitForPresent();
		//
		return value;
	}

	/**
	 * Get Element Text.
	 *
	 * @return Elements Text or null if error happens
	 */
	public String getTextOrNull() {
		try {
			return getText();
		} catch (Exception e) {
			logger.debug(e);
			return null;
		}
	}

	/**
	 * If Element visible.
	 *
	 */
	public boolean isVisible() {
		checkElement();
		return element.isDisplayed();
	}

	/**
	 * If Element enabled.
	 *
	 */
	public boolean isEnabled() {
		checkElement();
		waitForVisible();
		return element.isEnabled();
	}

	/**
	 * If Element read only und disabled.
	 *
	 */
	public boolean isReadOnlyAndDisable() {
		checkElement();

		boolean result = (this.getAttribute("disabled") != null) && (this.getAttribute("readonly") != null);

		return result;
	}

	/**
	 * If element is disabled;
	 */
	public boolean isDisabled() {
		return !isEnabled();
	}

	public boolean isDisabledAttribute() {
		checkElement();

		boolean result = this.getAttribute("disabled") != null;

		return result;
	}

	/**
	 * Get Attribute Value.
	 *
	 * @param attributeName
	 *            - attribute name
	 * @return attribute value
	 *
	 */
	public String getAttribute(final String attributeName) {
		checkElement();
		return element.getAttribute(attributeName);
	}

	/**
	 * Zum Element Scrollen
	 */
	public void scrollToElement() {
		checkElement();
		try {
			scrollToElementAction();
		} catch (StaleElementReferenceException e) {
			findAndSetElement();
			scrollToElementAction();
		}
	}

	private void scrollToElementAction() {
		new Actions(driver).moveToElement(element).build().perform();
	}

	/**
	 * @return the driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 * @return die Instanz des GuiElements
	 */
	public WinBaseElement withDriver(final WindowsDriver<WebElement> driver) {
		setDriver(driver);
		return this;
	}

	/**
	 * @return Returns the element.
	 */
	public WebElement getElement() {
		return element;
	}

	private void findAndSetElement() {
		element = findElement();
	}

	/**
	 * Initialize an element.
	 */
	protected void checkElement() {
		if (driver == null) {
			throw new DriverNotSetException();
		}

		if (element == null) {
			findAndSetElement();
		}
	}

	/**
	 * Verfassen eine Fehlermeldung in der Fall wenn, ein Element nicht
	 * gefunden.
	 * 
	 * @return die erzeugte Fehlermeldung
	 */
	protected String erzeugeErrorMessageFuerElementNichtGefunden() {
		String message = "Element nicht gefunden '" + locator + "'.";
		return message;
	}

	@Override
	public void setDriver(WindowsDriver<WebElement> driver) {
		this.driver = driver;

	}

	/**
	 * Element rausfinden
	 */
	protected WebElement findElement() {
		switch (locatorTyp) {
		case ACCESSIBILITY_ID:
			return driver.findElementByAccessibilityId(locator);
		case NAME:
			return driver.findElementByName(locator);
		case XPATH:
			return driver.findElementByXPath(locator);
		case UI_AUTOMATION:
			return driver.findElementByWindowsUIAutomation(locator);
		case ID:
			return driver.findElementById(locator);
		default:
			return driver.findElementByName(locator);
		}
	}

	/**
	 * Element rausfinden
	 */
	protected List<WebElement> findElements() {
		switch (locatorTyp) {
		case ACCESSIBILITY_ID:
			return driver.findElementsByAccessibilityId(locator);
		case NAME:
			return driver.findElementsByName(locator);
		case XPATH:
			return driver.findElementsByXPath(locator);
		case UI_AUTOMATION:
			return driver.findElementsByWindowsUIAutomation(locator);
		case ID:
			return driver.findElementsById(locator);
		default:
			return driver.findElementsByName(locator);
		}
	}

}
