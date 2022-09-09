package com.prithwirajr07.dnp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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

public class register_activity extends AppCompatActivity {
    EditText mFullname, mEmail, mPassword, mMobile;
    TextView mLogIn;
    Button mReg_button;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullname=findViewById(R.id.editTextTextPersonName2_reg);
        mEmail=findViewById(R.id.editTextTextEmailAddress_reg);
        mPassword=findViewById(R.id.editTextTextPassword1_reg);
        mMobile=findViewById(R.id.editTextMobile_reg);
        mLogIn=findViewById(R.id.textView_log_from_reg);
        mReg_button=findViewById(R.id.button_reg);
        progressBar=findViewById(R.id.progressBar_reg);
        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }



        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<   Handle the button click  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
        mReg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be of 6 or more characters long");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //<<<<<<<<<<<<<<<<< Registering User in FireBase  >>>>>>>>>>>>>>>>>>>>>>//

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText( register_activity.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else {
                            Toast.makeText(register_activity.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        });
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login_activity.class));
            }
        });

    }
}