package com.levin.user.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeviceListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeviceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceListFragment extends Fragment implements AdapterView.OnItemClickListener{

    private OnFragmentInteractionListener mListener;
   private static BluetoothAdapter bluetoothAdapters;
    private ArrayList<DeviceListItems> deviceListItemses;
    private AbsListView listView;
    private ArrayAdapter<DeviceListItems> arrayAdapter;
    private ToggleButton toggleButton;
    String TAG = "DeviceListFragment";

    public DeviceListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DeviceListFragment newInstance(BluetoothAdapter bluetoothAdapter) {
        DeviceListFragment fragment = new DeviceListFragment();
        bluetoothAdapters = bluetoothAdapter;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        deviceListItemses = new ArrayList<DeviceListItems>();

        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapters.getBondedDevices();

        if (bluetoothDevices.size() > 0){
            for (BluetoothDevice bluetoothDevice : bluetoothDevices){
                deviceListItemses.add(new DeviceListItems(bluetoothDevice.getName(),bluetoothDevice.getAddress(),"false"));
            }
        }
        if (deviceListItemses.size() == 0){
            deviceListItemses.add(new DeviceListItems("No devices Found","","false"));
        }
        arrayAdapter = new DeviceListAdapter(getActivity(),deviceListItemses,bluetoothAdapters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_device_list, container, false);;
        listView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);

        toggleButton = (ToggleButton) view.findViewById(R.id.scan);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
                intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);


                       if (b){
                           arrayAdapter.clear();
                           getActivity().registerReceiver(broadcastReceiver,intentFilter);
                           Log.i(TAG," bluetoothAdapters.isDiscovering() ? "+bluetoothAdapters.isDiscovering());
                           if (bluetoothAdapters.isDiscovering()){
                               bluetoothAdapters.cancelDiscovery();
                           }
                           bluetoothAdapters.startDiscovery();
                       }else {
                           getActivity().unregisterReceiver(broadcastReceiver);
                           bluetoothAdapters.cancelDiscovery();
                       }
            }
        });
        return  view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d("DEVICELIST", "onItemClick position: " + position +
                " id: " + id + " name: " + deviceListItemses.get(position).getDeviceName() + "\n");

        if (mListener != null) {
            mListener.onFragmentInteraction(deviceListItemses.get(position).getDeviceName());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String uri);
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i(TAG," action "+intent.getAction());
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice bluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                DeviceListItems deviceListItems = new DeviceListItems(bluetoothDevice.getName(),bluetoothDevice.getAddress(),"false");
                arrayAdapter.add(deviceListItems);
                arrayAdapter.notifyDataSetChanged();
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)){
                Log.i(TAG," ACTION_DISCOVERY_STARTED ?"+intent.getAction());
                toggleButton.setChecked(true);
            }else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)){
                Log.i(TAG," ACTION_DISCOVERY_FINISHED ? "+intent.getAction());
                toggleButton.setChecked(false);
            }
        }
    };

}
