package connecting;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by user on 2/15/2017.
 */

public class ServerThread extends Thread {

    private BluetoothSocket bluetoothSocket;
    private String TAG = "ServerThread";

    public  ServerThread(){

    }

    public void acceptConnect(BluetoothAdapter bluetoothAdapter, UUID uuid){

        BluetoothServerSocket temp = null;

        try {
            temp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("Service_Name",uuid);
        }
        catch (IOException e){
            Log.i(TAG,"Could not connect to BluetoothServerSocket");
            e.printStackTrace();
        }

        while (true){
            try {
                bluetoothSocket = temp.accept();
            }
            catch (IOException e){
                Log.i(TAG,"Could not accept Incoming Connection");
                break;
            }

            if (bluetoothSocket != null){
                try {
                    temp.close();
                }
                catch (IOException e){
                    Log.i(TAG,"Could not close ServerSocket");
                }
                break;
            }
        }
    }


    public  void  closeConnect(){
        try {
            bluetoothSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Log.i(TAG,"Could not close ServerSocket");
        }
    }

}
