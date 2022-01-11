package com.ipa.suite.bankmanager.testcases;

import java.net.MalformedURLException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.ipa.base.TestBase;
import com.ipa.utilities.Constants;
import com.ipa.utilities.DataProviders;
import com.ipa.utilities.DataUtil;
import com.ipa.utilities.ExcelReader;

public class AddCustomerTest extends TestBase{

	@Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP")
	public void addCustomerTest(Hashtable<String, String> data) throws MalformedURLException, InterruptedException {
		
		//super.setUp();
		test = extent.startTest("Add Customer Test " + data.get("browser"));
		setExtentTest(test);
		
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		
		DataUtil.checkExecution("BankManagerSuite", "AddCustomerTest", data.get("Runmode"), excel);
		
		openBrowser(data.get("browser"));
		navigate("testsiteurl");
		
		click("bankManagerLoginBtn_CSS");
		//wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(OR.getProperty("addCustomerBtn_CSS"))));
		//waitFor("addCustomerBtn_CSS");
		click("addCustomerBtn_CSS");
		type("firstNameFld_CSS", data.get("firstname"));
		type("lastNameFld_XPATH", data.get("lastname"));
		type("postCodeFld_CSS", data.get("postcode"));
		
		verifyEquals("asdf", "hjk");
		
		Assert.assertTrue(isElementPresent("addBtn_CSS"));
		click("addBtn_CSS");
		
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
