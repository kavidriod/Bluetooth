package com.apps.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Kavitha on 5/29/2017.
 */

/*Connecting as a client is simple. Your first obtain the RFCOMM socket from the desired BluetoothDevice
 by calling createRfcommSocketToServiceRecord(), passing in a UUID,
  a 128-bit value that you create. The UUID is similar to a port number.
  */

public class ConnectThread extends  Thread {

    private BluetoothSocket bluetoothSocket;
    private String TAG = getClass().getSimpleName();

public boolean connect(BluetoothDevice bluetoothDevice, UUID uuid){
    BluetoothSocket tmpBluetoothSocket = null;
    try {
        tmpBluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        bluetoothSocket = tmpBluetoothSocket;
    }catch (IOException e){
        Log.i(TAG,"Could not create RFCOMM socket: " + e.toString());
        return  false;
    }

try {
   /* Once the BluetoothSocket is created, you call connect() on the BluetoothSocket.
            This will initialize a connection with the BluetoothDevice through the RFCOMM socket.*/
    bluetoothSocket.connect();
}
catch (IOException e){
    Log.i(TAG,"Could not Connect : " + e.toString());
    try {
        bluetoothSocket.close();
    }catch (IOException ee){
        Log.i(TAG,"Could not Close : " + ee.toString());
        return false;
    }
}
    return  true;
}

    private boolean cancel(){
        try {
            bluetoothSocket.close();
        }catch (IOException e){
            Log.i(TAG,"Could not Close : " + e.toString());
            return false;
        }
        return true;
    }

}
