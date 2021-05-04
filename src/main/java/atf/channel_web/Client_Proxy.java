package atf.channel_web;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import atf.Parent_proxy;
import atf.start.Start_Web;
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
	
	public void attachScreenShot() {
		final byte[] screenshot = (Start_Web.browser).getScreenshotAs(OutputType.BYTES);
		scenario.embed(screenshot, "image/png");
	}
	
	public void printText(String str) {
		scenario.write(str);
	}
	
	public void launch_browser() {
		load_properties(true);
		initiate_browser();
		Start_Web.browser.manage().window().maximize();
	}
	
	private void initiate_browser() {
		Driver_Loader path = new Driver_Loader();
		WebDriver driver;
		String browser = "webdriver." + prop.getProperty("BROWSER") + ".driver";
		System.setProperty(browser, String.valueOf(path.PATH_TO_DRIVER));
		if(prop.getProperty("BROWSER").equals("chrome")) {
			driver = new ChromeDriver(set_caps());
			
		}else {
			driver = new InternetExplorerDriver(set_caps();)
		}
		event_listener(driver);
	}
	
	private DesiredCapabilities set_caps() {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		ChromeOptions option = new ChromeOptions();
		option.setBinary("chrome.exe path from program files folder");
		caps.setCapability(ChromeOptions.CAPABILITY, option);
		return caps;
	}
	
	public void clear_session() {
		Start_Web.browser.get("javascript:localStorage.clear();");
		clear_browser_cache();
	}
	
	private void clear_session_storage() {
		// clear local storage

	}
	
	private void clear_browser_cache() {
		Start_Web.browser.manage().deleteAllCookies();

	}
	
	private void event_listener(WebDriver driver) {
		Start_Web.browser = new EventFiringWebDriver(driver);
		WebEventListener eventListener = new WebEventListener();
		if(prop.get("LOG")).toString().contains("true")){
			Start_Web.browser.register(eventListener);
		}else {
			Start_Web.browser.unregister(eventListener);
		}

	}
	
	public void quit_browser() {
		Start_Web.browser.quit();
	}
	
	public void close_browser() {
		Set<String> windowHandles = Start_Web.browser.getWindowHandles();
		for(String window : windowHandles) {
			Start_Web.browser.switchTo().window(window);
			Start_Web.browser.close();
		}
	}
}
