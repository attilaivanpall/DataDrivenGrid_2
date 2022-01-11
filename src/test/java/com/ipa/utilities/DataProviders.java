package com.ipa.utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "bankManagerDP", parallel = true)
	public static Object[][] getDataSuite1(Method m){
		
		System.out.println(m.getName());
		String testcase = m.getName();
		
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		
		return DataUtil.getDataTest(testcase, excel);
	}
	
	
	@DataProvider(name = "openAccountDP", parallel = true)
	public static Object[][] getDataSuite2(Method m){
		
		System.out.println(m.getName());
		
		String testcase = m.getName();
		//String 
		
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		
		return DataUtil.getDataTest(testcase, excel);
		
	}
}
