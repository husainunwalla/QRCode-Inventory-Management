package com.example.enamul.qrcode;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText emailEditText, passwordEditText;
    Button loginButton;
    ProgressBar progressBar;
    TextView titleText, errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBarLogin);
        titleText = findViewById(R.id.textViewTitle);
        errorText = findViewById(R.id.textViewError);

        progressBar.setVisibility(View.INVISIBLE);
        errorText.setVisibility(View.INVISIBLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                titleText.setVisibility(View.INVISIBLE);
                errorText.setVisibility(View.INVISIBLE);
                login();
            }
        });

        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER)
                    login();

                return false;
            }
        });
    }

    public void login(){
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Welcome.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",  Toast.LENGTH_SHORT).show();
                            errorText.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            titleText.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

}
