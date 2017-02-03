package com.example.razdanr.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*Variable declaration for layout items */
        final EditText etUserName = (EditText) findViewById((R.id.etUserName));
        final EditText etPassword = (EditText) findViewById((R.id.etPassword));
        final Button bLogin = (Button) findViewById((R.id.bLogin));
        //final TextView tvRegister = (TextView) findViewById((R.id.tvRegister));
        mAuth = FirebaseAuth.getInstance();
        /*tvRegister.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
               Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
               LoginActivity.this.startActivity(registerIntent);
           }
        });*/

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email= etUserName. getText().toString();
                String password= etPassword. getText().toString();

            }
        });
    }
}
