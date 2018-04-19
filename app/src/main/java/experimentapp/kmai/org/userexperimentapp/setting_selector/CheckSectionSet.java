package experimentapp.kmai.org.userexperimentapp.setting_selector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.practice_session.MainHomeActivity;
import experimentapp.kmai.org.userexperimentapp.register_login.LoginActivity;
import experimentapp.kmai.org.userexperimentapp.register_login.MainActivity;
import experimentapp.kmai.org.userexperimentapp.register_login.SignUpActivity;

/**
 * Created by saila on 4/10/18.
 */

public class CheckSectionSet extends Activity {
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedinstancestate){
        super.onCreate(savedinstancestate);

//        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.current_session_setting), Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        String session_sequence = sharedpreferences.getString("session_sequence","");

        if(session_sequence == ""){
            //call register user
            Intent settingIntent = new Intent(getApplicationContext(),SettingOptionLayoutActivity.class);
            startActivity(settingIntent);
        }else{
            //call the activity to collect data
            Intent registerIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(registerIntent);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
