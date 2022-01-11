package com.ipa.suite.bankmanager.testcases;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ipa.base.TestBase;
import com.ipa.utilities.Constants;
import com.ipa.utilities.DataProviders;
import com.ipa.utilities.DataUtil;
import com.ipa.utilities.ExcelReader;

public class OpenAccountTest extends TestBase{
	
	
	@Test(dataProviderClass = DataProviders.class, dataProvider = "openAccountDP")
	public void openAccountTest(Hashtable<String, String> data) throws MalformedURLException {
		
		//super.setUp();
		test = extent.startTest("Open Account Test " + data.get("browser"));
		setExtentTest(test);
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		DataUtil.checkExecution("BankManagerSuite", "OpenAccountTest", data.get("Runmode"), excel);
		openBrowser(data.get("browser"));
		navigate("testsiteurl");
		
		click("bankManagerLoginBtn_CSS");
		//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(OR.getProperty("openAccountBtn_CSS"))));
		//waitFor("openAccountBtn_CSS");
		click("openAccountBtn_CSS");
		select("customerDDFld_ID", data.get("customer"));
		select("currencyDDFld_ID", data.get("currency"));
		click("processBtn_CSS");
		
		//wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
	}
	
	
	/*@AfterMethod
	public void tearDown() {
		
		if(extent!=null) {
			
			extent.endTest(getExtentTest());
			extent.flush();
		}
		
		getDriver().quit();
		driver=null;
			
	}*/
	
}
