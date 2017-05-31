package com.apps.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Kavitha on 5/29/2017.
 */


/*First, from your BluetoothAdapter, you must get a BluetoothServerSocket, which will be used to listen for a connection.
        This is only used to obtain the connection's shared RFCOMM socket.
        Once the connection is established, the server socket is no longer need and can be closed by calling close() on it.*/

public class ServerThread extends  Thread {

BluetoothSocket bluetoothSocket;
    private  String  TAG = getClass().getSimpleName();


    public ServerThread(){

    }

public  void acceptConnect(BluetoothAdapter bluetoothAdapter, UUID uuid){
    BluetoothServerSocket tempBluetoothServerSocket = null;

    try {
        tempBluetoothServerSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("Service_Name",uuid);
    }catch (IOException e){
        Log.i(TAG,"Could not get a BluetoothServerSocket:" + e.toString());
    }


    while (true){
        try {
            bluetoothSocket = tempBluetoothServerSocket.accept();
        }catch (IOException e){
            Log.i(TAG,"Could not accept an incoming connection. " + e.toString());
break;
        }


        if (bluetoothSocket != null){
            try {
                tempBluetoothServerSocket.close();
            }catch (IOException e){
                Log.i(TAG,"Could not accept an incoming connection. " + e.toString());
            }
        }
    }

}

    public void closeConnet(){
        try {
bluetoothSocket.close();
        }catch (IOException e){
            Log.i(TAG,"Could not close connection: " + e.toString());
        }
    }

}
