package com.nv.lightshow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;


/**
 * Simple UI demonstrating how to connect to a Bluetooth device,
 * send and receive messages using Handlers, and update the UI.
 */
public class MyActivity extends Activity {

    // Tag for logging
    private static final String TAG = "BluetoothActivity";

    // MAC address of remote Bluetooth device
    // Replace this with the address of your own module
    private final String address = "00:06:66:68:17:DF";

    // The thread that does all the work
    BluetoothThread btt;

    // Handler for writing messages to the Bluetooth connection
    Handler writeHandler;

    /**
     * Launch the Bluetooth thread.
     */
    public void connectButtonPressed() {
        Log.v(TAG, "Connect button pressed.");

        // Only one thread at a time
        if (btt != null) {
            Log.w(TAG, "Already connected!");
            return;
        }

        // Initialize the Bluetooth thread, passing in a MAC address
        // and a Handler that will receive incoming messages
        btt = new BluetoothThread(address, new Handler() {

            @Override
            public void handleMessage(Message message) {

                String s = (String) message.obj;

                // Do something with the message

                if (s.equals("CONNECTED")) {
                    TextView tv = (TextView) findViewById(R.id.statusText);
                    tv.setText("Connected.");
                    Button b1 = (Button) findViewById(R.id.buttonMode1);
                    Button b2 = (Button) findViewById(R.id.buttonMode2);
                    Button b3 = (Button) findViewById(R.id.buttonConnection);
                    b1.setEnabled(true);
                    b2.setEnabled(true);
                    b3.setText("Manage Connection");
                } else if (s.equals("DISCONNECTED")) {
                    TextView tv = (TextView) findViewById(R.id.statusText);
                    Button b1 = (Button) findViewById(R.id.buttonMode1);
                    Button b2 = (Button) findViewById(R.id.buttonMode2);
                    b1.setEnabled(false);
                    b2.setEnabled(false);
                    tv.setText("Disconnected.");
                } else if (s.equals("CONNECTION FAILED")) {
                    TextView tv = (TextView) findViewById(R.id.statusText);
                    tv.setText("Connection failed!");
                    Button b3 = (Button) findViewById(R.id.buttonConnection);
                    b3.setText("Try again to connect");
                    btt = null;
                } else {

                 if(s.charAt(0)=='t'){
                     TextView tv = (TextView) findViewById(R.id.detailText);
                     tv.setText(s);
                 }
                    else {
                     TextView tv = (TextView) findViewById(R.id.flightStatusText);
                     tv.setText(s);
                 }
                }

            }
        });

        // Get the handler that is used to send messages
        writeHandler = btt.getWriteHandler();

        // Run the thread
        btt.start();

    }

    /**
     * Kill the Bluetooth thread.
     */
    public void disconnectButtonPressed() {
        Log.v(TAG, "Disconnect button pressed.");

        if (btt != null) {
            btt.interrupt();
            btt = null;
        }
    }

    /**
     * Send a message using the Bluetooth thread's write handler.
     */

    public void clickStopButton (View v){
        String data = "i";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);

    }
    public void clickStopButtonSmooth (View v){
        String data = "sd";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);

    }
    public void clickButtonThrottleUp(View v) {
        String data = "tu";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonThrottleDown(View v) {
        String data = "td";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonYawUp(View v) {
        String data = "ru";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonYawDown(View v) {
        String data = "rd";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonYawCenter(View v) {
        String data = "rc";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonElevatorUp(View v) {
        String data = "vu";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonElevatorDown(View v) {
        String data = "vd";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonElevatorCenter(View v) {
        String data = "vc";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonAileronUp(View v) {
        String data = "pu";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonAileronDown(View v) {
        String data = "pd";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonAileronCenter(View v) {
        String data = "pc";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }
    public void clickButtonArmit(View v) {
        String data = "a";
        Message msg = Message.obtain();
        msg.obj = data;
        writeHandler.sendMessage(msg);
    }

    public void clickButtonDisarmit(View v) {
        String data = "d";
        Message msg = Message.obtain();
        msg.obj=data;
        writeHandler.sendMessage(msg);
    }

    public void clickButton(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Manage Connection");
        builder.setMessage("Please press Button");
        builder.setCancelable(false);
        builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                connectButtonPressed();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Disconnect.", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {

                disconnectButtonPressed();
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button b1 = (Button) findViewById(R.id.buttonMode1);
        Button b2 = (Button) findViewById(R.id.buttonMode2);
        b1.setEnabled(false);
        b2.setEnabled(false);
        //Button b = (Button) findViewById(R.id.writeButton);
        //b.setEnabled(false);
    }

    /**
     * Kill the thread when we leave the activity.
     */
    protected void onPause() {
        super.onPause();

        if(btt != null) {
            btt.interrupt();
            btt = null;
        }
    }
}

