package operation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadObject {
	final static String frame_Path = System.getProperty("user.dir")+"\\src";
	Properties p = new Properties();
    public Properties getObjectRepository() throws IOException{
        //Read object repository file
        InputStream stream = new FileInputStream(new File(frame_Path+"\\inputFiles\\ObjRep.txt"));
        //load all objects
        p.load(stream);
         return p;
    }
}
