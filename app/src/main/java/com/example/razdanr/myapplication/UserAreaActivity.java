package com.example.razdanr.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAreaActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        final TextView tvName = (TextView) findViewById((R.id.tvName));
        final Button bLogout = (Button) findViewById((R.id.bLogout));

        firebaseAuth= FirebaseAuth.getInstance();
        //If the user has logged out then it will redirect to login Page
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(UserAreaActivity.this, LoginActivity.class));
        }
        FirebaseUser user= firebaseAuth.getCurrentUser();

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //Intent loginIntent = new Intent(UserAreaActivity.this, LoginActivity.class);
               //  UserAreaActivity.this.startActivity(loginIntent);
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(UserAreaActivity.this, LoginActivity.class));

            }
        });
    }
}
