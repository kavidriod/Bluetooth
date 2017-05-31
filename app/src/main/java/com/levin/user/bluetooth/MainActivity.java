package com.levin.user.bluetooth;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeviceListFragment.OnFragmentInteractionListener {

    DeviceListFragment deviceListFragment;
    BluetoothAdapter bluetoothAdapter;
    public final static int REQUEST_BLUETOOTH = 1;
    ArrayAdapter<DeviceListItems> deviceListItemsArrayAdapter;
    private final int  PERMISSION_REQUEST_CODE = 2;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null){
            new AlertDialog.Builder(this)
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.stat_sys_data_bluetooth)
                    .show();
        }else {
            if (!bluetoothAdapter.isEnabled()){
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBTIntent,REQUEST_BLUETOOTH);
            }else {
                showFragment();
            }
        }


        Log.i("Device Version", "  "+ Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 23) {  //Build.VERSION_CODES.M
            //Grant Permission in runtime
            requestPermissionAtRuntime();
        } else {
            //User already granted permission before Installation
        }
    }

    private boolean requestPermissionAtRuntime() {

        String[] permissionsToRequest = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        List<String> listPermissionsNeeded = new ArrayList<String>();
        int result;
        for (String p : permissionsToRequest) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                }else {
                    requestPermissionAtRuntime();
                }
                break;

            default:
                break;
        }
    }

    private void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        deviceListFragment = DeviceListFragment.newInstance(bluetoothAdapter);
        fragmentManager.beginTransaction().replace(R.id.activity_main,deviceListFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case  REQUEST_BLUETOOTH:

                if (resultCode == RESULT_OK){
                    showToast("BT Enabled");
                    showFragment();


                }else {
                    showToast("BT is not Enabled");
                }

                break;
        }
    }


    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }



    @Override
    public void onFragmentInteraction(String uri) {
        Log.i(TAG, " onFragmentInteraction  "+ uri);
    }
}
