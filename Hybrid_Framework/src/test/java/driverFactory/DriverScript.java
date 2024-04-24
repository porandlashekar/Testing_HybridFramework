package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	WebDriver driver;
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String TestCases = "MasterTestCases";

	public void startTest() throws Throwable {
		String Module_status = "";
		// Create object for excelFileUtil class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		// iterate all the test cases in TestCases
		for (int i = 1; i <= xl.rowCount(TestCases); i++) {
			if (xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y")) {
				
				String Module_New = "";
				// read the corresponding sheet or TestCases
				String TCModule = xl.getCellData(TestCases, i, 1);
				// define path of HTML
				report = new ExtentReports(
						"./target/ExtentReports/" + TCModule + FunctionLibrary.generateDate() + ".html");
				logger = report.startTest(TCModule);
				logger.assignAuthor("SHEKHAR");

				// iterate all row in TcModule sheet
				for (int j = 1; j <= xl.rowCount(TCModule); j++) {
					// read all rows in TCModule Sheet
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Lname = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if (Object_Type.equalsIgnoreCase("startBrowser")) {
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("openUrl")) {
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(Lname, Lvalue, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(Lname, Lvalue, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(Lname, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("mouseClick")) {
							FunctionLibrary.mouseClick();
							logger.log(LogStatus.INFO, Description);
						}
						if (Object_Type.equalsIgnoreCase("categoryTable")) {
							FunctionLibrary.categoryTable(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Lname, Lvalue, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Lname, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
						}
						// write as pass into TCModule sheet in status cell
						xl.setCellData(TCModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Module_status = "True";

					} catch (Exception e) {
						System.out.println(e.getMessage());
						// write as fail into TCModule Sheet statusCell
						xl.setCellData(TCModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
//						Module_status= "False";
						Module_New = "False";

					}
//					if(Module_status.equalsIgnoreCase("True"))
//					{
//						//write as pass into TestCases sheet
//						xl.setCellData(TestCases, i, 3, "PASS", outputpath);
//					}
//					else
//					{
//						//write as pass into TestCases sheet
//						xl.setCellData(TestCases, i, 3, "FAIL", outputpath);
//					}
					if (Module_status.equalsIgnoreCase("True")) {
						// write as pass into TestCases sheet
						xl.setCellData(TestCases, i, 3, "PASS", outputpath);
					}
					if (Module_New.equalsIgnoreCase("False")) {
						// write as pass into TestCases sheet
						xl.setCellData(TestCases, i, 3, "FAIL", outputpath);
					}
					report.endTest(logger);
					report.flush();
				}

			} else {
				// write as blocked for test cases flag to N in TestCases Sheet
				xl.setCellData(TestCases, i, 3, "Blocked", outputpath);
			}
		}
	}

}
