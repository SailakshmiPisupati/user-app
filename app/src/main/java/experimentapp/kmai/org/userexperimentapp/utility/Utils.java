package experimentapp.kmai.org.userexperimentapp.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by saila on 2/20/18.
 */

public class Utils {
    public static String userid;
    public static String deviceID;
    public static String password;
    public final static int minimumpasswordLength = 8;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Utils.password = password;
    }

    static public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");
        Date date = new Date();
        return  dateFormat.format(date);
    }

    public static String encodeUserEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    public static String decodeUserEmail(String userEmail) {
        return userEmail.replace(",", ".");
    }


    public static String getDeviceID() {
        return deviceID;
    }

    public static void setDeviceID(String deviceID) {
        Utils.deviceID = deviceID;
    }

}
