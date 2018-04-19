package experimentapp.kmai.org.userexperimentapp.practice_session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.register_login.SignUpActivity;
import experimentapp.kmai.org.userexperimentapp.setting_selector.SplashScreenActivity;
import experimentapp.kmai.org.userexperimentapp.utility.Utils;

import static android.view.KeyEvent.keyCodeToString;

public class InstructionPageActivity extends AppCompatActivity {
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;
    //TexttoSpeech
    TextToSpeech textToSpeech;
    TextView message_to_display_textview;
    String toSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_page);
        initialiseTextToSpeech();

        message_to_display_textview = findViewById(R.id.message_to_display_textview);
        Button button = findViewById(R.id.continue_button);

        Bundle bundle = getIntent().getExtras();
        String message_to_display = bundle.getString("message_to_display");

        switch(message_to_display){
            case "pre_exp_survey_1":
                toSpeak = getString(R.string.pre_exp_survey_message_1);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                message_to_display_textview.setText(getString(R.string.pre_exp_survey_message_1));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(InstructionPageActivity.this, SignUpActivity.class);
                        startActivity(registerIntent);
                    }
                });
                break;
            case "tutorial_reminder":
                toSpeak = getString(R.string.tutorial_reminder);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                message_to_display_textview.setText(getString(R.string.tutorial_reminder));
                //set tutorial done in shared preferences.
                sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("tutorial_reminder","done");
                editor.apply();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(InstructionPageActivity.this, MainHomeActivity.class);
                        startActivity(registerIntent);
                    }
                });
                break;
            case "help_keyboard_switch":
                toSpeak = getString(R.string.help_instructons);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                message_to_display_textview.setText(getString(R.string.help_instructons));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(InstructionPageActivity.this, SplashScreenActivity.class);
                        startActivity(registerIntent);
                    }
                });
                break;
            case "help_instructions":
                toSpeak = getString(R.string.help_instructons);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                message_to_display_textview.setText(getString(R.string.help_instructons));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(InstructionPageActivity.this, SplashScreenActivity.class);
                        startActivity(registerIntent);
                    }
                });
                break;
            case "post_exp_survey1":
                toSpeak = getString(R.string.help_instructons);
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                message_to_display_textview.setText(getString(R.string.post_exp_survey_message_1));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent registerIntent = new Intent(InstructionPageActivity.this, SplashScreenActivity.class);
                        startActivity(registerIntent);
                    }
                });
                break;
        }

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            //get Next phrase
            String toSpeak = "Press continue to start session.";
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            // read the phrase
            String toSpeak = message_to_display_textview.getText().toString();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
        return true;
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }
//
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }


}
