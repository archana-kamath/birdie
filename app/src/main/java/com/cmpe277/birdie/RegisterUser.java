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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private TextView banner;
    private Button registerUser;
    private EditText userName, userEmail, userPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerButton);
        registerUser.setOnClickListener(this);

        userName = (EditText) findViewById(R.id.userName);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword= (EditText) findViewById(R.id.userPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String username = userName.getText().toString().trim();
        String useremail = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(username.isEmpty()){
            userName.setError("Enter name of user");
            userName.requestFocus();
            return;
        }
        if(useremail.isEmpty()){
            userEmail.setError("Enter email address");
            userEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            userPassword.setError("Enter password");
            userPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
            userEmail.setError("Please enter a valid email address");
            userEmail.requestFocus();
            return;
        }

        if(password.length()<6){
            userPassword.setError("Please enter minimum 6 characters");
            userPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(useremail,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    User user = new User(username,useremail);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "User has been registered!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.VISIBLE);
                            }else{
                                Intent intent = new Intent(RegisterUser.this, HomeActivity.class);
                                startActivity(intent);

//                              Toast.makeText(RegisterUser.this, "Failed to register. Try again!",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    Intent intent = new Intent(RegisterUser.this, HomeActivity.class);
                    startActivity(intent);

                    Toast.makeText(RegisterUser.this, "User has been registered!",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
    }
}