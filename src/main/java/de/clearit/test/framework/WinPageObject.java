package de.clearit.test.framework;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import io.appium.java_client.windows.WindowsDriver;

/**
 * Framework Page Object.
 *
 * @author Ilja Winokurow
 */
public class WinPageObject extends BasisPageObject {

	/* WebDriver */
	protected WindowsDriver<WebElement> driver;

	public WinPageObject(WindowsDriver<WebElement> driver) {
		this.driver = driver;
	}

	/**
	 * Warten bis alle Elemente, die mit dem Annotation Check markiert sind,
	 * angezeigt werden. Ausserdem werden in den GuiElementen die WebDriver
	 * gesetzt.
	 */
	public void waitForMainElementsIsShown() {
		waitForMainElementsIsShown(-1);
	}

	/**
	 * Warten bis alle Elemente, die mit dem Annotation Check markiert sind,
	 * angezeigt werden. Ausserdem werden in den GuiElementen die WebDriver
	 * gesetzt.
	 * 
	 * @param timeoutInSeconds
	 *            - das Timeout (in Sekunden)
	 */
	public void waitForMainElementsIsShown(long timeoutInSeconds) {

		// logErrorMessageBoxWennGefundenUndFailWennTechnischerFehlerMitID();
		// checkForMaskenTitle();
		setWebDriverOnAllGuiElementsAndWaitForVisibleIfWanted(true);

		// ExecutionTimerManager.getExecutionTimer().end(title);
	}

	/**
	 * Driver für alle GuiElementen setzen und überprüfen wenn notwendig
	 * (Annotation Check)
	 *
	 * @param waitForVisible
	 *            - ob Elemente auf Sichtbarkeit geprüft werden
	 */
	private void setWebDriverOnAllGuiElementsAndWaitForVisibleIfWanted(boolean waitForVisible) {
		final List<Field> fields = getAllFields(new ArrayList<Field>(), this.getClass());
		try {
			for (final Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(this);
				if (value instanceof DriverInjectable) {
					DriverInjectable webDriverVerwender = (DriverInjectable) value;
					webDriverVerwender.setDriver(driver);
				}
				// if (value instanceof WinBaseElement) {
				// WinBaseElement guielement = (WinBaseElement) value;
				// if (field.isAnnotationPresent(Check.class) && waitForVisible)
				// {
				// guielement.waitForVisible();
				// }
				// }

			}
		} catch (final IllegalAccessException e) {
			logger.error(e);
		}
	}

	/**
	 * Eine Fehlermeldung ausgeben wenn angezeigt
	 */
	private void logErrorMessageBoxWennGefundenUndFailWennTechnischerFehlerMitID() {
		String errorTextAufMaske = ErrorBoxUtil.holeAktuellenErrorText(driver, 1L);
		if (!StringUtils.isEmpty(errorTextAufMaske)) {
			logger.info(StringUtils.CR + StringUtils.CR
					+ "**************************************************************************************"
					+ StringUtils.CR + "Error Message Box auf der Seite hat folgendes Ausgegeben:" + StringUtils.CR
					+ StringUtils.CR + errorTextAufMaske + StringUtils.CR + StringUtils.CR
					+ "**************************************************************************************"
					+ StringUtils.CR);

			FataleFehlerWerfer.pruefeFehlerTextUndFailWennFatal(errorTextAufMaske);
		}
	}

	/**
	 * Get all members
	 *
	 * @param fields
	 *            - initial list of fields
	 * @param type
	 *            - class
	 * @return list of members
	 */
	private static List<Field> getAllFields(List<Field> fields, final Class<?> type) {
		List<Field> tempFields = new ArrayList<>();
		tempFields.addAll(fields);
		tempFields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			tempFields = getAllFields(tempFields, type.getSuperclass());
		}

		return tempFields;
	}

	/**
	 * getTitle.
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
}
