package atf.start;


import atf.Logging;
import atf.channel_web.Client_Proxy;
import atf.start.helper.helper;
import cucumber.api.Scenario;

import java.io.File;
import java.nio.file.Path;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.events.EventFiringWebDriver;



public class Start_Web extends helper{

	public static final int DEFAULT_TIMEOUT =45;
	public static EventFiringWebDriver browser;
	private Client_Proxy client_proxy;

	public Start_Web(Scenario scenario) {
		client_proxy = new Client_Proxy();
		client_proxy.initialize(scenario);
		start_log(scenario);
	}

	public void attachScreenshot() {
		client_proxy.attachScreenShot();
	}

	public void printText(String str) {
		client_proxy.printText(str);
	}

	public void close_browser(Scenario scenario) {
		client_proxy.close_browser();
		client_proxy.quit_browser();
		close_log(scenario);

	}

	public void log_file_handler() {
		Logging.merge_to_single_log_file();
		Logging.delete_temp_dir();
	}

	private void close_log(Scenario scenario) {
		logging.end_log("Finishing scenario >>>>" + scenario.getName() + "\n") +
		StringUtils.repeat("*", 120) + "\n");

	}

	private void start_log(Scenario scenario) {
		logging.start_log("Starting scenario >>>>"+ scenario.getName());

	}

	public Object load_class(Path path) {
		File file = new File(String.valueOf(path));

		URL url = null;
		try {
			url = file.toURI().toURL();
		}catch(MalformedURLException e) {
			e.printStackTrace();
		}
		URL[] urls = new URL[] {url};
		ClassLoader cl = new URLClassLoader(urls);

		Class cls = null;

		try {
			cls = cl.loadClass("Constants");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object object = null;
		try {
			object = cls.newInstance();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}


}
