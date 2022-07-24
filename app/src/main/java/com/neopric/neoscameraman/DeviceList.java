package com.neopric.neoscameraman;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class DeviceList extends Activity {

    //widgets
    Button btnPaired;
    ListView devicelist;

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        //Calling widgets
        btnPaired = (Button)findViewById(R.id.button);
        devicelist = (ListView)findViewById(R.id.listView);

        //get default bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        //if device has no bluetooth
        if(myBluetooth == null)
        {
            //Show a mensage that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //quit finish apk
             finish();
        }
        //else if device bluetooth is turn off
        else if (!myBluetooth.isEnabled()) {

            //Ask to the user turn the bluetooth on
            Intent turnBton = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBton,1);
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
        }
        });
    }

    private void pairedDevicesList()
    {
        pairedDevices = myBluetooth.getBondedDevices();
        ArrayList list = new ArrayList();
        if(pairedDevices.size()>0)
        {
            for(BluetoothDevice bt:pairedDevices)
            {
                //Get the device's name and the address
                list.add(bt.getName() + "\n" + bt.getAddress());

            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        devicelist.setAdapter(adapter);
        ////Method called when the device from the list is clicked
        devicelist.setOnItemClickListener(myListClickListener);
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView)view).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity.
            Intent i = new Intent(DeviceList.this, RemoteControl.class);
            //Change the activity
            i.putExtra(EXTRA_ADDRESS, address); //this will be received at ledControl (class) Activity
            Log.e("device_address",address);
            startActivity(i);
            finish();
        }
    };

}
