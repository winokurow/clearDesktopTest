package de.clearit.test.framework.listener;

import org.testng.ITestResult;

import de.clearit.test.framework.DriverCleanUp;
import de.clearit.test.framework.WinPropertyManager;

public class DriverCleanUpOnTestFinished extends BaseProjectTestListenerAdapter {

	@Override
	public void onTestFailure(final ITestResult tr) {
		final boolean remote = Boolean.parseBoolean(System.getProperty("remote", "false"));
		final boolean jenkins = Boolean.parseBoolean(System.getProperty("jenkins", "false"));
		final boolean allesSchliessenBeiTestEnde = Boolean
				.parseBoolean(WinPropertyManager.getInstance().getProperty("alles.schliessen.bei.test.ende", "false"));
		if (remote || jenkins || allesSchliessenBeiTestEnde) {
			stoppeWebDrivers(tr);
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		stoppeWebDrivers(tr);
	}

	private void stoppeWebDrivers(final ITestResult tr) {
		Object instance = tr.getInstance();
		if (instance instanceof DriverCleanUp) {
			((DriverCleanUp) instance).stopWinDrivers();
		}
	}

}
