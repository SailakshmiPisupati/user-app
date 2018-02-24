package experimentapp.kmai.org.userexperimentapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by saila on 2/23/18.
 */
@IgnoreExtraProperties
public class User {
    static public String email;
    static public String password;
    static public String registration_date;
    static public String device_id;

    public User(){

    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getRegistration_date() {
        return registration_date;
    }

    public static void setRegistration_date(String registration_date) {
        User.registration_date = registration_date;
    }

    public static String getDevice_id() {
        return device_id;
    }

    public static void setDevice_id(String device_id) {
        User.device_id = device_id;
    }
}
