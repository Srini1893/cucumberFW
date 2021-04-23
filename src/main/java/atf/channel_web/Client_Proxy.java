package atf.channel_web;

import java.util.concurrent.TimeUnit;

import atf.Parent_proxy;
import cucumber.api.Scenario;

public class Client_Proxy extends Parent_proxy{
	
	private Scenario scenario;
	
	public void initialize(Scenario scenario) {
		int timeout = Start_web.DEFAULT_TIMEOUT;
		this.scenario = scenario;
		launch_browser();
		Start_Web.browser.manage().window.maximize();
		Start_Web.browser.manage().timeouts.implicitlyWait(timeout, TimeUnit.SECONDS);
	}
}
