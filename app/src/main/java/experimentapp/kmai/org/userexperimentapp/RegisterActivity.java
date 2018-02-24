package experimentapp.kmai.org.userexperimentapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * Created by saila on 2/20/18.
 */

public class RegisterActivity extends Activity {
    private FirebaseAuth mAuth;
    EditText username, password, password1, password2;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        mAuth = FirebaseAuth.getInstance();
        // Set up the login form.

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password1);
        password1 = (EditText) findViewById(R.id.password2);
        password2 = (EditText) findViewById(R.id.password3);


        final String name = username.getText().toString();
        final String sPassword =password.getText().toString();
        final String sPassword2 =password1.getText().toString();
        final String sPassword3 =password2.getText().toString();


        register = (Button) findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("register form","button clicked validation");
                if(validatePassword(sPassword,sPassword2,sPassword3)){
                    registerUser(name,sPassword);
                }
            }
        });

    }

    //check if all the entered passwords match
    private boolean validatePassword(String spassword1,String spassword2,String spassword3) {
        Log.d("register form","inside validation");
        String regex_pattern = "[a-z]||[A-Z]";
        Pattern pattern = Pattern.compile(regex_pattern);
        Matcher m = pattern.matcher(spassword1);

        if(spassword2.equals(spassword1) && spassword3.equals(spassword1)){
            return true;
        }

        return false;
    }

    protected void registerUser(String username, String password){
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });

    }
}
