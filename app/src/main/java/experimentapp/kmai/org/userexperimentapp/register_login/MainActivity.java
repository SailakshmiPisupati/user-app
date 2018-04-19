package experimentapp.kmai.org.userexperimentapp.register_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.practice_session.CheckTutorialCompleted;
import experimentapp.kmai.org.userexperimentapp.practice_session.InstructionPageActivity;
import experimentapp.kmai.org.userexperimentapp.register_login.LoginActivity;
import experimentapp.kmai.org.userexperimentapp.register_login.SignUpActivity;
import experimentapp.kmai.org.userexperimentapp.setting_selector.SettingOptionLayoutActivity;

/**
 * Created by saila on 2/23/18.
 */

public class MainActivity extends Activity {
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedinstancestate){
        super.onCreate(savedinstancestate);

        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        String email_id = sharedpreferences.getString("email_id","");

        if(email_id == ""){
            //call register user
            Intent registerIntent = new Intent(getApplicationContext(),InstructionPageActivity.class);
            registerIntent.putExtra("message_to_display","pre_exp_survey_1");
            startActivity(registerIntent);
        }else{
            //call the activity to collect data
            Intent rawTouchDataIntent = new Intent(getApplicationContext(), CheckTutorialCompleted.class);
            startActivity(rawTouchDataIntent);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
