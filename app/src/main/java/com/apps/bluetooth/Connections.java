package com.apps.bluetooth;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Kavitha on 5/3/2017.
 */

public class Connections {

    private static  boolean state = false;

    public static boolean blueTooth(){
        BluetoothAdapter bluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()){
           System.out.println("Bluetooth is Disable");
            state = true;
        }else if(bluetoothAdapter.isEnabled()){
            String address = bluetoothAdapter.getAddress();
            String name = bluetoothAdapter.getName();
            System.out.println("Bluetooth is Enables : Name : "+name+"Address : "+address);
            state = false;
        }
        return  state;
    }
}
