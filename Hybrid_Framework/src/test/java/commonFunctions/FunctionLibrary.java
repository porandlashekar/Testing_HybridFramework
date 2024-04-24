package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;

	public static WebDriver startBrowser() throws Throwable {
		conpro = new Properties();
		// load file
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if (conpro.getProperty("Browser").equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			Reporter.log("Browser key value is not Matching", true);
		}
		return driver;
	}

	// Method for Launching URL
	public static void openUrl() {
		driver.get(conpro.getProperty("Url"));
	}

	// Method for WebElement to wait till load
	public static void waitForElement(String LocatorName, String LocatorValue, String TestData) {
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		// ParseINT= is used to convert string type data into Integer type data
		if (LocatorName.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if (LocatorName.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
		if (LocatorName.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
	}

	// method for type Action
	public static void typeAction(String LocatorName, String LocatorValue, String TestData) {
		if (LocatorName.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if (LocatorName.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);

		}
		if (LocatorName.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);

		}
	}

	// method for page click Action
	public static void clickAction(String LocatorName, String LocatorValue) {
		if (LocatorName.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if (LocatorName.equalsIgnoreCase("id")) {
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		if (LocatorName.equalsIgnoreCase("name")) {
			driver.findElement(By.name(LocatorValue)).click();
		}
	}

	// method for Page Title Validation
	public static void validateTitle(String Expected_Title) {
		String Actual_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, Expected_Title, "Title is not matching");
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}

	public static String generateDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);
	}

	public static void mouseClick() throws Throwable {
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(3000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[text()='Stock Categories'])[2]"))).click().perform();
		Thread.sleep(3000);
	}

	// method for validating Category name value before adding and after adding
	public static void categoryTable(String Exp_Data) throws Throwable {
		// if search textbox not displayed click search panel
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
			// click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		Reporter.log(Exp_Data + "         " + Act_Data, true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Category Name is Not Matching");
		} catch (AssertionError e) {
			Reporter.log(e.getMessage(), true);
		}
	}

	// method for ListBoxes selection
	public static void dropDownAction(String locatorName, String locatorValue, String tesData) {
		if (locatorName.equalsIgnoreCase("xpath")) {
			int value = Integer.parseInt(tesData);
			Select element = new Select(driver.findElement(By.xpath(locatorValue)));
			element.selectByIndex(value);
		}
		if (locatorName.equalsIgnoreCase("id")) {
			int value = Integer.parseInt(tesData);
			Select element = new Select(driver.findElement(By.id(locatorValue)));
			element.selectByIndex(value);
		}
		if (locatorName.equalsIgnoreCase("name")) {
			int value = Integer.parseInt(tesData);
			Select element = new Select(driver.findElement(By.name(locatorValue)));
			element.selectByIndex(value);
		}
	}

	// method for capture stock number into notepad
	public static void captureStock(String locatorName, String locatorValue) throws Throwable {
		String stockNum = "";
		if (locatorName.equalsIgnoreCase("xpath")) {
			stockNum = driver.findElement(By.xpath(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("id")) {
			stockNum = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("name")) {
			stockNum = driver.findElement(By.name(locatorValue)).getAttribute("value");
		}
		// store that copied number into Notepad
		FileWriter fw = new FileWriter("./CaptureData/StockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();

	}

	// method for validate captured stock number into notepad
	public static void stockTable() throws Throwable {
		FileReader fr = new FileReader("./CaptureData/StockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
			// click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data + "         " + Act_Data, true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Stock Number is Not Matching");
		} catch (AssertionError e) {
			Reporter.log(e.getMessage(), true);
		}

	}

	// method for supplier number to capture into notepad
	public static void captureSupplier(String locatorName, String locatorValue) throws Throwable {
		String stockNum = "";
		if (locatorName.equalsIgnoreCase("xpath")) {
			stockNum = driver.findElement(By.xpath(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("id")) {
			stockNum = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("name")) {
			stockNum = driver.findElement(By.name(locatorValue)).getAttribute("value");
		}
		// store that copied number into Notepad
		FileWriter fw = new FileWriter("./CaptureData/SupplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}

	public static void supplierTable() throws Throwable {
		FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
			// click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data + "         " + Act_Data, true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Supplier Number is not matching");
		} catch (AssertionError e) {
			Reporter.log(e.getMessage(), true);
		}
	}
	//method to Capture Customer Number 
	public static void captureCustomer(String locatorName, String locatorValue) throws Throwable {
		String stockNum = "";
		if (locatorName.equalsIgnoreCase("xpath")) {
			stockNum = driver.findElement(By.xpath(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("id")) {
			stockNum = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		if (locatorName.equalsIgnoreCase("name")) {
			stockNum = driver.findElement(By.name(locatorValue)).getAttribute("value");
		}
		// store that copied number into Notepad
		FileWriter fw = new FileWriter("./CaptureData/CustonerNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}

	public static void customerTable() throws Throwable {
		FileReader fr = new FileReader("./CaptureData/CustonerNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if (!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
			// click search panel button
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data = driver
				.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data + "         " + Act_Data, true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Customer Number is not matching");
		} catch (AssertionError e) {
			Reporter.log(e.getMessage(), true);
		}
	}

	public static void closeBrowser() {
		driver.quit();
	}

}
