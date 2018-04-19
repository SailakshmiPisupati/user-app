package experimentapp.kmai.org.userexperimentapp.setting_selector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

import experimentapp.kmai.org.userexperimentapp.practice_session.InstructionPageActivity;
import experimentapp.kmai.org.userexperimentapp.practice_session.PracticeActivity;
import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.register_login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener {
//    private static int SPLASH_TIME_OUT = 3000;
    TextView instruction;
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initialiseTextToSpeech();
        instruction = findViewById(R.id.splash_screen);
        Button button = findViewById(R.id.confirm_session_instruction);
        Button splash_screen_help = findViewById(R.id.splash_screen_help_menu_button);

        button.setOnClickListener(this);
        splash_screen_help.setOnClickListener(this);
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        String current_session = sharedpreferences.getString("current_session","");

        setText(Integer.parseInt(current_session));

        Log.d("SharedPreferences","USER ID after saving is "+sharedpreferences.getString("email_id",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("password",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("current_session",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_1",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_2",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_3",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_4",""));
    }

    public void setText(int session){
        switch (session){
            case 1 :
                instruction.setText(R.string.splash_screen_instruction_1);
                break;
            case 2 :
                instruction.setText(R.string.splash_screen_instruction_2);
                break;
            case 3 :
                instruction.setText(R.string.splash_screen_instruction_3);
                break;
            case 4:
                instruction.setText(R.string.splash_screen_instruction_4);
                break;
            default:
                instruction.setText("session value: " + session);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.confirm_session_instruction:
                Intent login_activity = new Intent(SplashScreenActivity.this, LoginActivity.class);
                if(textToSpeech!=null){
                    textToSpeech.shutdown();
                    textToSpeech.stop();
                }
                startActivity(login_activity);
                break;
            case R.id.splash_screen_help_menu_button:
                Intent help_intent = new Intent(SplashScreenActivity.this, InstructionPageActivity.class);
                if(textToSpeech!=null){
                    textToSpeech.shutdown();
                    textToSpeech.stop();
                }
                help_intent.putExtra("message_to_display","help_keyboard_switch");
                startActivity(help_intent);
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            String toSpeak = instruction.getText().toString();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            String toSpeak = getString(R.string.splashscreen_volumne_down_instruction);
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        } else {

            return false;
        }
        return true;
    }

    public void initialiseTextToSpeech(){
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }
}
