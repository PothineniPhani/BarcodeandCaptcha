package captcha;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class CaptchaTest {
	public static WebDriver driver;
	public static Alert alert;
	public static Actions action;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "E://Testing//Webdriver//chromedriver_win32//chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("https://www.irctc.co.in/nget/train-search");
		driver.findElement(By.xpath("//button[contains(text(),'Ok')]")).click();
	} 

	@Test
	public void CaptchaTesting() throws InterruptedException, IOException, TesseractException {
		driver.findElement(By.xpath("//a[@id='loginText']")).click();
		driver.findElement(By.xpath("//input[@id='userId']")).sendKeys("PHANI1270P");
		driver.findElement(By.xpath("//input[@id='pwd']")).sendKeys("PHANI1270P");
		Thread.sleep(5000);
		String path="E:\\Testing\\SeleniumAutomation\\BarcodeTest\\BarCode\\screenshot\\Captcha.PNG";
		/*String  src = driver.findElement(By.tagName("img")).getAttribute("src");
		System.out.println(src);		
		BufferedImage bufferedImage = ImageIO.read(src);
		System.out.println(bufferedImage);*/		
		File src=driver.findElement(By.className("captcha-img")).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(path));
		ITesseract tesseract = new Tesseract();
		String imgTest =tesseract.doOCR(new File(path));
		System.out.println(imgTest);
		//String finalString=imgTest.split("below")[1].replaceAll("[^A-Za-z0-9]", "");
		driver.findElement(By.xpath("//input[@id='captcha']")).sendKeys(imgTest);
		driver.findElement(By.xpath("//button[contains(text(),'SIGN IN')]")).click();
		String user=driver.findElement(By.xpath("//span[contains(text(),'(PHANI1270P)')]")).getText();
		System.out.println(user);
	}

}
