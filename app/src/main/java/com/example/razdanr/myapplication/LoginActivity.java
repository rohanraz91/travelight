package com.example.razdanr.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etUserName;
    private EditText etPassword;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private final static String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Variable declaration for UI items.
        etUserName = (EditText) findViewById((R.id.etUserName));
        etPassword = (EditText) findViewById((R.id.etPassword));

        final Button bLogin = (Button) findViewById((R.id.bLogin));
        final TextView tvRegister = (TextView) findViewById((R.id.tvRegister));
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //If user has logged In
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                }
            }
        };
        tvRegister.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
               Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
               LoginActivity.this.startActivity(registerIntent);
           }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signIn();

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);

    }

    protected void signIn(){
        String email= etUserName.getText().toString();
        String password= etPassword.getText().toString();
        checker(email, password);
        //check if user has not left the Uname and Pass field Blanck.
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Log.w(TAG,"EMAIL of PASSWORD Field is empty");
            Toast.makeText(LoginActivity.this, "Email/ Password Field Empty", Toast.LENGTH_LONG).show();
        }
        else{
            final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...","Processing...",true);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   // progressDialog.dismiss();
                    if (!task.isSuccessful()) {
                        Log.e("ERROR", task.getException().toString());
                        Toast.makeText(LoginActivity.this, "Error While SignIn", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    protected boolean checker(String e, String p){
        String email= e;
        String password= p;

        if(email.isEmpty() || password.isEmpty()){
            Log.i(TAG,"EMAIL of PASSWORD Field is empty hence the checker method will fail");
            return false;
        }
        else{
            Log.i(TAG,"EMAIL of PASSWORD Field is empty hence the checker method will pass");
            return true;
        }

    }
}
