package connecting;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by user on 2/15/2017.
 */

public class ConnectThread extends  Thread {

    private BluetoothSocket bluetoothSocket;
    String TAG = "ConnectThread";

    public boolean connect(BluetoothDevice bluetoothDevice, UUID uuid){
        BluetoothSocket tmp;
        try {
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);

        }
        catch (IOException e){
            Log.i(TAG,"Could not create RFCOMM");
            return  false;
        }


        try {
            bluetoothSocket.connect();
        }
        catch (IOException e){
            Log.i(TAG,"Could not Connect");
            e.printStackTrace();
            try{
                bluetoothSocket.close();
            }catch (IOException eclose){
                Log.i(TAG,"Could not Close Connection");
                eclose.printStackTrace();
                return  false;
            }
        }
        return true;
    }


    public boolean cancel(){
        try {
            bluetoothSocket.close();
        }catch (IOException e){
            Log.i(TAG,"Could not close Connection");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
