package experimentapp.kmai.org.userexperimentapp.practice_session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.register_login.MainActivity;

/**
 * Created by saila on 4/10/18.
 */

public class CheckTutorialCompleted extends Activity {

    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        String sharedPref_tutorial_completed = sharedpreferences.getString("tutorial_reminder","");

        if(sharedPref_tutorial_completed == ""){
            //call register user
            Intent settingIntent = new Intent(getApplicationContext(),InstructionPageActivity.class);
            settingIntent.putExtra("message_to_display","tutorial_reminder");
            startActivity(settingIntent);
        }else{
            //call the activity to collect data
            Intent registerIntent = new Intent(getApplicationContext(),MainHomeActivity.class);
            startActivity(registerIntent);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
