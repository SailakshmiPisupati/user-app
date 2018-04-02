package experimentapp.kmai.org.userexperimentapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by saila on 4/1/18.
 */

public class ModeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mode_layout);

        Button practice, experiment;
        practice = findViewById(R.id.start_practice_mode);
//        experiment = findViewById(R.id.start_experiment_mode);

        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeActivity.this,SectionSelectorActivity.class);
                startActivity(intent);
            }
        });

//        experiment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ModeActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });

    }
}
