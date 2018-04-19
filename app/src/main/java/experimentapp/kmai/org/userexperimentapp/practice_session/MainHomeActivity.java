package experimentapp.kmai.org.userexperimentapp.practice_session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.setting_selector.SplashScreenActivity;

public class MainHomeActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        initialiseTextToSpeech();
        TextView textView = findViewById(R.id.mainhomepage_message);
        textView.setText(getString(R.string.first_time_main_page_message));

//        String toSpeak = getString(R.string.welcome_message) + getString(R.string.first_time_main_page_message);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        Button start_button = findViewById(R.id.start_experiment_button);
        Button help_button = findViewById(R.id.help_button);

        start_button.setOnClickListener(this);
        help_button.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        String current_phrase = sharedpreferences.getString("current_phrase","0");

        if(Integer.parseInt(current_phrase.trim()) > 0){
            Intent intent = new Intent(this,PracticeActivity.class);
            startActivity(intent);
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(textToSpeech!=null){
            textToSpeech.shutdown();
            textToSpeech.stop();
        }

    }
    @Override
    protected void onStop()
    {
        super.onStop();

        if(textToSpeech != null){
            textToSpeech.shutdown();
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_experiment_button:
                Intent splash_intent = new Intent(this, SplashScreenActivity.class);
                if(textToSpeech!=null){
                    textToSpeech.shutdown();
                    textToSpeech.stop();
                }
                startActivity(splash_intent);
                break;
            case R.id.help_button:
                Intent instruction_intent = new Intent(this, InstructionPageActivity.class);
                if(textToSpeech!=null){
                    textToSpeech.shutdown();
                    textToSpeech.stop();
                }
                instruction_intent.putExtra("message_to_display","help_instructions");
                startActivity(instruction_intent);
                break;
        }

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            //get Next phrase

        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            String toSpeak = getString(R.string.welcome_message) + getString(R.string.first_time_main_page_message);
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            // read the phrase
        } else {

            return false;
        }
        return true;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        String toSpeak = getString(R.string.first_time_main_page_message);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        String toSpeak = getString(R.string.first_time_main_page_message);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }

}
