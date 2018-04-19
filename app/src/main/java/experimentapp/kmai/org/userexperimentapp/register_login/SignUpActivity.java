package experimentapp.kmai.org.userexperimentapp.register_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import experimentapp.kmai.org.userexperimentapp.R;
import experimentapp.kmai.org.userexperimentapp.practice_session.CheckTutorialCompleted;
import experimentapp.kmai.org.userexperimentapp.practice_session.InstructionPageActivity;
import experimentapp.kmai.org.userexperimentapp.utility.Utils;
import experimentapp.kmai.org.userexperimentapp.setting_selector.SectionSelectorActivity;

import static experimentapp.kmai.org.userexperimentapp.utility.Utils.encodeUserEmail;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfirmPassword1, inputConfirmPassword2;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private final String TAG = "SignUpActivity";
    public static final String sharedPreferenceName ="USEREXPERIMENTAPP";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfirmPassword1 = (EditText) findViewById(R.id.confirm_password1);
//        inputConfirmPassword2 = (EditText) findViewById(R.id.confirm_password2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                String confirm_password1 = inputConfirmPassword1.getText().toString().trim();
//                String confirm_password2 = inputConfirmPassword2.getText().toString().trim();

//                validate(email,password,confirm_password1,confirm_password2);

                validate(email,password,confirm_password1);

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    //prompt password to the user.

                                } else {
                                    // Save the username and password to the database
                                    Utils.setPassword(password);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference mDatabase = database.getReference(encodeUserEmail(email));

                                    mDatabase.child("password").setValue(password);
                                    mDatabase.child("device_id").setValue(getDeviceID());
                                    mDatabase.child("registration_date").setValue(getTime());

                                    //Set the username as the shared preferences.
                                    saveUser(email);
                                    savePassword(password);

                                    Intent intent = new Intent(SignUpActivity.this, CheckTutorialCompleted.class);
                                    intent.putExtra("message_to_display","tutorial_reminder");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


            }
        });
    }

    private void saveUser(String email){
        String encodedEmail = encodeUserEmail(email);
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("email_id",encodedEmail);
        editor.apply();
//        editor.commit();                      - using apply since apply() will add it asynchronously. commit() is synchronous.

    }

    private void savePassword(String password){
        sharedpreferences = getSharedPreferences(sharedPreferenceName,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("password",password);
        editor.apply();
    }


    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
        Date date = new Date();
        return  dateFormat.format(date);
    }

    protected void validate(String email,String password,String confirm_password1,String confirm_password2){
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirm_password1)) {
            Toast.makeText(getApplicationContext(), "Enter confirm password 1!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(confirm_password2)) {
            Toast.makeText(getApplicationContext(), "Enter confirm password 2!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("register form","inside validation");
        String regex_pattern = "[a-z]||[A-Z]";
        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher m = pattern.matcher(confirm_password1);

        if(confirm_password2.equals(password) && confirm_password1.equals(password)){
            return;
        }
    }

    protected void validate(String email,String password,String confirm_password1){
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(confirm_password1)) {
            Toast.makeText(getApplicationContext(), "Enter confirm password 1!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("register form","inside validation");
        String regex_pattern = "[a-z]||[A-Z]";
        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher m = pattern.matcher(confirm_password1);

        if(confirm_password1.equals(password)){
            return;
        }
    }

    public String getDeviceID() {
        TelephonyManager tManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "Permission denied for device ID";
        }
        String uid = tManager.getDeviceId();
        Utils.setDeviceID(uid);
        return uid;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}