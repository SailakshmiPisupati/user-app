package experimentapp.kmai.org.userexperimentapp.setting_selector;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.register_login.SignUpActivity;

public class PhraseSelectorActivity extends AppCompatActivity {

    RadioGroup sessionSelector;
    RadioButton selectedButton;
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_selector_layout);

        sessionSelector = findViewById(R.id.setting_selector_group);

        nextButton = findViewById(R.id.setting_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sessionSelector.isSelected()){
                    int selectedId=sessionSelector.getCheckedRadioButtonId();
                    selectedButton=(RadioButton)findViewById(selectedId);
                    Toast.makeText(PhraseSelectorActivity.this,selectedButton.getText(),Toast.LENGTH_SHORT).show();
                    setValuestoUtils(selectedButton.getText().toString());   //add to the utils
                    Intent intent = new Intent(PhraseSelectorActivity.this,SignUpActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog alertDialog = new AlertDialog.Builder(PhraseSelectorActivity.this).create();
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

        int session_1 = Integer.parseInt(Character.toString(session_sequence.charAt(1)));
        int session_2 = Integer.parseInt(Character.toString(session_sequence.charAt(4)));
        int session_3 = Integer.parseInt(Character.toString(session_sequence.charAt(7)));
        int session_4 = Integer.parseInt(Character.toString(session_sequence.charAt(10)));

        int count  = 1;

        switch (session_1){
            case 1 :
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection1_start);
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection1_end);
                break;
            case 2 :
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection1_start);
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection1_end);
                break;
            case 3 :
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection1_start);
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection1_end);
                break;
            case 4 :
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection1_start);
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection1_end);
                break;
        }

        switch (session_2){
            case 1 :
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection2_start);
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection2_end);
                break;
            case 2 :
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection2_start);
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection2_end);
                break;
            case 3 :
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection2_start);
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection2_end);
                break;
            case 4 :
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection2_start);
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection2_end);
                break;
        }

        switch (session_3){
            case 1 :
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection3_start);
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection3_end);
                break;
            case 2 :
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection3_start);
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection3_end);
                break;
            case 3 :
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection3_start);
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection3_end);
                break;
            case 4 :
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection3_start);
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection3_end);
                break;
        }
        
        switch (session_4){
            case 1 :
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection4_start);
                ModeSectionValues.setExperimentSection1_start(ModeSectionValues.PhraseSection4_end);
                break;
            case 2 :
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection4_start);
                ModeSectionValues.setExperimentSection2_start(ModeSectionValues.PhraseSection4_end);
                break;
            case 3 :
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection4_start);
                ModeSectionValues.setExperimentSection3_start(ModeSectionValues.PhraseSection4_end);
                break;
            case 4 :
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection4_start);
                ModeSectionValues.setExperimentSection4_start(ModeSectionValues.PhraseSection4_end);
                break;
        }
    }
}
