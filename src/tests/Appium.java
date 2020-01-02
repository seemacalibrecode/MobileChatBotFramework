package tests;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.xssf.usermodel.XSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;

public class Appium {
	
	 private static XSSFWorkbook book;
	 private static WebDriverWait wait;
	
	public static void main(String[] args) throws InterruptedException{
		
		AppiumDriver<MobileElement> driver = null;
		
		//Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "My Phone");
		caps.setCapability("udid", "9708a9f1"); //Give Device ID of your mobile phone
		caps.setCapability("platformName", "Android");
		caps.setCapability("platformVersion", "6.0");
		caps.setCapability("appPackage", "ai.replika.app");
		caps.setCapability("appActivity", "ai.replika.app.ui.activity.onboarding.LauncherActivity");
		caps.setCapability("noReset", "true");
		 
		
		//Instantiate Appium Driver
		try
		{
				driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
			
		} 
		catch (MalformedURLException e)
		{
			System.out.println(e.getMessage());
		}
		
		try {
			File excel=new File("Book4.xlsx");
			FileInputStream fis = new FileInputStream(excel); 
			book = new XSSFWorkbook(fis); 
			XSSFSheet sheet = book.getSheetAt(0); 
			Iterator<Row> itr = sheet.iterator(); 
			while(itr.hasNext())
			{	
				Row row = itr.next();
				Cell cell=row.getCell(0);
				String s2=cell.getStringCellValue();
				driver.findElementByClassName("android.widget.EditText").sendKeys(s2);
				driver.findElement(By.id("ai.replika.app:id/fragment_chat_send")).click();
				Thread.sleep(10000);
				List<MobileElement> allChats = driver.findElements(By.id("ai.replika.app:id/view_chat_user_message_content"));
				String kk = allChats.get(allChats.size() -1).getText();
				Cell cell1=row.getCell(1);
				String k1=cell1.getStringCellValue();
				Cell cell2=row.getCell(2);
				if(k1.equals(kk))
					{
					
					cell2.setCellValue("Pass");
					}
				    
				else
					{
					cell2.setCellValue("No");
					}
					FileOutputStream fos = new FileOutputStream(excel);
					book.write(fos);
					fos.close();
				    
				
			}

	
		}
		catch (FileNotFoundException fe) 
		{ fe.printStackTrace(); } 
        catch (IOException ie)
		{ ie.printStackTrace(); } 
	}
 
}
