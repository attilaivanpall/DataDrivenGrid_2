package com.ipa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.ipa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;

public class CustomListeners extends TestBase implements ITestListener{

	
	public void onTestStart(ITestResult result) {

		//test = extent.startTest("Add Customer Test " + browser);
		//setExtentTest(test);
	  }
	
	
	public void onTestSuccess(ITestResult result) {
	    getExtentTest().log(LogStatus.PASS, result.getName() + " testcase is PASSED.");
	    extent.endTest(getExtentTest());
	    extent.flush();
	    addLog(result.getName() + " testcase is PASSED.");
	    
	  }
	
	
	public void onTestFailure(ITestResult result) {
	    captureScreenshot();
		getExtentTest().log(LogStatus.FAIL, result.getName() + " Testcase is failed.");
		getExtentTest().log(LogStatus.FAIL, result.getThrowable());
		getExtentTest().log(LogStatus.FAIL, test.addScreenCapture(screenshotName));
		extent.endTest(getExtentTest());
		extent.flush();
		addLog(result.getName() + " Testcase is failed.");
		
		String errorMsg = result.getThrowable().toString();
		
		addLog(errorMsg);
	  }
	
	
	public void onTestSkipped(ITestResult result) {
	    
		//throw new SkipException("Skipping testcase " + result.getName());
		
		
	  }
	
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }
	
	
	public void onTestFailedWithTimeout(ITestResult result) {
	   
	  }
	
	
	public void onStart(ITestContext context) {
	    // not implemented
	  }
	
	
	public void onFinish(ITestContext context) {

	  }
	
	
}
