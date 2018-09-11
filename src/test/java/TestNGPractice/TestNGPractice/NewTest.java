package TestNGPractice.TestNGPractice;

import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NewTest {

	public WebDriver driver;
	public WebDriverWait wait;

	@BeforeTest
	public void before() {
		System.setProperty("webdriver.chrome.driver", "E:\\Softwares\\Selenium\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("http://automationpractice.com/");
	}

	@Test
	public void LoginTest() {
		// Login
		driver.findElement(By.cssSelector(".login")).click();
		driver.findElement(By.id("email")).sendKeys("ramarajan@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("ramarajan");
		driver.findElement(By.id("SubmitLogin")).click();
	}

	@Test
	public void SelectCasualDress()
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement dresses = driver.findElement(By.cssSelector(".sf-menu > li:nth-child(2) > a:nth-child(1)"));
		Actions action = new Actions(driver);
		action.moveToElement(dresses).build().perform();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		action.pause(30);
		
		By locator = By.cssSelector(".sf-menu > li:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		//driver.findElement(By.partialLinkText("CASUAL DRESSES")).click();
		driver.findElement(By.cssSelector(".sf-menu > li:nth-child(2) > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)")).click();
//		driver.findElement(By.xpath("/html/body/div/div[1]/header/div[3]/div/div/div[6]/ul/li[2]/ul/li[1]/a")).click();
//		driver.findElement(By.xpath(".//a[contains(@title, 'Casual Dresses')]")).click();
		
		//Mouse Hover on Product
		action.moveToElement(driver.findElement(By.className("product-container"))).build().perform();
		driver.findElement(By.cssSelector(".ajax_add_to_cart_button > span:nth-child(1)")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='layer_cart']")));
		//Click on Proceed to Checkout
		driver.findElement(By.xpath("//div[@id='layer_cart']//span[contains(.,'Proceed to checkout')]")).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='center_column']/p[2]")));
		//Proceed to check out at Summary
		driver.findElement(By.cssSelector(".standard-checkout > span:nth-child(1)")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='center_column']/form/p/button/span")));
		//Proceed to check out at Address
		driver.findElement(By.xpath("//*[@id='center_column']/form/p/button/span")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cgv")));
		//Click on Terms of Service
//		driver.findElement(By.id("cgv")).click();
		//Click on Proceed to Checkout
		driver.findElement(By.xpath("//*[@id='form']/p/button")).click();
		
		//Check for Error Message
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"order\"]//p[@class='fancybox-error']")));
			driver.findElement(By.xpath("//*[@id=\"order\"]//a[@title='Close' or @class='fancybox-item fancybox-close']")).click();
		}
		catch(Exception e)
		{
			System.out.println("Error is not displayed");
		}
		
		System.out.println(driver.findElement(By.id("form")).isEnabled());
		//Click on Terms of Service
//		wait.until(ExpectedConditions.elementToBeClickable(By.id("cgv")));
		System.out.println(driver.findElement(By.id("cgv")).isEnabled());
		driver.navigate().refresh();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='form']/div/p[2]")));
		WebElement terms = driver.findElement(By.id("cgv"));
//		action.moveToElement(terms).click().perform();
		terms.click();
		//driver.findElement(By.id("cgv")).click();
		driver.findElement(By.cssSelector("button.button:nth-child(4)")).click();
		
		//Payment by Check
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"HOOK_PAYMENT\"]//p/a[@title='Pay by check.' or @class='cheque']")));
		driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]//p/a[@title='Pay by check.' or @class='cheque']")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button.button-medium")));
		driver.findElement(By.cssSelector("button.button-medium")).click();
		
		//Verify Alert is Present "Your order on My Store is complete."
		wait.until(ExpectedConditions.titleContains("Order confirmation - My Store"));
		assertTrue(driver.findElement(By.cssSelector(".alert")).isDisplayed());
		System.out.println(driver.findElement(By.cssSelector(".alert")).getText());
		
		//Capture Screenshot
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File scrFilePath = new File(System.getProperty("user.dir")+"\\orderPlaced.png");
		try {
			FileUtils.copyFile(scrFile, scrFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO Exception");
		}
	}
	
	@AfterTest
	public void windUp()
	{
		driver.quit();
	}
}
