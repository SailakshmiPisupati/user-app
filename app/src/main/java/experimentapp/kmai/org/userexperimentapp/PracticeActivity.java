package experimentapp.kmai.org.userexperimentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.view.KeyEvent.keyCodeToString;
import static experimentapp.kmai.org.userexperimentapp.Utils.encodeUserEmail;
import static experimentapp.kmai.org.userexperimentapp.Utils.getDeviceID;

/**
 * Created by saila on 2/23/18.
 */

public class PracticeActivity extends Activity {

    EditText practice_text , practice_optional_text;
    Button survey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice_layout);
        practice_text = (EditText) findViewById(R.id.practice_text_edittext);
        practice_optional_text = (EditText) findViewById(R.id.practice_optional_text_editext);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.userid), Context.MODE_PRIVATE);
        final String username = sharedPref.getString(getString(R.string.userid),"");
//        TextView userid_tv = (TextView)findViewById(R.id.user_id);
//        userid_tv.setText(username);

        Log.d("user is ",encodeUserEmail(username));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mDatabase = database.getReference(encodeUserEmail(username));

        practice_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                char unicodeChar = (char)keyEvent.getUnicodeChar();
                Log.d("key pressed",String.valueOf(keyCodeToString(i)+" key is "+unicodeChar+" event time "+getTime()+ " "+ keyEvent.getEventTime()));



                mDatabase.child("Keyboard Session").child("Key Pressed ").child("Time :"+getTime().toString()+" Letter: ").setValue(Character.toString(unicodeChar));


                return false;
            }
        });

        survey = (Button) findViewById(R.id.survey_button);
        survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PracticeActivity.this, WebViewactivity.class);
                startActivity(intent);
            }
        });

    }


    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");
        Date date = new Date();
        return  dateFormat.format(date);
    }

}
