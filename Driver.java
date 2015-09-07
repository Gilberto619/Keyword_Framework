package testCases;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import operation.ActionKeywords;
import operation.ReadObject;
import utility.ExcelUtils;
import utility.Log;

public class Driver {
	final static String frame_Path = System.getProperty("user.dir") + "\\src";
	WebDriver webdriver = null;

	@Test(dataProvider = "hybridData")
	public void testLogin(String testcaseName, String keyword, String objectName, String objectType, String caseAction,
			String value) throws Exception {
		if (testcaseName != null && testcaseName.length() != 0) {
			Log.startTestCase(testcaseName);
			webdriver = new FirefoxDriver();
			webdriver.manage().window().maximize();
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		DOMConfigurator.configure("log4j.xml");
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		ActionKeywords operation = new ActionKeywords(webdriver);
		// Call perform function to perform operation on UI
		operation.perform(allObjects, keyword, objectName, objectType, caseAction, value);

	}

	@DataProvider(name = "hybridData")
	public Object[][] getDataFromDataprovider() throws IOException {
		Object[][] object = null;
		ExcelUtils file = new ExcelUtils();
		// Read keyword sheet
		Sheet testSheet = file.readExcel(frame_Path + "\\inputFiles", "TestCases.xlsx", "Keyword");
		// Find number of rows in excel file
		int rowCount = testSheet.getLastRowNum() - testSheet.getFirstRowNum();
		object = new Object[rowCount][6];
		for (int i = 0; i < rowCount; i++) {
			// Loop over all the rows
			Row row = testSheet.getRow(i + 1);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Print excel data in console
				object[i][j] = row.getCell(j).toString();
			}
		}
		System.out.println("");
		return object;
	}
}
