package experimentapp.kmai.org.userexperimentapp.practice_session;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.setting_selector.ModeSectionValues;
import experimentapp.kmai.org.userexperimentapp.setting_selector.SplashScreenActivity;
import experimentapp.kmai.org.userexperimentapp.utility.PhrasesDictionary;
import experimentapp.kmai.org.userexperimentapp.utility.Utils;

import static android.view.KeyEvent.keyCodeToString;
import static experimentapp.kmai.org.userexperimentapp.utility.Utils.encodeUserEmail;

/**
 * Created by saila on 2/23/18.
 */

public class PracticeActivity extends Activity{

    //asset file variables
    PhrasesDictionary dictionary;
    int phraseCount = 0, phraseTotal;
    int sectionStart, sectionEnd;

    //UI components
    EditText practice_text;
    TextView text_practice_instruction;
    Button survey, next;

    //Firebase references
    DatabaseReference mDatabase;

    //TexttoSpeech
    TextToSpeech textToSpeech;

    public static final String TAG = "practicepage";

    //SharedPreferences attributes
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;
    String current_session;
    String session_1;
    String session_2;
    String session_3;
    String session_4;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchSharedPreferenceValues();  //fetch and set sharedPreference values
        setContentView(R.layout.practice_layout);
        initialiseTextToSpeech();

        FirebaseDatabase database;

        setUpPracticePage();

        Log.d(TAG,sectionStart+" "+sectionEnd);
        Log.d(TAG+" user is ", encodeUserEmail(username));
        Log.d(TAG+" current ", encodeUserEmail(current_session));
        Log.d(TAG+" session1 ", encodeUserEmail(session_1));
        Log.d(TAG+" session2 ", encodeUserEmail(session_2));
        Log.d(TAG+" session3 ", encodeUserEmail(session_3));
        Log.d(TAG+" session4 ", encodeUserEmail(session_4));

        if(username == ""){
            fetchSharedPreferenceValues();
        }else{
            database = FirebaseDatabase.getInstance();
            mDatabase = database.getReference(encodeUserEmail(username));

        }


        practice_text = findViewById(R.id.practice_text_edittext);
        practice_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if((start + count - 1) >= 0) {
                    char ch = charSequence.charAt(start + count - 1);
                    Log.d(TAG+"Key Pressed", ch + " : " + Utils.getTime());
                    mDatabase.child("Keyboard Session").child(Utils.getTime()).setValue(Character.toString(ch));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        text_practice_instruction = findViewById(R.id.text_practice_instruction);

        next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextText();
            }
        });
    }


    public void setUpPracticePage(){
        //set all the components
        promptPageLoadDetail();
        loadPhrases();                  //load the phrases from assets file
        setSessionAttributes();         //set the session attributes.
    }

    public void promptPageLoadDetail(){
        String toSpeak = "This is the practice page. Press volume down to start the session. Press volume up to read the phrase aloud.";
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void fetchSharedPreferenceValues(){
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        username = sharedpreferences.getString("email_id","");
        current_session = sharedpreferences.getString("current_session","");
        session_1 = sharedpreferences.getString("session_1","");
        session_2 = sharedpreferences.getString("session_2","");
        session_3 = sharedpreferences.getString("session_3","");
        session_4 = sharedpreferences.getString("session_4","");

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

    public void loadPhrases(){
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("phrases.txt");
            dictionary = new PhrasesDictionary(new InputStreamReader(inputStream));
            phraseTotal = PhrasesDictionary.getphraseCount();
            Log.d(TAG+" StrokeIME", dictionary.toString());
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public String getPhrase(){
        if((phraseCount+sectionStart) < phraseTotal){
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString("current_phrase",String.valueOf(((phraseCount++)+sectionStart)));
//            editor.apply();
            return PhrasesDictionary.getPhrase(sectionStart+phraseCount++);
        }else{
            return PhrasesDictionary.getPhrase(phraseTotal-1);
        }
    }

    public void setSessionAttributes(){
        //get current session
        Log.d(TAG,"Inside setsession attributes");
        switch (current_session){
            case "1":
                sectionStart = ModeSectionValues.experimentSection1_start; sectionEnd = ModeSectionValues.experimentSection1_end;
                break;
            case "2":
                sectionStart = ModeSectionValues.experimentSection2_start; sectionEnd = ModeSectionValues.experimentSection2_end;
                break;
            case "3":
                sectionStart = ModeSectionValues.experimentSection3_start; sectionEnd = ModeSectionValues.experimentSection3_end;
                break;
            case "4":
                sectionStart = ModeSectionValues.experimentSection4_start; sectionEnd = ModeSectionValues.experimentSection4_end;
        }
    }

    public boolean hasNextSession(){
        Log.d(TAG,"inside hasNextPage");
        Log.d(TAG,"current_session:"+current_session);
        Log.d(TAG,"session_1"+session_1);
        Log.d(TAG,"session_2"+session_2);
        Log.d(TAG,"session_3"+session_3);
        Log.d(TAG,"session_4"+session_4);

        if(current_session.equals(session_1) || current_session.equals(session_2) ||current_session.equals(session_3)){
            return true;
        }
        return false;
    }

    public void promptNextSession(){
        Log.d("PracticePage","inside prompt next session");
        String toSpeak = "Current Session Ended. Going to next session.";
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void promptEndOfSession(){
        Log.d("PracticePage","inside end of all");
        String toSpeak = "Thank you for completing all the sessions.";
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void setNextSession(){
        Log.d("PracticePage","inside setting next session");
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
       if(current_session.equals(session_1)){
           current_session = session_2;
           editor.putString("current_session",session_2);
       }else if(current_session.equals(session_2)){
           current_session = session_3;
           editor.putString("current_session",session_3);
       }else if(current_session.equals(session_3)){
           current_session = session_4;
           editor.putString("current_session",session_4);
       }else if(current_session.equals(session_4)){
           current_session = "";
           editor.putString("current_session",session_1);
           Intent intent = new Intent(this,InstructionPageActivity.class);
           intent.putExtra("message_to_display","post_exp_survey1");
           startActivity(intent);
       }
        editor.commit();
    }

    public void goToSplashScreen(){
        Intent intent = new Intent(this, SplashScreenActivity.class);
        if(textToSpeech!=null){
            textToSpeech.shutdown();
            textToSpeech.stop();
        }
        startActivity(intent);
    }

    public void goToPostSurveyScreen(){
        Intent intent = new Intent(this, InstructionPageActivity.class);
        intent.putExtra("message_to_display","post_exp_survey1");
        if(textToSpeech!=null){
            textToSpeech.shutdown();
            textToSpeech.stop();
        }
        startActivity(intent);
    }


    public void getNextText(){
        if(checkIfCurrentDone()){
            practice_text.setText("");
            if((phraseCount+sectionStart) == sectionEnd) {
                //set current session parameters and all to new one.
                if(hasNextSession()){
                    setNextSession();
                    setSessionAttributes();
                    promptNextSession();
//                    goToSplashScreen();
                    goToPostSurveyScreen();
                }else{
                    promptEndOfSession();
                }
            }else if ((phraseCount+sectionStart) > sectionEnd) {
                phraseCount = 0;
                goToPostSurveyScreen();
//                goToSplashScreen();
            }

            if((phraseCount+sectionStart) <= sectionEnd){
                String phrase = getPhrase();
                text_practice_instruction.setText(phrase);
                String toSpeak = "Getting Next Phrase. Press Volume Up to hear the next phrase.";
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }

        }else{
            //prompt to finish it
            String toSpeak = "Please complete the current phrase before going ahead. Press Volume Up to hear the phrase.";
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }

    }

    public boolean checkIfCurrentDone(){
        //considering only the length parameter to check if the phrase is completely. We do not check the correctness.
        if(practice_text.getText().toString().equals(getString(R.string.text_practice_instruction))){
            return true;
        }
        if(practice_text.getText().length() >= text_practice_instruction.getText().length())
            return true;
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            //get Next phrase
            mDatabase.child("Keyboard Session").child(Utils.getTime()).setValue("volumedown");
            getNextText();

        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            // read the phrase
            mDatabase.child("Keyboard Session").child(Utils.getTime()).setValue("volumeup");
            String toSpeak = text_practice_instruction.getText().toString();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            char unicodeChar = (char) event.getUnicodeChar();
            Log.d("Key Pressed", String.valueOf(keyCodeToString(keyCode) + " key is " + Character.toString(unicodeChar) + " event time " + Utils.getTime() + " " + event.getEventTime()));
            mDatabase.child("Keyboard Session").child("Key Pressed ").child("Time :" + Utils.getTime().toString() + " Letter: ").setValue(Character.toString(unicodeChar));
            return false;
        }
        return true;
    }

//    @Override
//    public Loader<D> onCreateLoader(int i, Bundle bundle) {
//        return null;
//    }
//
//    @Override
//    public void onLoadFinished(Loader<D> loader, D d) {
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<D> loader) {
//
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        String toSpeak = getString(R.string.practice_page_speak);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        String toSpeak = getString(R.string.practice_page_speak);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }
//
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        String toSpeak = getString(R.string.practice_page_speak);
//        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//
//    }

      @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }





}
