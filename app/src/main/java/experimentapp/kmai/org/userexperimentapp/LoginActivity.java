package experimentapp.kmai.org.userexperimentapp;

/**
 * Created by saila on 2/23/18.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.view.KeyEvent.keyCodeToString;
import static experimentapp.kmai.org.userexperimentapp.User.email;
import static experimentapp.kmai.org.userexperimentapp.Utils.decodeUserEmail;
import static experimentapp.kmai.org.userexperimentapp.Utils.encodeUserEmail;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private int wrongPasswordCount = 0;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // set the view now
        setContentView(R.layout.login_layout);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.userid), Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.userid),"");

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);


        inputEmail.setText(decodeUserEmail(username));
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(encodeUserEmail(username));

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if((start + count - 1) > 0) {
                    char ch = charSequence.charAt(start + count - 1);
                    Log.d("Key Pressed", ch + " : " + getTime());
                    mDatabase.child("Password Session").child(getTime()).setValue(Character.toString(ch));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        inputPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                char unicodeChar = (char)keyEvent.getUnicodeChar();
                Log.d("key pressed",String.valueOf(keyCodeToString(i)+" key is "+unicodeChar+" event time "+getTime()+ " "+ keyEvent.getEventTime()));
                mDatabase.child("Password Session").child(getTime()).setValue(Character.toString(unicodeChar));
                //mDatabase.child("Password Session").child("Key Pressed ").child("Time :"+getTime().toString()+" Letter: ").setValue(Character.toString(unicodeChar));
                return false;
            }
        });

//        btnReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        wrongPasswordCount++;
                                        if(wrongPasswordCount % 3 == 0){

                                            new GetPassword().execute(encodeUserEmail(email));
                                        }else{
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, SectionSelectorActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }

    class GetPassword extends AsyncTask<String, String, String>{
        String password;
        Map<String, Object> user;

        @Override
        protected String doInBackground(String... strings) {
            Log.d("Signup:","email is "+strings[0]);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(strings[0]);

            // Attach a listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = (Map<String, Object>) dataSnapshot.getValue();
                    password = user.get("password: ").toString();
                    System.out.println(" password: inside  "+password);
                    runOnUiThread(new Runnable() {

                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("Your Password is : "+password);
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
//                            Toast.makeText(getApplicationContext(),"Your Password is: "+password,Toast.LENGTH_LONG).show();
                        }
                    });
//
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//            System.out.println(" password: out  "+user.get(password));
            return password;
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(LoginActivity.this,"Your Password is: "+result,Toast.LENGTH_LONG);
        }

    }


//    private String announcePassword(final String email){
//
//        Log.d("Signup:","email is "+encodeUserEmail(email));
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference(encodeUserEmail(email));
//
//        // Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Map<String, Object> user = (Map<String, Object>) dataSnapshot.getValue();
//                password = user.get("password: ").toString();
//                System.out.println(" password: inside  "+password);
////                Toast.makeText(LoginActivity.this,"Your Password is: "+password,Toast.LENGTH_LONG);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        System.out.println(" password: outside  "+password);
//        return password;
//    }

    public String getTime(){
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss:SSS");
        Date date = new Date();
        return  dateFormat.format(date);
    }
}