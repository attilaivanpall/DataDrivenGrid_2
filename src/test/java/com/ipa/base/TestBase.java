package com.ipa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.ipa.utilities.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class TestBase {
	
	public static RemoteWebDriver driver = null;
	public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();
	public FileInputStream fis;
	public Properties OR = new Properties();
	public Properties Config = new Properties();
	public static DesiredCapabilities cap;
	public static ExtentReports extent = ExtentManager.getInstance();
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> exTest = new ThreadLocal<ExtentTest>(); 
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static WebDriverWait wait;
	public String browser;
	
	
	public static void addLog(String msg) {
		
		log.debug("Browser : " + getThreadValue(dr.get()) + "   " + msg);
	}
	
	
	
	public static String getThreadValue(Object value) {
		
		String text1 = value.toString();
		String[] newText = text1.split(" ");
		String threadID = newText[newText.length-1];
		String bRowser = newText[newText.length-4];
		String text2 = bRowser + " - " + threadID;
		
		return text2;
	}
	
	
	@BeforeMethod
	public void setUp() {
		
		if(driver == null) {	
		
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Config.load(fis);
				log.debug("Config file is loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				OR.load(fis);
				log.debug("OR file is loaded.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
	
	
	public static WebDriver getDriver() {
		
		return dr.get();
	}
	
	
	public void setDriver(RemoteWebDriver driver) {
		
		dr.set(driver);
	}
	
	
	public static ExtentTest getExtentTest() {
		
		return exTest.get();
	}
	
	
	public void setExtentTest(ExtentTest et) {
		
		exTest.set(et);
	}
	
	
	public void openBrowser(String browser) throws MalformedURLException {
		
		this.browser = browser;
		DesiredCapabilities cap = new DesiredCapabilities();
		
		if(browser.equals("chrome")) {
			
			//cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.ANY);
			
			ChromeOptions options = new ChromeOptions();
			//options.setBrowserVersion("95");
			//options.setPlatformName("Linux");
			options.merge(cap);
			
		} else if(browser.equals("firefox")) {
			
			//cap = DesiredCapabilities.firefox();
			cap.setBrowserName("firefox");
			cap.setPlatform(Platform.ANY);
			
			FirefoxOptions options = new FirefoxOptions();
			options.merge(cap);
			
		} else if(browser.equals("edge")) {
			
			//cap = DesiredCapabilities.edge();
			cap.setBrowserName(BrowserType.EDGE);
			cap.setPlatform(Platform.LINUX);
			
			EdgeOptions options = new EdgeOptions();
			options.merge(cap);
			
		}
		
		driver = new RemoteWebDriver(new URL("http:/localhost:4445/wd/hub"), cap);
		setDriver(driver);
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);
		//wait = new WebDriverWait(getDriver(), 10);
		getExtentTest().log(LogStatus.INFO, "Browser opened successfully " + browser);
		addLog("Browser opened successfully " + browser);
		
		System.out.println(getThreadValue(dr.get()));
	}
	
	
	
	public void navigate(String url) {
		
		getDriver().get(Config.getProperty(url));
	}
	
	
	
	public void click(String locator){
		
		if(locator.endsWith("_CSS")) {
			
			getDriver().findElement(By.cssSelector(OR.getProperty(locator))).click();
			
			
		} else if(locator.endsWith("_XPATH")) {
			
			getDriver().findElement(By.xpath(OR.getProperty(locator))).click();
			
		} else if(locator.endsWith("_ID")) {
			
			getDriver().findElement(By.id(OR.getProperty(locator))).click();
			
		}
		
		getExtentTest().log(LogStatus.INFO, "Clicking on button: " + locator);
		addLog("Clicking on button: " + locator);
	}
	
	
	
	
	public void type(String locator, String value){
		
		if(locator.endsWith("_CSS")) {
			
			getDriver().findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
			
		} else if(locator.endsWith("_XPATH")) {
			
			getDriver().findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
			
		} else if(locator.endsWith("_ID")) {
			
			getDriver().findElement(By.id(OR.getProperty(locator))).sendKeys(value);
			
		}
		
		getExtentTest().log(LogStatus.INFO, "Typing on button: " + locator);
		addLog("Typing on button: " + locator);
	}
	
	
	public WebElement dropdown;
	
	public void select(String locator, String value) {
		
			if(locator.endsWith("_CSS")) {
				
				dropdown = getDriver().findElement(By.cssSelector(OR.getProperty(locator)));
				
			} else if(locator.endsWith("_XPATH")) {
				
				dropdown = getDriver().findElement(By.xpath(OR.getProperty(locator)));
				
			} else if(locator.endsWith("_ID")) {
				
				dropdown = getDriver().findElement(By.id(OR.getProperty(locator)));
				
			}
			
			Select select = new Select(dropdown);
			
			select.selectByVisibleText(value);
			
			getExtentTest().log(LogStatus.INFO, "Selecting " + value + " element from " + locator + " dropdown list.");
			addLog("Selecting " + value + " element from " + locator + " dropdown list.");
		
	}
	
	
	public static String screenshotName;
	
	public void captureScreenshot() {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		try {
			FileUtils.copyFile(srcFile, new File (System.getProperty("user.dir") + "\\reports\\" + screenshotName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		getExtentTest().log(LogStatus.INFO, " Screenshot -> " + test.addScreenCapture(screenshotName));
	}
	
	
	public boolean isElementPresent(String locator) {
		
		try {
		
		if(locator.endsWith("_CSS")) {
			
			getDriver().findElement(By.cssSelector(OR.getProperty(locator)));
			
		} else if(locator.endsWith("_XPATH")) {
			
			getDriver().findElement(By.xpath(OR.getProperty(locator)));
			
		} else if(locator.endsWith("_ID")) {
			
			getDriver().findElement(By.id(OR.getProperty(locator)));
			
		}
		
		getExtentTest().log(LogStatus.INFO, "Assertion is successful: " + locator);
		addLog("Assertion is successful: " + locator);
		
		return true;
		
		} catch (NoSuchElementException n) {
			
			getExtentTest().log(LogStatus.FAIL, "Assertion is unsuccessful: " + locator);
			addLog("Assertion is unsuccessful: " + locator);
			return false;
			
		}
			
	}
	
	
	
	public void verifyEquals(String actual, String expected) {
		
		try {
			
			Assert.assertEquals(actual, expected);
			getExtentTest().log(LogStatus.INFO, "Value verification is successful: " + expected);
			addLog("Value verification is successful: " + expected);
			
		} catch(Throwable t) {
			
			getExtentTest().log(LogStatus.FAIL, "Value verification is unsuccessful: " + "Actual value: " + actual + " - Expected value: " + expected);
			addLog("Value verification is unsuccessful: " + "Actual value: " + actual + " - Expected value: " + expected);
		}
		
	}
	
	
	public void waitFor(String locator) {
		
		if(locator.endsWith("_CSS")) {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(OR.getProperty(locator))));
			
		} else if(locator.endsWith("_XPATH")) {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locator))));
			
		} else if(locator.endsWith("_ID")) {
			
			wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(locator))));
			
		}
		
	}
	
	

	
	
	@AfterMethod
	public void tearDown() {
		
		if(extent!=null) {
			
			extent.endTest(getExtentTest());
			extent.flush();
		}
		
		getDriver().quit();
		driver=null;
	}
}
