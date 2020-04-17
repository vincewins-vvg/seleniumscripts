package daily.practise.scenarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class NykkaWebsiteUseCase {
//	    1) Go to https://www.nykaa.com/
//		2) Mouseover on Brands and Mouseover on Popular
//		3) Click L'Oreal Paris
//		4) Go to the newly opened window and check the title contains L'Oreal Paris
//		5) Click sort By and select customer top rated
//		6) Click Category and click Shampoo
//		7) check whether the Filter is applied with Shampoo
//		8) Click on L'Oreal Paris Colour Protect Shampoo
//		9) GO to the new window and select size as 175ml
//		10) Print the MRP of the product
//		11) Click on ADD to BAG
//		12) Go to Shopping Bag 
//		13) Print the Grand Total amount
//		14) Click Proceed
//		15) Click on Continue as Guest
//		16) Print the warning message (delay in shipment)
//		17) Close all windows
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.nykaa.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//WebElement brandList = driver.findElementByXPath("//a[text()="brands"]");
		Actions hover = new Actions(driver);
		hover.moveToElement(driver.findElementByXPath("//a[text()=\"brands\"]"));
		Thread.sleep(5000);
     	//WebElement popular = driver.findElementByXPath("//a[@class='brandHeadingbox current_active']");
		
		hover.moveToElement(driver.findElementByXPath("//a[text()=\"Popular\"]")).perform();

     	driver.findElementByXPath("//img[@src='https://adn-static2.nykaa.com/media/wysiwyg/2019/lorealparis.png']").click();		
    	
		Set<String> windowHandles = driver.getWindowHandles();
		
		List<String> handlesInList = new ArrayList<String>(windowHandles);
		
		//Switched to Loreal Page
		driver.switchTo().window(handlesInList.get(1));
		Thread.sleep(3000);
		String title = driver.getTitle();
		if (title.contains("L'Oreal"))
		{
			System.out.println(title);
		}
		
		Thread.sleep(7000);
		driver.findElementByXPath("//span[contains(text() ,'Sort By : ')]").click();
		driver.findElementByXPath("//span[contains(text() ,'customer top rated')]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//div[text()='Category']").click();
		//driver.findElementByXPath("//div[text()='Category']/following-sibling::div/i").click();
		driver.findElementByXPath("//span[contains (text() ,'Shampoo')]").click();
		String filterAppliedCheck = driver.findElementByXPath("//ul[@class='pull-left applied-filter-lists']/li[1]").getText();
		
		try {
			filterAppliedCheck.contains("Shampoo");
			System.out.println(filterAppliedCheck+ " filter has been applied");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		driver.findElementByXPath("//h2[contains(@title,'Oreal Paris Colour Protect Shampoo')]").click();
		
       Set<String> handleToProductPages = driver.getWindowHandles();
		
		List<String> productPagesList = new ArrayList<String>(handleToProductPages);
		//Switch  to individual product page
		driver.switchTo().window(productPagesList.get(2));
		
		Thread.sleep(3000);
	
		//To check if the size is available
		List<WebElement> availabilityCheck = driver.findElements(By.xpath("//span[contains(text(),'175ml')]/ancestor::li[@class='opacity-dull']"));
		
		    
			if(availabilityCheck.size()==0)
			{
		      System.out.println("The selected size " +driver.findElementByXPath("//span[@class='size-pallets' and contains(text(),'175ml')]").getText()+ " is available");
				
				driver.findElementByXPath("(//span[@class='size-pallets'])[2]").click();
				
				String price = driver.findElementByXPath("(//span[@class='post-card__content-price-offer'])[1]").getText().trim().replaceAll("\\D", "");
				System.out.println("Price is Rs "+price.trim().replace("?", ""));
				
				driver.findElementByXPath("(//button[text()='ADD TO BAG'])[1]").click();
				driver.findElementByXPath("//div[@class='AddBagIcon']").click();
				
				Thread.sleep(2000);
				//To print grand total
				String grandTotal = driver.findElementByXPath("(//div[@class='value'])[4]").getText().trim().replaceAll("\\D", "");
				System.out.println("Grand Total is Rs "+grandTotal.trim().replace("?", ""));
				
				//Click proceed
				driver.findElementByXPath("//span[contains(text(),'Proceed')]").click();
				Thread.sleep(2000);
				//Continue as Guest
				driver.findElementByXPath("//button[contains(text(),'CONTINUE AS GUEST')]").click();
				
				//To check if any announcement is present
				List<WebElement> isWarninggMessagePresent = driver.findElements(By.className("message"));
				if(isWarninggMessagePresent.size()>0)
				{
					System.out.println("Important Notice: "+driver.findElementByClassName("message").getText());
				}
				
				driver.quit();
			}
			else{
					System.out.println("The selected size is " +driver.findElementByXPath("//span[@class='size-pallets' and contains(text(),'175ml')]").getText()+ " not available");
					driver.quit();
				}
			
			
			
			
}
		
		
	}

