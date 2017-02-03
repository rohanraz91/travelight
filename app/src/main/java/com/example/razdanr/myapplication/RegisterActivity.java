package com.example.razdanr.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*Variable declaration for layout items */
        final EditText etName = (EditText) findViewById((R.id.etName));
        final EditText etUserName = (EditText) findViewById((R.id.etUserName));
        final EditText etPassword = (EditText) findViewById((R.id.etPassword));
        final EditText etSuitcaseID = (EditText) findViewById((R.id.etSuitcaseID));
        final Button bRegister = (Button) findViewById((R.id.bRegister));
        final TextView tvLogin = (TextView) findViewById((R.id.tvLogin));

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });


    }
}
