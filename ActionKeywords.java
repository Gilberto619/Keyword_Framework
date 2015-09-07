package operation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.Log;

public class ActionKeywords {
	WebDriver driver;
	public static WebElement element;
	final static String frame_Path = System.getProperty("user.dir")+"\\src\\outputFiles\\";
	final static String IE_DriverPath = frame_Path+"\\browserDrivers\\IEDriverServer.exe";
	final static String Chrome_DriverPath = frame_Path+"\\browserDrivers\\chromedriver.exe";
	static String file;
	
    public ActionKeywords(WebDriver driver){
        this.driver = driver;
    }
    
    public void perform(Properties p,String operation,String objectName,String objectType, String caseAction, String data) throws Exception{
        System.out.println("");
/*################################################################################ Common Comands ##################################################################
* 
*Methods with all the common commands. 
* 
*##################################################################################################################################################################*/
        if(operation.toUpperCase().equals("NAVIGATE")){
        	 //Get url of application
        	try{
        		Log.info("Navigating to URL: " + data);
                driver.get(data);
    			Log.info("Screenshot created in: "+file);
        	}catch(Exception e){
    			Log.error("Not able to navigate --- " + e.getMessage());
    			//Taking Screen Shot on Error
    			file = frame_Path+"navigateError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			takeSnapShot(driver, file);
    			Log.error("Screenshot created in: "+file);
    		}
        }else if(operation.toUpperCase().equals("CLICK")){
        	//Perform click
        	try{
        		Log.info("Clicking on Webelement "+ objectName);
        		element = driver.findElement(this.getObject(p,objectName,objectType));
        		fnHighlightMe(driver,element);
    			element.click();
        	}catch(Exception e){
    			Log.error("Not able to click --- " + e.getMessage());
     			//Taking Screen Shot on Error
     			file = frame_Path+"clickError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
     			takeSnapShot(driver, file);			      					      
    			Log.error("Screenshot created in: "+file);
    		}
        }else if(operation.toUpperCase().equals("SETTEXT")){
        	 //Set text on control
        	try{
        		Log.info("Entering the text: " + data + " in " + objectName);
        		element = driver.findElement(this.getObject(p,objectName,objectType));
        		fnHighlightMe(driver,element);
    			element.sendKeys(data);
        	}catch(Exception e){
   			 Log.error("Not able to Enter --- " + e.getMessage());
 			//Taking Screen Shot on Error
 			 file = frame_Path+"inputError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
 			 takeSnapShot(driver, file);
 			 Log.error("Screenshot created in: "+file);
 		 }  
        }else if(operation.toUpperCase().equals("CLEAR")){
        	//Clear text on control
        	try{
        		Log.info("Cleaning: " + objectName);
    			element = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,element);
    			element.clear();
        	}catch(Exception e){
   			 Log.error("Not able to Clear --- " + e.getMessage());
 			//Taking Screen Shot on Error
 			 file = frame_Path+"clearError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
 			 takeSnapShot(driver, file);
 			 Log.error("Screenshot created in: "+file);
 		 }
        }else if(operation.toUpperCase().equals("WAITFOR")){
        	  //Wait
        	try{
    			double value_timedouble = Double.parseDouble(data);
    			int value_time = (int)value_timedouble;
    			Thread.sleep(value_time*1000);
    			Log.info("Wait for " + data + " seconds");
    		 }catch(TimeoutException e){
    			 Log.error("Not able to Wait --- " + e.getMessage());
    		 }
        }else if(operation.toUpperCase().equals("IMPLICITWAIT")){
        	//implicit wait
        	try{
    			driver.manage().timeouts().implicitlyWait(Long.parseLong(data), TimeUnit.valueOf(caseAction.toUpperCase()));
    			Log.info("Wait for " + data + caseAction);
    		 }catch(TimeoutException e){
    			 Log.error("Not able to Wait --- " + e.getMessage());
    		 }
        }else if(operation.toUpperCase().equals("EXPLICITWAIT")){
        	 //Explicit Wait
        	try{
    			WebDriverWait waitVar = new WebDriverWait(driver,10);
    			Log.info("Waiting for element: " + objectName);
    			switch (caseAction.toUpperCase()){
    				case "ALERT_PRESENT":
    					waitVar.until(ExpectedConditions.alertIsPresent());
    					break;
    				case "ELEMENT_CLICKABLE":
    					waitVar.until(ExpectedConditions.elementToBeClickable(this.getObject(p,objectName,objectType)));
    					break;
    				case "ELEMENT_TOBESELECTED":
    					waitVar.until(ExpectedConditions.elementToBeSelected(this.getObject(p,objectName,objectType)));
    					break;
    				case "FRAME_AVAILABE_ANDSWITCH":
    					waitVar.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(this.getObject(p,objectName,objectType)));
    					break;
    				case "INVISIBILITY_ELEMENT":
    					waitVar.until(ExpectedConditions.invisibilityOfElementLocated(this.getObject(p,objectName,objectType)));
    					break;
    				case "PRESENCE_ALL_ELEMENTS":
    					waitVar.until(ExpectedConditions.presenceOfAllElementsLocatedBy(this.getObject(p,objectName,objectType)));
    					break;
    				case "PRESENCE_ELEMENT":
    					waitVar.until(ExpectedConditions.presenceOfElementLocated(this.getObject(p,objectName,objectType)));
    					break;
    				case "TEXT_TOBE_PRESENT":
    					waitVar.until(ExpectedConditions.textToBePresentInElementLocated(this.getObject(p,objectName,objectType), data));
    					break;
    				case "TITLE_CONTAINS":
    					waitVar.until(ExpectedConditions.titleContains(data));
    					break;
    				case "URL_MATCH":
    					waitVar.until(ExpectedConditions.urlToBe(data));
    					break;
    				case "VISIBILITY_ALL_ELEMENTS":
    					waitVar.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(this.getObject(p,objectName,objectType)));
    					break;
    				case "VISIBILITY_ELEMENT":
    					waitVar.until(ExpectedConditions.visibilityOfElementLocated(this.getObject(p,objectName,objectType)));
    					break;
    				default:
    					Log.error("Invalid Option!");
    					break;
    			}
        	}catch(NoSuchElementException  e){
   			 Log.error("Not element to Wait --- " + e.getMessage());
 			//Taking Screen Shot on Error
 			 file = frame_Path+"explicitWaitError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
 			 takeSnapShot(driver, file);
 			 Log.error("Screenshot created in: "+file);
 		 }catch(TimeoutException e){
 			 Log.error("Not able to Wait --- " + e.getMessage());
 		 }
        }else if(operation.toUpperCase().equals("CLOSEBROWSER")){
        	try{
    			Log.info("Closing the browser");
    			Log.endTestCase();
    			driver.quit();
    		 }catch(Exception e){
    			 Log.error("Not able to Close the Browser --- " + e.getMessage());
    		 }
        }else if(operation.toUpperCase().equals("SUBMITFORM")){
        	try{
    			Log.info("Form Submitted");
    			element = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,element);
    			element.submit();
    		 }catch(Exception e){
    			 Log.error("Not able to Submit the Form --- " + e.getMessage());
    			//Taking Screen Shot on Error
    			 file = frame_Path+"submitForm_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			 takeSnapShot(driver, file);
    			 Log.error("Screenshot created in: "+file);		
    		 }
        }else if(operation.toUpperCase().equals("SWITCH_TO_FRAME")){
        	try{Log.info("Switch to Frame: " + data);
			driver.switchTo().frame(data); //in data include the name of the frame
		 }catch(Exception e){
			 Log.error("Not able to Switch beteween frames --- " + e.getMessage());
			//Taking Screen Shot on Error
			 file = frame_Path+"switchToFrameError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
			 takeSnapShot(driver, file);
			 Log.error("Screenshot created in: "+file);
		 }
        }else if(operation.toUpperCase().equals("SWITCH_TO_WINDOW")){
        	try{
    			Log.info("Switch to window: " + data);
    			driver.switchTo().window(data); //in data include the name of the window
    		 }catch(Exception e){
    			 Log.error("Not able to Switch to Window --- " + e.getMessage());
    			//Taking Screen Shot on Error
    			 file = frame_Path+"switchToWindow_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			 takeSnapShot(driver, file);
    			 Log.error("Screenshot created in: "+file);	
    		 }
        }else if(operation.toUpperCase().equals("SCROLLWINDOW")){
        	try{
				JavascriptExecutor jse = (JavascriptExecutor)driver;
				jse.executeScript("window.scrollBy("+data+")", "");
				Log.info("Scroll executed!");
			 }catch(TimeoutException e){
				 Log.error("Not able to Scroll --- " + e.getMessage());
			 }
        }else if(operation.toUpperCase().equals("SWITCH_TO_ALERT")){
        	try{
    			switch (caseAction.toUpperCase()) {
    			case "ACCEPT":
    				driver.switchTo().alert().accept();
    				break;
    			case "AUTHENTICATE":
    				String string = data; //introduce the string as user,password in excel sheet
    				String[] parts = string.split(",");
    				String user = parts[0]; //user
    				String password = parts[1]; //password
    				driver.switchTo().alert().authenticateUsing(new UserAndPassword(user, password));
    				Log.info("Alert authentication wit credentials: " + user + "," + password);
    				break;
    			case "DISMISS":
    				driver.switchTo().alert().dismiss();
    				Log.info("Alert window dismissed");
    				break;
    			case "ALERT_GETTEXT":
    				if (data.isEmpty()){
    					Log.info("Alert window text is: " + driver.switchTo().alert().getText());
    				}
    				else if (!data.isEmpty() && driver.switchTo().alert().getText().equals(data)){
    					Log.info(driver.switchTo().alert().getText() + " is equal to: " + data);
    				}else{
    					Log.error(driver.switchTo().alert().getText() + " is not equal to: " + data);
    				}
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}
    		 }catch(Exception e){
    			 Log.error("Not able to Submit the Form --- " + e.getMessage());
    			//Taking Screen Shot on Error
    			 file = frame_Path+"switchToAlertError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			 takeSnapShot(driver, file);
    			 Log.error("Screenshot created in: "+file);
    		 }
        }else if(operation.toUpperCase().equals("SELECT_DROPDOWN")){
        	try{
    			Log.info("Selecting " + data +" from DropDown box: " + objectName);
    			element = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,element);
    			Select object_select = new Select(driver.findElement(this.getObject(p,objectName,objectType)));
    			switch (caseAction.toUpperCase()) {
    			case "VISIBLE_TEXT":
    				object_select.selectByVisibleText(data);
    				break;
    			case "VALUE":
    				object_select.selectByValue(data);
    				break;
    			case "INDEX":
    				object_select.selectByIndex(Integer.parseInt(data));
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}
    		}catch(Exception e){
    			Log.error("Not able to Select --- " + e.getMessage());
    			//Taking Screen Shot on Error	
    			file = frame_Path+"dropdownError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			takeSnapShot(driver, file);
    			Log.error("Screenshot created in: "+file);	
    		}
        }else if(operation.toUpperCase().equals("DESELECT_DROPDOWN")){
        	try{
    			Log.info("Deselecting " + data +" from DropDown box: " + objectName);
    			element = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,element);
    			Select object_deselect = new Select(driver.findElement(this.getObject(p,objectName,objectType)));	
    			switch (caseAction.toUpperCase()) {
    			case "VISIBLE_TEXT":
    				object_deselect.deselectByVisibleText(data);
    				break;
    			case "VALUE":
    				object_deselect.deselectByValue(data);
    				break;
    			case "INDEX":
    				object_deselect.deselectByIndex(Integer.parseInt(data));
    				break;
    			case "ALL":
    				object_deselect.deselectAll();
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}
    		}catch(Exception e){
    			Log.error("Not able to Deselect --- " + e.getMessage());
    			//Taking Screen Shot on Error	 
    			file = frame_Path+"dropdownError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			takeSnapShot(driver, file);
    			Log.error("Screenshot created in: "+file);	
    		}
        }else if(operation.toUpperCase().equals("MULTIPLE_GET_OBJECT")){
        	try{
    			element = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,element);	
    			switch (caseAction.toUpperCase()) {
    			case "GET_CLASS":
    				if (data.isEmpty()){
    					Log.info("Getting the class: " + element.getClass() + " from " + objectName);
    				}
    				else if (data != null && element.getClass().equals(data)){
    					Log.info(element.getClass() + " is equal to: " + data);
    				}else{
    					Log.error(element.getClass() + " is not equal to: " + data);
    				}
    				break;
    						
    			case "GET_LOCATION":
    				if (data.isEmpty()){
    					Log.info("Getting the location: " + element.getLocation() + " of " + objectName);
    				}
    				else if (data != null && element.getLocation().equals(data)){
    					Log.info(element.getLocation() + " is equal to: " + data);
    				}else{
    					Log.error(element.getLocation() + " is not equal to: " + data);
    				}
    				break;

    			case "GET_SIZE":
    				if (data.isEmpty()){
    					Log.info("Getting the size: " + element.getSize() + " of " + objectName);
    				}
    				else if (data != null && element.getSize().equals(data)){
    					Log.info(element.getSize() + " is equal to: " + data);
    				}else{
    					Log.error(element.getSize() + " is not equal to: " + data);
    				}
    				break;
    						
    			case "GET_TAGNAME":
    				if (data.isEmpty()){
    					Log.info("Getting the tag name: " + element.getTagName() + " from " + objectName);
    				}
    				else if (data != null && element.getTagName().equals(data)){
    					Log.info(element.getTagName() + " is equal to: " + data);
    				}else{
    					Log.error(element.getTagName() + " is not equal to: " + data);
    				}
    				break;
    						
    			case "GET_TEXT":	
    				if (data.isEmpty()){
    					Log.info("Getting the text: " + element.getText() + " from " + objectName);
    				}
    				else if (data != null && element.getText().equals(data)){
    					Log.info(element.getText() + " is equal to: " + data);
    				}else{
    					Log.error(element.getText() + " is not equal to: " + data);
    				}
    				break;
    				
    			case "GET_ATTRIBUTE":
    				Log.info("Getting the text: " + element.getAttribute(data) + " from " + objectName);
    				break;	
    				
    			case "GET_CSSVALUE":
    				Log.info("Getting the text: " + element.getCssValue(data) + " from " + objectName);
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}
    		}catch(Exception e){
    			Log.error("Not able to use Get command --- " + e.getMessage());
    			//Taking Screen Shot on Error
    			file = frame_Path+"multObjGetError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			takeSnapShot(driver, file);
    			Log.error("Screenshot created in: "+file);		
    		}
        }else if(operation.toUpperCase().equals("MULTIPLE_GET_PAGE")){
        	try{
    			switch (caseAction.toUpperCase()) {
    			case "GET_CLASS":
    					if (data.isEmpty()){
    						Log.info("Getting the class: " + driver.getClass());
    					}
    					else if (data != null && driver.getClass().equals(data)){
    						Log.info(driver.getClass() + " is equal to: " + data);
    					}else{
    						Log.error(driver.getClass() + " is not equal to: " + data);
    					}
    				break;
    						
    			case "GET_CURRENTURL":
    				if (data.isEmpty()){
    					Log.info("Getting the current URL: " + driver.getCurrentUrl());
    				}
    				else if (data != null && driver.getCurrentUrl().equals(data)){
    					Log.info(driver.getCurrentUrl() + " is equal to: " + data);
    				}else{
    					Log.error(driver.getCurrentUrl() + " is not equal to: " + data);
    				}
    				break;

    			case "GET_PAGESOURCE":
    				if (data.isEmpty()){
    					Log.info("Getting the page source: " + driver.getPageSource());
    				}
    				else if (data != null && driver.getPageSource().equals(data)){
    					Log.info(driver.getPageSource() + " is equal to: " + data);
    				}else{
    					Log.error(driver.getPageSource() + " is not equal to: " + data);
    				}
    				break;
    						
    			case "GET_TITLE":
    				if (data.isEmpty()){
    					Log.info("Getting the page title: " + driver.getTitle());
    				}
    				else if (data != null && driver.getTitle().equals(data)){
    					Log.info(driver.getTitle() + " is equal to: " + data);
    				}else{
    					Log.error(driver.getTitle() + " is not equal to: " + data);
    				}
    				break;
    						
    			case "GET_WINDOWHANDLE":
    				Log.info("Getting the window handle: " + driver.getWindowHandle());
    				break;

    			case "GET_WINDOWHANDLES":
    				Log.info("Getting the window handles: " + driver.getWindowHandles());
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}		
    		}catch(Exception e){
    			Log.error("Not able to use Get command --- " + e.getMessage());
    		}
        }else if(operation.toUpperCase().equals("SELECT_CONDITION")){
        	try{
    			WebElement obj_condition = driver.findElement(this.getObject(p,objectName,objectType));
    			fnHighlightMe(driver,obj_condition);
    			switch (caseAction.toUpperCase()) {
    			case "IS_ENABLED":
    					if (obj_condition.isEnabled()){
    						Log.info(objectName + " is Enabled");
    					}else{
    						Log.error(objectName + " is not Enabled");
    					}
    				break;
    						
    			case "IS_DISPLAYED":
    				if (obj_condition.isDisplayed()){
    					Log.info(objectName + " is Displayed");
    				}else{
    					Log.error(objectName + " is not Displayed");
    				}
    				break;

    			case "IS_SELECTED":
    				if (obj_condition.isSelected()){
    					Log.info(objectName + " is Selected");
    				}else{
    					Log.error(objectName + " is not Selected");
    				}
    				break;
    			default:
    				Log.error("Invalid Option!");
    				break;
    			}
    		}catch(Exception e){
    			Log.error("Not able to evaluate a condition --- " + e.getMessage());
    			//Taking Screen Shot  on Error 
    			file = frame_Path+"selCondError_"+(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime())).toString()+".png";
    			takeSnapShot(driver, file);
    			Log.error("Screenshot created in: "+file);
    		}
        }
//####################################################################################################################################################################
    }
     
    /**
	 * Find element BY using object type and value
	 * @param p
	 * @param object
	 * @param objectType
	 * @return 
	 * @throws Exception
	 */
	public By getObject(Properties p,String object,String objectType) throws Exception{
		
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){
			return By.xpath(p.getProperty(object));
		}
		//Find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){
			return By.className(p.getProperty(object));
		}
		//Find by name
		else if(objectType.equalsIgnoreCase("NAME")){
			return By.name(p.getProperty(object));
		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){
			return By.cssSelector(p.getProperty(object));
		}
		//Find by link
		else if(objectType.equalsIgnoreCase("LINK")){
			return By.linkText(p.getProperty(object));
		}
		//Find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){
			return By.partialLinkText(p.getProperty(object));
		}
		//Find by id
		else if(objectType.equalsIgnoreCase("ID")){
			return By.id(p.getProperty(object));
		}
		//Find by Tag Name
		else if(objectType.equalsIgnoreCase("TAGNAME")){
			return By.tagName(p.getProperty(object));
		}else
		{
			throw new Exception("Wrong object type");
		}
	}
	
	 /**
     * This function will take screenshot
     * 
     * @param webdriver
     * @param fileWithPath
     * 
     * @throws Exception
     */
    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws NullPointerException{
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        try{ //Call getScreenshotAs method to create image file
                File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
            //Move image file to new destination
                File DestFile=new File(fileWithPath);
                //Copy file at destination
                FileUtils.copyFile(SrcFile, DestFile);
        } catch (Exception e) {
        	Log.error(e.getMessage());
        }
    }
    
	/**
	 * HighLight an element
	 * 
	 * @param driver
	 * @param element
	 * 
	 * @throws InterruptedException
	 */
	public static void fnHighlightMe(WebDriver driver,WebElement element) throws InterruptedException{
		//Creating JavaScriptExecuter Interface
		JavascriptExecutor js = (JavascriptExecutor)driver;
		for (int iCnt = 0; iCnt < 3; iCnt++) {
			//Execute javascript
			js.executeScript("arguments[0].setAttribute('style','border: groove 4px yellow')", element);
			Thread.sleep(1000);
			js.executeScript("arguments[0].setAttribute('style','border:')", element);
		}
	}
}
