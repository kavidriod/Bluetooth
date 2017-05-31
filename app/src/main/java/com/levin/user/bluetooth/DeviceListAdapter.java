package com.levin.user.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/9/2017.
 */

public class DeviceListAdapter extends ArrayAdapter<DeviceListItems> {

    private Context context;
    private BluetoothAdapter bTAdapter;
    private  DeviceListItems deviceListItems;

    public DeviceListAdapter(Context context, List items, BluetoothAdapter bTAdapter) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.bTAdapter = bTAdapter;
        this.context = context;
    }

    private  class ViewHolder{
        TextView deviceName,macAddress;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.device_list_item,null);
            viewHolder = new ViewHolder();

            viewHolder.deviceName = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.macAddress = (TextView) convertView.findViewById(R.id.macAddress);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        deviceListItems = getItem(position);

        viewHolder.deviceName.setText(deviceListItems.getDeviceName());
        viewHolder.macAddress.setText(deviceListItems.getAddress());


        return  convertView;
    }
}
