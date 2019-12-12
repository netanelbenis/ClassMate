package com.example.classmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button loginButton;
    private Button register;
    private EditText emailEditText;
    private EditText passwordEditText;
    private boolean pflag = false, eflag = false;
    private FirebaseAuth mAuth;
    private static final String TAG = "Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginButton = findViewById(R.id.loginButton);
        register=findViewById(R.id.register);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        InitListeners();
        Login();

    }

    protected void InitListeners(){
        //Email
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                    if(!isValidEmail(s)){
                        emailEditText.setError("Invalid Email");
                        ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                        ViewCompat.setBackgroundTintList(emailEditText, colorStateList);
//                        eflag = false;
                    }
                    else {
                        ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                        ViewCompat.setBackgroundTintList(emailEditText, colorStateList);
                        eflag = true;
                    }
            }
        });

        //Password
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!isValidPassword(s)){
                    passwordEditText.setError("Please enter a password at least 8 characters long");
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFFD3212D);
                    ViewCompat.setBackgroundTintList(passwordEditText, colorStateList);
//                    pflag = false;
                }
                else {
                    ColorStateList colorStateList = ColorStateList.valueOf(0xFF1C1CF0);
                    ViewCompat.setBackgroundTintList(passwordEditText, colorStateList);
                    pflag = true;
                }
            }
        });
    }

    private final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private final static boolean isValidPassword(CharSequence target) {
        if(target == null || target.length() < 8)
            return false;

        return true;
    }

    private void checkEMAILpassword(){
        Log.d(TAG, "into the func");
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            eflag=pflag=true;
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    protected void Login() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eflag&&pflag) {
                    checkEMAILpassword();
                }else{
                    Toast.makeText(Login.this, "Invalid email or password.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {}

}
