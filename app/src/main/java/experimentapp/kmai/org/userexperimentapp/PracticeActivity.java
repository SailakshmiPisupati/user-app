package experimentapp.kmai.org.userexperimentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
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

import static android.view.KeyEvent.keyCodeToString;
import static experimentapp.kmai.org.userexperimentapp.Utils.encodeUserEmail;
import static experimentapp.kmai.org.userexperimentapp.Utils.getDeviceID;

/**
 * Created by saila on 2/23/18.
 */

public class PracticeActivity extends Activity {

    EditText practice_text, practice_optional_text;
    TextView text_practice_instruction;
    Button survey, next;
    PhrasesDictionary dictionary;
    int phraseCount = 0, phraseTotal;
    DatabaseReference mDatabase;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_layout);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("phrases.txt");
            dictionary = new PhrasesDictionary(new InputStreamReader(inputStream));
            phraseTotal = PhrasesDictionary.getphraseCount();
            Log.d("StrokeIME", dictionary.toString());
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        practice_text = findViewById(R.id.practice_text_edittext);
//        practice_optional_text = (EditText) findViewById(R.id.practice_optional_text_editext);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.userid), Context.MODE_PRIVATE);
        final String username = sharedPref.getString(getString(R.string.userid), "");

        Log.d("user is ", encodeUserEmail(username));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(encodeUserEmail(username));


        practice_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if((start + count - 1) > 0) {
                    char ch = charSequence.charAt(start + count - 1);
                    Log.d("Key Pressed", ch + " : " + getTime());
                    mDatabase.child("Keyboard Session").child(getTime()).setValue(Character.toString(ch));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

//        practice_text.setOnKeyListener(new View.OnKeyListener() {
//
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                char ch=  s.charAt(start + count - 1);
//
//            }
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                char unicodeChar = (char) keyEvent.getUnicodeChar();
//                Log.d("Key Pressed", String.valueOf(keyCodeToString(i) + " key is " + unicodeChar + " event time " + getTime() + " " + keyEvent.getEventTime()));
//                mDatabase.child("Keyboard Session").child("Key Pressed ").child("Time :" + getTime().toString() + " Letter: ").setValue(Character.toString(unicodeChar));
//                return false;
//            }
//        });

//        survey = (Button) findViewById(R.id.survey_button);
//        survey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PracticeActivity.this, WebViewactivity.class);
//                startActivity(intent);
//            }
//        });

        text_practice_instruction = findViewById(R.id.text_practice_instruction);

        next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNextText();
            }
        });
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    public String getPhrase(){
        if(phraseCount < phraseTotal){
            return PhrasesDictionary.getPhrase(phraseCount++);
        }
        else if(phraseCount == phraseTotal) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.password), Context.MODE_PRIVATE);
            return sharedPref.getString(getString(R.string.password), "");
        }else{
            return PhrasesDictionary.getPhrase(phraseTotal-1);
        }
    }

    public void getNextText(){
        practice_text.setText("");
        if (phraseCount >= phraseTotal - 1) {
            next.setVisibility(View.INVISIBLE);
        }
        String phrase = getPhrase();
        text_practice_instruction.setText(phrase);
        String toSpeak = "Getting Next Phrase. Press Volume Up to hear the next phrase.";
        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            //get Next phrase
            getNextText();

        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            // read the phrase
            String toSpeak = text_practice_instruction.getText().toString();
            textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        } else {
            //log the key
            char unicodeChar = (char) event.getUnicodeChar();
            Log.d("Key Pressed", String.valueOf(keyCodeToString(keyCode) + " key is " + Character.toString(unicodeChar) + " event time " + getTime() + " " + event.getEventTime()));
            mDatabase.child("Keyboard Session").child("Key Pressed ").child("Time :" + getTime().toString() + " Letter: ").setValue(Character.toString(unicodeChar));
            return false;
        }
        return true;
    }
}
