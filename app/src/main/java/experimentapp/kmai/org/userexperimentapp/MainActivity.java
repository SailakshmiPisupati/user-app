package experimentapp.kmai.org.userexperimentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by saila on 2/23/18.
 */

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedinstancestate){
        super.onCreate(savedinstancestate);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.userid), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.userid),"");

        if(username == ""){
            //call register user
            Intent registerIntent = new Intent(getApplicationContext(),SignUpActivity.class);
            startActivity(registerIntent);
        }else{
            //call the activity to collect data
            Intent rawTouchDataIntent = new Intent(getApplicationContext(),PracticeActivity.class);
            startActivity(rawTouchDataIntent);
        }
    }
}
