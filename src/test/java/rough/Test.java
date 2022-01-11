package rough;

import java.util.Hashtable;

import com.ipa.utilities.Constants;
import com.ipa.utilities.DataUtil;
import com.ipa.utilities.ExcelReader;

public class Test {

	public static void main(String[] args) {
	
		System.out.println(DataUtil.isSuiteRunnable("BankManagerSuite"));
		
		System.out.println(DataUtil.isTestRunnable("AddCustomerTest", new ExcelReader(Constants.SUITE1_XL_PATH)));
		
		//DataUtil.getData("AddCustomerTest", new ExcelReader(Constants.SUITE1_XL_PATH));
		
		ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
		
		System.out.println(DataUtil.getDataTest("AddCustomerTest", excel));
	}
	
}
