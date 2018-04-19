package experimentapp.kmai.org.userexperimentapp.setting_selector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import experimentapp.kmai.org.userexperimentapp.practice_session.PracticeActivity;
import experimentapp.kmai.org.userexperimentapp.R;

public class SectionSelectorActivity extends AppCompatActivity implements  View.OnClickListener {
    Button section1,section2,section3,section4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_selector);

        section1 = findViewById(R.id.button_section1);
        section2 = findViewById(R.id.button_section2);
        section3 = findViewById(R.id.button_section3);
        section4 = findViewById(R.id.button_section4);


        section1.setOnClickListener(this);
        section2.setOnClickListener(this);
        section3.setOnClickListener(this);
        section4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
//        bundle.putString("phrases_filename", "training_phrase_1");                    use this for swithcing traning and experiemnt

        switch(view.getId()){
            case R.id.button_section1 :
                bundle.putString("section", "section1");
                break;
            case R.id.button_section2 :
                bundle.putString("section", "section2");
                break;
            case R.id.button_section3 :
                bundle.putString("section", "section3");
                break;
            case R.id.button_section4 :
                bundle.putString("section", "section4");
        }
        Intent intent = new Intent(SectionSelectorActivity.this,PracticeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
