package com.example.razdanr.myapplication;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserHome extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        final Button bLogout = (Button) findViewById((R.id.bLogout));
        final Button bScanDevices = (Button) findViewById((R.id.bScanDevices));

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        firebaseAuth = FirebaseAuth.getInstance();

        //If the user has logged out then it will redirect to login Page
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(UserHome.this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Logout button tapped
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent loginIntent = new Intent(UserHome.this, LoginActivity.class);
                //  UserHome.this.startActivity(loginIntent);
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(UserHome.this, LoginActivity.class));

            }
        });

        // ScanSuitcases button tapped
        bScanDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter = bluetoothManager.getAdapter();
                // Ensures Bluetooth is available on the device and it is enabled. If not,
                // displays a dialog requesting user permission to enable Bluetooth.
                if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }
            }
        });
    }
}
