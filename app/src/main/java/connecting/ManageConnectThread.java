package connecting;

import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by user on 2/16/2017.
 */

public class ManageConnectThread extends  Thread {

    public  ManageConnectThread(){

    }

    public void sendData(BluetoothSocket socket,int data) throws IOException{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4);
        outputStream.write(data);
        OutputStream outputStream1 = socket.getOutputStream();
        outputStream.write(outputStream.toByteArray());
    }

    public int receiverData(BluetoothSocket socket) throws IOException{
            byte[] buffer = new byte[4];
           ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        InputStream inputStream1 = socket.getInputStream();
        inputStream1.read(buffer);
        return inputStream1.read();
    }

}
