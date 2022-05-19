package com.cmpe277.birdie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText email, password;
    private Button login;

    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        email = (EditText) findViewById(R.id.email);
        password = (EditText)  findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;
        }
    }

    private void userLogin() {

        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        if(emailStr.isEmpty()){
            email.setError("Enter email address");
            email.requestFocus();
            return;
        }
        if(passwordStr.isEmpty()){
            password.setError("Enter password");
            password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            email.setError("Please enter a valid email address");
            email.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        firebaseAuth.signInWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                /* FirebaseUser user = FirebaseUser.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email to verify!",Toast.LENGTH_LONG).show();
                    }*/

                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Login Failed! Check credentials !",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}