package experimentapp.kmai.org.userexperimentapp.setting_selector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.register_login.MainActivity;

public class SettingOptionLayoutActivity extends AppCompatActivity {

    RadioGroup sessionSelector;
    RadioButton selectedButton;
    Button nextButton;
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_option_layout);

        sessionSelector = findViewById(R.id.setting_selector_group);

        nextButton = findViewById(R.id.setting_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sessionSelector.isSelected()){
                    int selectedId=sessionSelector.getCheckedRadioButtonId();
                    selectedButton=(RadioButton)findViewById(selectedId);
                    Toast.makeText(SettingOptionLayoutActivity.this,selectedButton.getText(),Toast.LENGTH_SHORT).show();
                    setValuestoUtils(selectedButton.getText().toString());   //add to the utils
                    Intent intent = new Intent(SettingOptionLayoutActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(SettingOptionLayoutActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Select a Setting type and proceed.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }

            }
        });
    }

    protected void setValuestoUtils(String session_sequence){

        int session_1 = Integer.parseInt(Character.toString(session_sequence.charAt(0)));
        int session_2 = Integer.parseInt(Character.toString(session_sequence.charAt(2)));
        int session_3 = Integer.parseInt(Character.toString(session_sequence.charAt(4)));
        int session_4 = Integer.parseInt(Character.toString(session_sequence.charAt(6)));

        ModeSectionValues.setSection_first(session_1);
        ModeSectionValues.setSection_second(session_2);
        ModeSectionValues.setSection_third(session_3);
        ModeSectionValues.setSection_fourth(session_4);
        ModeSectionValues.setCurrentSession(session_1);

        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("session_sequence",session_sequence);
        editor.putString("current_session",String.valueOf(session_1));
        editor.putString("current_phrase","0");
        editor.putString("session_1",String.valueOf(session_1));
        editor.putString("session_2",String.valueOf(session_2));
        editor.putString("session_3",String.valueOf(session_3));
        editor.putString("session_4",String.valueOf(session_4));

        editor.commit();

        Log.d("SharedPreferences","USER ID after saving is "+sharedpreferences.getString("email_id",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("password",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("current_session",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_1",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_2",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_3",""));
        Log.d("SharedPreferences","Password after saving is "+sharedpreferences.getString("session_4",""));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
