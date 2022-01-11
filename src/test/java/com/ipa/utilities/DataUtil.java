package com.ipa.utilities;

import java.util.Hashtable;

import org.testng.SkipException;

import com.ipa.base.TestBase;
import com.relevantcodes.extentreports.LogStatus;



public class DataUtil extends TestBase{
	

	public static void checkExecution(String testSuiteName,
									  String testCaseName,
									  String runMode,
									  ExcelReader excel) {
		
		if(!isSuiteRunnable(testSuiteName)) {
			
			getExtentTest().log(LogStatus.SKIP, "Skipping testcase: " + testCaseName + " as the runmode of testsuite: " + testSuiteName + " is NO");
			addLog("Skipping testcase: " + testCaseName + " as the runmode of testsuite: " + testSuiteName + " is NO");
			throw new SkipException("Skipping testcase: " + testCaseName + " as the runmode of testsuite: " + testSuiteName + " is NO");
			

		}
		
		if(!isTestRunnable(testCaseName, excel)) {
			
			getExtentTest().log(LogStatus.SKIP, "Skipping testcase: " + testCaseName + " as the runmode of testcase: " + testCaseName + " is NO");
			addLog("Skipping testcase: " + testCaseName + " as the runmode of testcase: " + testCaseName + " is NO");
			throw new SkipException("Skipping testcase: " + testCaseName + " as the runmode of testcase: " + testCaseName + " is NO");
		}
		
		if(runMode.equalsIgnoreCase(Constants.RUNMODE_NO)) {
			
			getExtentTest().log(LogStatus.SKIP, "Skipping the test: " + testCaseName + " as the runmode to dataset: is NO");
			addLog("Skipping the test: " + testCaseName + " as the runmode to dataset: is NO");
			throw new SkipException("Skipping the test: " + testCaseName + " as the runmode to dataset: is NO");
		}
		
	}
	
	
	public static boolean isSuiteRunnable(String suitename) {
		
		ExcelReader excel = new ExcelReader(Constants.SUITE_XL_PATH);
		
		int rows = excel.getRowCount(Constants.SUITE_SHEET);
		
		for(int rNum = 2; rNum <= rows; rNum ++) {
			
			String data = excel.getCellData(Constants.SUITE_SHEET, Constants.SUITE_NAME, rNum);
			
			if(data.equals(suitename)) {
				
				String runmode = excel.getCellData(Constants.SUITE_SHEET, Constants.SUITE_RUNMODE, rNum);
				
				if(runmode.equals(Constants.RUNMODE_YES)) {
					
					return true;
					
				} else {
					
					return false;
					
				}
			}
			
		}
		
		return false;
		
	}
	
	
	
	
	public static boolean isTestRunnable(String testCaseName, ExcelReader excel) {
		
		int rows = excel.getRowCount(Constants.TESTCASE_SHEET);
		
		int rNum = 2;
		
		for(rNum = 2; rNum <= rows; rNum++) {
			
			String data = excel.getCellData(Constants.TESTCASE_SHEET, Constants.TESTCASE_NAME, rNum);
			
			if(data.equals(testCaseName)) {
				
				String runmode = excel.getCellData(Constants.TESTCASE_SHEET, Constants.TESTCASE_RUNMODE, rNum);
				
				if(runmode.equals(Constants.RUNMODE_YES)) {
					
					return true;
					
				} else {
					
					return false;
					
				}
				
			}
		}
		
		return false;
		
	}
	
	
	
	
	public static Object[][] getData(String testcase, ExcelReader excel){
		
		int rows = excel.getRowCount(Constants.DATA_SHEET);
		
		int testCaseRowNum = 1;
		
		String testname = testcase;
		
		for(testCaseRowNum = 1; testCaseRowNum <= rows; testCaseRowNum++) {
			
			String testCaseName = excel.getCellData(Constants.DATA_SHEET, 0, testCaseRowNum);
			
			if(testCaseName.equals(testname)) {
				
				break;
			}
		}
		
		
		int dataStartRowNum = testCaseRowNum + 2;
		
		int testRows = 0;
	
		while(!excel.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum + testRows).equalsIgnoreCase("")) {
			
			testRows++;
		}
		
		
		int colStartColRowNum = testCaseRowNum + 1;
		
		int colNum = 0;
		
		while(!excel.getCellData(Constants.DATA_SHEET, colNum, colStartColRowNum).equals("")) {
			
			colNum++;
			
		}
		
		
		Object[][] data = new Object[testRows][1];
		
		int i = 0;
		
		
		for(int rNum = 1; rNum <= testRows; rNum++) {
			
			Hashtable<String, String> table = new Hashtable<String, String>();
			
			for(int cNum = 1; cNum < colNum; cNum++) {
				
				String testdata = excel.getCellData(Constants.DATA_SHEET, cNum, rNum);
				
				String colName = excel.getCellData(Constants.DATA_SHEET, cNum, colStartColRowNum);
				
				table.put(colName, testdata);
				
			}
			
			data[i][0] = table;
			i++;
		}
		
		return data;
		
	}
	
	
	
	public static Object[][] getDataTest(String testCase, ExcelReader excel){
		
		//ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		
		//Defining the start row of a testcase
		int rows = excel.getRowCount(Constants.DATA_SHEET);
		
		System.out.println(rows);
		
		int testCaseRowNum = 1;
		
		String testName = testCase;
		
		for(testCaseRowNum = 1; testCaseRowNum <= rows; testCaseRowNum++) {
			
			String testCaseName = excel.getCellData(Constants.DATA_SHEET, 0, testCaseRowNum);
			
			if(testCaseName.equalsIgnoreCase(testName)) {
				System.out.println(testCaseName);
				break;
			}
		}
		
		
		//Counting the number of test rows
		int dataStartRowNum = testCaseRowNum + 2;
		
		int testRows = 0;
		
		while (!excel.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum + testRows).equalsIgnoreCase("")) {
			
			testRows++;
			
		}

		System.out.println("Number of test rows: " + testRows);
		
		
		//Counting the number of test columns
		int colDataStartRowNum = testCaseRowNum + 1;
		
		int testCols = 0;
		
		while(!excel.getCellData(Constants.DATA_SHEET, testCols, colDataStartRowNum).equalsIgnoreCase("")) {
			
			testCols++;
		}
		
		System.out.println("Number of test cols: " + testCols);
		
		
		//Printint the data
		Object[][] data = new Object[testRows][1]; 
		
		int i = 0;
		
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {
			
			Hashtable<String, String> table = new Hashtable<String, String>();
			
			for (int cNum = 0; cNum < testCols; cNum++) {
				
				String value = excel.getCellData(Constants.DATA_SHEET, cNum, rNum);
				String key = excel.getCellData(Constants.DATA_SHEET, cNum, colDataStartRowNum);
				
				table.put(key, value);
				
			}
			
			data[i][0] = table;
			i++;
		}
		
		return data;
	}
	
	
}
