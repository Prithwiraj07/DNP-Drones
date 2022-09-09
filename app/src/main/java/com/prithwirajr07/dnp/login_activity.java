package com.prithwirajr07.dnp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class login_activity extends AppCompatActivity {

    TextView toRegister;
    EditText lEmail,lPassword;
    Button loger;
    ProgressBar progressBar_log;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toRegister=findViewById(R.id.textView_reg_from_logIn);
        loger=findViewById(R.id.button_logIn);
        lEmail=findViewById(R.id.editTextTextEmailAddress_logIn);
        lPassword=findViewById(R.id.editTextTextPassword_logIn);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar_log=findViewById(R.id.progressBar_logIn);


        loger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=lEmail.getText().toString().trim();
                String password=lPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    lPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    lPassword.setError("Password must be of 6 or more characters long");
                    return;
                }
                progressBar_log.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText( login_activity.this, "logged in Succesfully", Toast.LENGTH_SHORT).show();
                            progressBar_log.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else {
                            Toast.makeText(login_activity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar_log.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register_activity.class));
                finish();
            }
        });
    }
}