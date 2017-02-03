package com.example.razdanr.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        final TextView tvName = (TextView) findViewById((R.id.tvName));
        final Button bLogout = (Button) findViewById((R.id.bLogout));

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent loginIntent = new Intent(UserAreaActivity.this, LoginActivity.class);
                UserAreaActivity.this.startActivity(loginIntent);
            }
        });
    }
}
