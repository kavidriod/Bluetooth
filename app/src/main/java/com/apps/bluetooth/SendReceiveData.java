package com.apps.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Kavitha on 5/29/2017.
 */

public class SendReceiveData extends Thread {

    public void sendDate(BluetoothSocket socket,int data) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4);
        byteArrayOutputStream.write(data);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    public int receiveDate(BluetoothSocket socket) throws IOException{
        byte[] buffer = new byte[4];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        InputStream inputStream = socket.getInputStream();
        inputStream.read(buffer);
        return  byteArrayInputStream.read();
    }
}
