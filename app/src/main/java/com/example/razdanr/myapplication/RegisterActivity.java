package com.example.razdanr.myapplication;

import android.content.Intent;
import android.content.SyncStatusObserver;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText etPassword;
    private EditText etUserName;
    private EditText etSuitcaseID;
    private FirebaseDatabase database;
    final ArrayList<String> array = new ArrayList<String>();
    private final static String TAG = RegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Variable declaration for UI items.
        etUserName = (EditText) findViewById((R.id.etUserName));
        etPassword = (EditText) findViewById((R.id.etPassword));
        etSuitcaseID = (EditText) findViewById((R.id.etSuitcaseID));
        final Button bRegister = (Button) findViewById((R.id.bRegister));
        final TextView tvLogin = (TextView) findViewById((R.id.tvLogin));
        firebaseAuth= FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("suitcaseID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get all of the children at this level
                final Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                Log.i(TAG,"Getting all registered suitcase IDs from the database");
                for (DataSnapshot child: children)
                {
                    array.add(child.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Default method to be overriden. Not required in out case.
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registerUser();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(loginIntent);
            }
        });
    }

    private void registerUser(){
        int flag=0;
        String email= etUserName.getText().toString();
        String password= etPassword.getText().toString();
        for(int i = 0; i< array.size();i++) {
            if (array.get(i).toString().equals(etSuitcaseID.getText().toString())) {
                registerChecker(email,password,array.get(i).toString());
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register Successfully", Toast.LENGTH_LONG).show();
                            Log.i(TAG,"Registration Successful");
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG).show();
                            Log.i(TAG,"Registration Error");
                        }
                    }
                });
                flag++;
                break;
            }
            Log.w(TAG,"Not an authentic user.");
        }
        if(flag==0)
        {
            Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG).show();
        }
    }
    //testing the user inputs
    protected boolean registerChecker(String e,String p,String id){
        String email=e;
        String password=p;
        String suitID=id;

        return !(suitID.isEmpty() || password.isEmpty() || email.isEmpty());
    }
}
