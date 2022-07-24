package com.neopric.neoscameraman;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class RemoteControl extends Activity {

    Button btnOn, btnOff, btnDis ,   btnPanRight , btnPanLeft  , btnDollyRight , btnDollyForward ,btnDollyBackward, btnDollyLeft , btnTiltDown , btnTiltUp , btnTripodHeightUp ,btnTripodHeightDown ;
    //SeekBar brightness;
    TextView lumn;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    int currentPanPos;
    int currentTiltPos;
    int panMaxAngle =210 ;
    int panMinAngle =0 ;
    int tiltMaxAngle=360;
    int tiltMinAngle=180;
    int currentTiltAngle =180;
    int panDelayseconds=25 ;
    int tiltDelayseconds =25;
    int dollyDelayseconds=1000 ;
    int tripodDelaysecond=1000 ;
    char panRight='F';
    char panLeft ='E';
    char tiltUp='G';
    char tiltDown='H';
    String currentTiltDirection ="0";
   char tripodUp='C';
   char tripodDown='D';
   char dollyForward ='I';
   char dollyBackward ='J';
   char dollyLeft='K';
   char dollyRight='L';



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_led_control);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        //view of the ledControl layout
        setContentView(R.layout.activity_led_control);

        //call the widgtes
      //  btnOn = (Button)findViewById(R.id.btnOn);
      //  btnOff = (Button)findViewById(R.id.btnOff);
        btnPanRight = (Button)findViewById(R.id.btnPanRight);
        btnPanLeft = (Button)findViewById(R.id.btnPanLeft);
        btnDollyRight = (Button)findViewById(R.id.btnDollyRight);
        btnDollyBackward = (Button)findViewById(R.id.btnDollyBackward);
        btnDollyForward = (Button)findViewById(R.id.btnDollyForward);
        btnDollyLeft = (Button)findViewById(R.id.btnDollyLeft);
        btnTiltDown = (Button)findViewById(R.id.btnTiltDown);
        btnTiltUp = (Button)findViewById(R.id.btnTiltUp);
        btnTripodHeightUp = (Button)findViewById(R.id.btnTripodHeightUp);
        btnTripodHeightDown = (Button)findViewById(R.id.btnTripodHeightDown);
        btnDis = (Button)findViewById(R.id.btnDisconnect);


        new ConnectBT().execute(); //Call the class to connect

        //commands to be sent to bluetooth


        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        btnDollyBackward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
MoveRobot(dollyBackward,0,dollyDelayseconds);
            }
        });
        btnDollyForward.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(dollyForward,0,dollyDelayseconds);
            }
        });
        btnDollyLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(dollyLeft,0,dollyDelayseconds);
            }
        });
        btnDollyRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(dollyRight,0,dollyDelayseconds);
            }
        });
        btnPanLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(panLeft,panMinAngle,panDelayseconds);
            }
        });
        btnPanRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(panRight,panMaxAngle,panDelayseconds);
            }
        });
        btnTiltDown.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(tiltDown,tiltMinAngle,tiltDelayseconds);
            }
        });
        btnTiltUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(tiltUp,tiltMaxAngle,tiltDelayseconds);
            }
        });
        btnTripodHeightDown.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(tripodDown,0,tripodDelaysecond);
            }
        });
        btnTripodHeightUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MoveRobot(tripodUp,0,tripodDelaysecond);
            }
        });


    }

    private void Disconnect()
    {
        if(btSocket != null)//if btSocket is busy
        {
            try {
                btSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finish();//return to the first layout
    }

    private void MoveRobot(char movementChar,int angle,int delaySeconds)
    {
        //Not using seconds at the moment
        if(btSocket != null)
        {
            try {
                btSocket.getOutputStream().write((String.valueOf(movementChar)+","+String.valueOf(angle)+","+String.valueOf(delaySeconds)).toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private class ConnectBT extends AsyncTask<Void,Void,Void>{

        private boolean ConnectSuccess = true;

        private void msg(String s)
        {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(RemoteControl.this,"Connecting...","Please wait!!!");

        }

        @Override
        protected Void doInBackground(Void... params) {
            //while the progress is show, the connection is done in background
            try
            {
                if(btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    //connect to the device address
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    //Create an RFCOMM(SPP) connection
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//startConnection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //after doBackground, check if everything went fine
            super.onPostExecute(aVoid);
            if(!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
                //finish();
            }
            progress.dismiss();
        }
    }
}
