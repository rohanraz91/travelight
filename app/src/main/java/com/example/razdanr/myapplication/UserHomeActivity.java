package com.example.razdanr.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserHomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private boolean scanning;

    private static final int RQS_ENABLE_BLUETOOTH = 1;
    Button bScan;
    ListView listView;
    List<BluetoothDevice> listBluetoothDevice;
    ListAdapter adapterLeScanResult;
    private Handler handler;
    private static final long SCAN_PERIOD = 10000;
    private final static String TAG = UserHomeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Log.i(TAG,"User is authenticated and can login.");
        // Check if BLE is supported on the device.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this,
                    "BLUETOOTH_LE not supported in this device!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        getBluetoothAdapterAndLeScanner();
        // Checks if Bluetooth Adapter is received.
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    "bluetoothManager.getAdapter()==null",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        bScan = (Button)findViewById(R.id.bScan);
        bScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanLeDevice(true);
            }
        });

        listView = (ListView)findViewById(R.id.lelist);

        listBluetoothDevice = new ArrayList<>();
        adapterLeScanResult = new ArrayAdapter<BluetoothDevice>(
                this, android.R.layout.simple_list_item_1, listBluetoothDevice);
        listView.setAdapter(adapterLeScanResult);
        listView.setOnItemClickListener(scanResultOnItemClickListener);

        handler = new Handler();
        final Button bLogout = (Button) findViewById((R.id.bLogout));
        firebaseAuth = FirebaseAuth.getInstance();

        //If the user has logged out then it will redirect to login Page.
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Logout button tapped.
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));

            }
        });
    }

    //When an item from the list is clicked
    AdapterView.OnItemClickListener scanResultOnItemClickListener =
            new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final BluetoothDevice device = (BluetoothDevice) parent.getItemAtPosition(position);
                    if (device == null) return;
                    final Intent intent = new Intent(UserHomeActivity.this, ControlBLEActivity.class);

                    //send device bame and address to ControlBLEActivity
                    intent.putExtra(ControlBLEActivity.EXTRAS_DEVICE_NAME, device.getName());
                    intent.putExtra(ControlBLEActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                    startActivity(intent);
                }
            };

    @Override
    protected void onResume() {
        super.onResume();

        if (!bluetoothAdapter.isEnabled()) {
            if (!bluetoothAdapter.isEnabled()) {
                Log.w(TAG,"Adapter needs to be turned on to discover the BLE devices");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, RQS_ENABLE_BLUETOOTH);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RQS_ENABLE_BLUETOOTH && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }

        getBluetoothAdapterAndLeScanner();

        // Checks if Bluetooth is supported on the device.
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    "bluetoothManager.getAdapter()==null",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getBluetoothAdapterAndLeScanner(){
        // Get BluetoothAdapter and BluetoothLeScanner.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

        scanning = false;
    }

    // Bluetooth admin permission required to start scan.
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            listBluetoothDevice.clear();
            listView.invalidateViews();

            // Stops scanning after given scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bluetoothLeScanner.stopScan(scanCallback);
                    listView.invalidateViews();

                    Toast.makeText(UserHomeActivity.this,
                            "Scan timeout",
                            Toast.LENGTH_LONG).show();

                    scanning = false;
                    bScan.setEnabled(true);
                }
            }, SCAN_PERIOD);

            bluetoothLeScanner.startScan(scanCallback);
            scanning = true;
            bScan.setEnabled(false);
        } else {
            bluetoothLeScanner.stopScan(scanCallback);
            scanning = false;
            bScan.setEnabled(true);
        }
    }

    //Decisions on scanCallback
    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            //add item to list on callback
            addBluetoothDevice(result.getDevice());
            Log.i(TAG, "Since discovery is a async process so callBack method required");
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for(ScanResult result : results){
                addBluetoothDevice(result.getDevice());
            }
        }

        @Override
        //throw error if scan failed
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e("ERROR", errorCode+"-has occured");
            Toast.makeText(UserHomeActivity.this,
                    "onScanFailed: " + String.valueOf(errorCode),
                    Toast.LENGTH_LONG).show();
        }

        //Add scanned items to listview @refactored
        private void addBluetoothDevice(BluetoothDevice device){
            if(!listBluetoothDevice.contains(device)){
                listBluetoothDevice.add(device);
                listView.invalidateViews();
            }
        }
    };
}
