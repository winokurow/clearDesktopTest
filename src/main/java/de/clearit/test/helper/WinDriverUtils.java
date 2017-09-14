package de.clearit.test.helper;

import org.apache.log4j.Logger;

/**
 * Hilfsmethoden, die WebDriver erweitern.
 * 
 * @author Ilja Winokurow
 */
public class WinDriverUtils {

	/* Logger */
	public static Logger logger = Logger.getLogger("WindriverUtils");

	// /**
	// *
	// * Warten bis Element deaktiviert wird.
	// *
	// * @param element
	// * -WebElement
	// */
	// public static void waitForDisable(WindowsDriver driver, final WebElement
	// element) {
	// final WebDriverWait wait = new WebDriverWait(driver,
	// Long.parseLong(WinPropertyManager.getInstance().getProperty("windriver.timeout")));
	// wait.until(new ExpectedCondition<Boolean>() {
	// @Override
	// public Boolean apply(WindowsDriver driver) {
	// String enabled = "";
	// if (element.getAttribute("disabled") != null) {
	// enabled = element.getAttribute("disabled");
	// }
	// if (element.getAttribute("aria-disabled") != null) {
	// enabled = element.getAttribute("aria-disabled");
	// }
	// return enabled.equals("true");
	// }
	// });
	// }
}
