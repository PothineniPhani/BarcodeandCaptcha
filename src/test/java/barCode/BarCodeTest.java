package barCode;



import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;



public class BarCodeTest {
	
	public static WebDriver driver;
	
	@BeforeMethod
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "E://Testing//Webdriver//chromedriver_win32//chromedriver.exe");
		driver=new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.get("https://qrcode.tec-it.com/en");
	}
	
	@Test
	public void TestBarCode() throws IOException, NotFoundException
	{
	  String barCodeURL=driver.findElement(By.tagName("img")).getAttribute("src");
	  System.out.println(barCodeURL);
	  
	  URL url=new URL(barCodeURL);
	  BufferedImage bufferedImage = ImageIO.read(url);
	  
	  LuminanceSource luminancesource= new BufferedImageLuminanceSource(bufferedImage);
	  BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(luminancesource));
	  Result result=new MultiFormatReader().decode(bitmap);
	  System.out.println(result);
	  
	}
	
	@AfterMethod
	public void tearDown() {
		driver.close();
	}
	

}
