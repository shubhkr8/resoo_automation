package lt;

import java.io.File;

import java.nio.file.*;

import java.io.IOException;

import java.time.Duration;

import java.util.List;

import org.openqa.selenium.Alert;

import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.Keys;

import org.openqa.selenium.OutputType;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.io.FileHandler;

import org.openqa.selenium.support.ui.Select;

import io.opentelemetry.context.Scope;

import net.sourceforge.tess4j.ITesseract;

import net.sourceforge.tess4j.Tesseract;

import net.sourceforge.tess4j.TesseractException;

@SuppressWarnings("unused")

public class Homepage {

	public static void main(String[] args) throws InterruptedException, IOException, TesseractException {

		// variables

//		String userName="a2zenter";
//		String passWord="Rakesh@51";
		
//		String userName="lizaent";
//		String passWord="Liza@1702";

		String userName = "HITECH11";
		String passWord = "Ghanshyam@123456";

		// Paths

		String downloadfolderPath = "C:\\Users\\pc\\Downloads";
		
		// String folderToMovePath = "C:\\Users\\pc\\Documents\\ackdocs\\liza";
		// String folderToMovePath = "C:\Users\pc\Documents\ackdocs\atoz";

		String folderToMovePath = "C:\\Users\\pc\\Documents\\ackdocs\\hitech";

		WebDriver driver = new ChromeDriver();

		try {

			driver.manage().window().maximize();

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

			driver.get("https://partners.lntecc.com/PartnerMgmtApp/login");

			// login start

			WebElement username = driver.findElement(By.xpath("//*[@id=\"Username\"]"));

			username.sendKeys(userName);

			Thread.sleep(1000);

			WebElement pass = driver.findElement(By.xpath("//input[@onpaste=\"return false\"]"));

			pass.sendKeys(passWord);

			Thread.sleep(1000);

			// start capturing Captcha

			WebElement imagelement = driver.findElement(By.xpath("//canvas[@class=\"vue-captcha-img\"]"));

			File src = imagelement.getScreenshotAs(OutputType.FILE);

			String path = "C:\\Users\\pc\\eclipse-workspace\\LT\\captchaImage.png";

			FileHandler.copy(src, new File(path));

			Thread.sleep(2000);

			String tessDataPath = "C:\\Users\\pc\\eclipse-workspace\\LT\\TestData";

			ITesseract image = new Tesseract();

			Thread.sleep(1000);

			image.setDatapath(tessDataPath);

			Thread.sleep(1000);

			image.setLanguage("eng");

			String str = image.doOCR(new File(path));

			Thread.sleep(1000);

			System.out.println("image OCR done ");

			System.out.println(str);

			String captcha = str.replaceAll("[^a-zA-Z0-9]", "");

			System.out.println(captcha);

			Thread.sleep(6000);

			WebElement captchaTextBox = driver.findElement(By.xpath("//*[@id=\"VueCaptcha\"]/div[2]/input"));

			captchaTextBox.sendKeys(captcha);

			// End capturing Captcha

			Thread.sleep(4000);

			WebElement login = driver.findElement(By.xpath("//button[text()='LOGIN']"));

			login.click();

		} catch (Exception e) {

			System.out.println("exception caught : " + e.getMessage());

		}

		// login End

		Thread.sleep(4000);

		Actions actions = new Actions(driver);

		actions.sendKeys(Keys.PAGE_DOWN).perform();

		Thread.sleep(20000);

		driver.findElement(By.xpath("//option[text()='EIP 4.0 - New EIP']")).click(); // Eip find out

		Thread.sleep(3000);

		driver.findElement(By.xpath("//a[@class=\"mat-menu-trigger modulemenu\"]")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[text()='Supply Chain Management']")).click();

		Thread.sleep(2000);

		driver.findElement(By.xpath("//span[@title=\"Procurement\"]")).click();

		driver.findElement(By.xpath("//span[@title=\"RFQ Bidders Quote\"]")).click();

		Thread.sleep(5000);

		// Acknowledgement page started

		WebElement ackpagedropdown = driver.findElement(By.xpath("//mat-select[@aria-label=\"Items per page:\"]"));

		Actions actions5 = new Actions(driver);

		actions5.moveToElement(ackpagedropdown).click().perform();

		actions5.sendKeys(Keys.END).perform();

		actions5.sendKeys(Keys.ENTER).perform();

		Thread.sleep(6000);

		while ((driver.findElements(By.xpath("//input[@value=\"Acknowledge\"]"))) != null) {

			WebElement ackno = driver.findElement(By.xpath("//input[@value=\"Acknowledge\"]"));

			Actions actions1 = new Actions(driver);

			actions1.sendKeys(Keys.PAGE_DOWN).perform();

			Thread.sleep(3000);

			ackno.click();

			Thread.sleep(2000);

			WebElement checkbox = driver.findElement(By.xpath("//span[@class=\"mat-checkbox-inner-container\"]")); // check
																													// box

			if (!checkbox.isSelected()) { // check if check box is selected or not

				checkbox.click();

			}

			Thread.sleep(2000);

			driver.findElement(By.xpath("//input[@id=\"btnTCSubmit\"]")).click();// submit button

			driver.findElement(By.xpath("//button[@class=\"btn btn-primary btn-sm cursor-pointer\"]")).click(); // ok
																												// button

			Thread.sleep(10000);

			// start capturing data for resoo

			String rfqnumber = driver.findElement(By.xpath("//p[@style=\"cursor: pointer;\"]/..//p[1]")).getText();

			String startdate = driver.findElement(By.xpath("(//p[@class=\"cv-view-data\"])[2]")).getText();

			System.out.println("startdate:" + startdate);

			Thread.sleep(2000);

			String publicoffer = driver.findElement(By.xpath("(//p[@class=\"cv-view-data ng-star-inserted\"])[1]"))
					.getText();

			String bidtype = publicoffer.equals("Yes") ? "Public" : "Private";

			System.out.println("public offer: " + bidtype);

			String buyer = driver.findElement(By.xpath("(//p[@class=\"sub-title\"])[2]")).getText()
					.replaceAll(", Buyer", "");

			System.out.println("buyer:" + buyer);

			String scope = driver.findElement(By.xpath("(//p[@class=\"sub-title\"])[1]")).getText()
					.replaceAll("Scope: ", "");

			System.out.println(scope);

			Thread.sleep(3000);

			String enddate = driver.findElement(By.xpath("(//p[@class=\"cv-view-data\"])[3]")).getText();

			System.out.println("EndDate:" + enddate);

			String phonenumber = driver.findElement(By.xpath("(//p[not(@_ngcontent)])[22]")).getText();

			System.out.println("phone number:" + phonenumber);

			int materialcount = Integer
					.parseInt(driver.findElement(By.xpath("(//p[@class=\"cv-view-data\"])[5]")).getText());

			Thread.sleep(5000);

			System.out.println("materialcount" + materialcount);

			int totalpage = materialcount / 10;

			System.out.println("totalpage" + totalpage);

			if (materialcount % 10 == 0) {

				totalpage = totalpage - 1;

			}

			System.out.println("totalpage" + totalpage);

			int trcount = 0;

			int pageclickleft = totalpage;

			for (int pcount = 0; pcount <= totalpage; pcount++) {

				System.out.println(pcount);

				for (WebElement rates : driver.findElements(By.xpath("//input[@id=\"rfqRate\"]"))) {

					rates.clear();

					rates.sendKeys("2");

					WebElement hsn = driver.findElement(By.xpath("//input[@id=\"actxtHsnCodes" + trcount + "\"]"));

					hsn.sendKeys(Keys.ARROW_DOWN);

					Thread.sleep(2000);

					hsn.sendKeys(Keys.ARROW_DOWN);

					Thread.sleep(1000);

					hsn.sendKeys(Keys.ENTER);

					Thread.sleep(5000);

					WebElement naturecode = driver
							.findElement(By.xpath("//input[@id=\"actxtHsnNatureCodes" + trcount + "\"]"));

					naturecode.sendKeys(Keys.ARROW_DOWN);

					Thread.sleep(2000);

					naturecode.sendKeys(Keys.ARROW_DOWN);

					Thread.sleep(1000);

					naturecode.sendKeys(Keys.ENTER);

					trcount++;

				}

				if (pageclickleft > 0) {

					driver.findElement(By.xpath("//span[@title=\"Go to the next page\"]")).click();

				}

				pageclickleft--;

				Thread.sleep(2000);

			}

			Thread.sleep(5000);

			// open Resoo Website

			JavascriptExecutor js = (JavascriptExecutor) driver;

			((JavascriptExecutor) driver).executeScript("window.open();");

			driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());

			driver.get("https://resoo.co.in/");

			Thread.sleep(5000);

			driver.findElement(By.xpath("//a[text()='Acknowledge']")).click();

			Thread.sleep(4000);

			driver.findElement(By.xpath("//input[@id=\"usr_name\"]")).sendKeys(userName);

			Thread.sleep(5000);

			driver.findElement(By.xpath("//input[@id=\"rfq_no\"]")).sendKeys(rfqnumber);

			driver.findElement(By.xpath("//input[@id=\"rfq_start_date\"]")).sendKeys(startdate);

			Thread.sleep(4000);

			driver.findElement(By.xpath("//input[@id=\"rfq_end_date\"]")).sendKeys(enddate);

			driver.findElement(By.xpath("//input[@id=\"buyer\"]")).sendKeys(buyer);

			Thread.sleep(4000);

			driver.findElement(By.xpath("//input[@id=\"buyer_no\"]")).sendKeys(phonenumber);

			Thread.sleep(4000);

			driver.findElement(By.xpath("//input[@id=\"scope\"]")).sendKeys(scope);

			driver.findElement(By.xpath("//input[@id=\"material_line_items\"]")).sendKeys("" + materialcount);

			driver.findElement(By.xpath("//input[@id=\"bid_type\"]")).sendKeys(bidtype);

			Thread.sleep(4000);

			driver.findElement(By.xpath("//button[text()='Submit']")).click();

			Thread.sleep(10000);

			Alert a = driver.switchTo().alert();

			a.accept();

			Thread.sleep(3000);

			driver.close();

			// close google form

			// Switch back to the first tab

			driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());

			Thread.sleep(3000);

			driver.findElement(By.xpath("//button[text()='Save Draft']")).click();// 1st save draft button

			driver.findElement(By.xpath("//button[text()='Ok']")).click(); // to click in okay button after draft

			Thread.sleep(3000);

			driver.findElement(By.xpath(
					"/html/body/app-root/div/div[2]/app-bqcreationcontainer/lib-eip-bqedit/div/div[2]/app-bqcreatematcardview/div[2]/kendo-grid/kendo-grid-toolbar/div/div[3]/div/div[2]/i"))
					.click();

			Thread.sleep(8000);

			String rfqFileName = rfqnumber.replaceAll("/", "-");

			String newFileName = rfqFileName + ".xlsx";

			renameFiles(downloadfolderPath, newFileName, folderToMovePath);

			driver.findElement(By.xpath("//button[text()='Save Draft']")).click();

			Thread.sleep(5000);

			driver.findElement(By.xpath("//button[text()='Ok']")).click();

			Thread.sleep(5000);

			JavascriptExecutor js1 = (JavascriptExecutor) driver;

			js1.executeScript("window.scrollTo(0,0);");

			Thread.sleep(2000);

			driver.findElement(By.xpath("//i[@class=\"fa fa-times-circle ng-star-inserted\"]")).click();

			Thread.sleep(8000);

			WebElement ackpagedropdowninside = driver
					.findElement(By.xpath("//mat-select[@aria-label=\"Items per page:\"]"));

			Actions actionstemp = new Actions(driver);

			actionstemp.moveToElement(ackpagedropdowninside).click().perform();

			actionstemp.sendKeys(Keys.END).perform();

			actionstemp.sendKeys(Keys.ENTER).perform();

			Thread.sleep(5000);

		}

		// Acknowledgement page ended

		System.out.println("task completed");

		// driver.quit();

	}

	// Method to rename all files in a folder

	@SuppressWarnings("unused")

	private static void renameFiles(String downloadfolderPath, String newFileName, String folderToMovePath) {

		// Create a File object representing the folder

		File downloadfolder = new File(downloadfolderPath);

		// Check if the specified path is a directory

		if (downloadfolder.isDirectory()) {

			// List all files in the directory

			File[] files = downloadfolder.listFiles();

			// Check if there are any files in the directory

			if (files != null) {

				// Iterate through each file and rename it

				for (File file : files) {

					// Create a new File object with the updated name

					File newFile = new File(downloadfolderPath, newFileName);

					// Perform the renaming operation

					if (file.renameTo(newFile)) {

						System.out.println("File renamed successfully: " + newFile.getAbsolutePath());

					} else {

						System.err.println("Error renaming file: " + file.getAbsolutePath());

					}

				}

			} else {

				System.out.println("No files found in the directory.");

			}

		} else {

			System.err.println("Invalid directory path: " + downloadfolderPath);

		}

		try {

			Path sourcePath = Paths.get(downloadfolderPath, newFileName);

			Path destinationPath = Paths.get(folderToMovePath, newFileName);

			// Perform the move operation

			Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

			System.out.println("File moved successfully!");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

}