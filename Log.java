package utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

	public class Log {

		//Initialize Log4j logs
		private static Logger Log = Logger.getLogger(Log.class.getName());// 

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static void startTestCase(String sTestCaseName){

	   Log.info("****************************************************************************************");
	   Log.info("****************************************************************************************");
	   Log.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
	   Log.info("****************************************************************************************");
	   Log.info("****************************************************************************************");

	   }

	//This is to print log for the ending of the test case, before sTestCaseName
	public static void endTestCase(){
	   Log.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
	   Log.info("X");
	   Log.info("X");
	   Log.info("X");
	   Log.info("X");

	   }

    // Need to create these methods, so that they can be called  
	public static void info(String message) {
		   Log.info(message);
		   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()).toString() + " INFO [lOG] " + message);
		   }

	public static void warn(String message) {
	   Log.warn(message);
	   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()).toString() + " WARN [lOG] " + message);
	   }

	public static void error(String message) {
	   Log.error(message);
	   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()).toString() + " ERROR [lOG] " + message);
	   }

	public static void fatal(String message) {
	   Log.fatal(message);
	   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()).toString() + " FATAL [lOG] " + message);
	   }

	public static void debug(String message) {
	   Log.debug(message);
	   System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(Calendar.getInstance().getTime()).toString() + " DEBUG [lOG] " + message);
	   }

	}
