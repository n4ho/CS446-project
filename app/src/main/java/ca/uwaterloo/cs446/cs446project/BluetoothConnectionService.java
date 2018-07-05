package ca.uwaterloo.cs446.cs446project;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by yuqinghe on 2018/7/4.
 */

public class BluetoothConnectionService {
    private  static final String appName = "FerryMan";

    private static final UUID my_uuid = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private final BluetoothAdapter myBluetoothAdapter;
    Context mContext;

    private AcceptThread mInsecureAcceptThread;
    private  ConnectThread mConnectThread;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;

    private ConnectedThread mConnectedThread;

    private BluetoothDevice mmDevice;

    public BluetoothConnectionService(Context context) {
        mContext = context;
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();

    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            //create new listening server socket
            try {
                tmp = myBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, my_uuid);
            } catch (IOException e) {

            }

            mmServerSocket = tmp;

        }

        public void run() {
            BluetoothSocket socket = null;

            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {

            }

            if (socket != null) {
                connected(socket, mmDevice);
            }
        }

        public void cancel() {
            try{
                mmServerSocket.close();
            } catch (IOException e) {

            }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        public ConnectThread(BluetoothDevice device, UUID uuid) {
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run() {
            BluetoothSocket tmp = null;
            try {
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                //e.printStackTrace();
            }

            mmSocket = tmp;

            myBluetoothAdapter.cancelDiscovery();

            try {
                mmSocket.connect();
            } catch (IOException e) {
                //close the socket
                try {
                    mmSocket.close();
                } catch (IOException e1) {

                }
            }

            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try{
                mmSocket.close();
            } catch (IOException e) {

            }
        }
    }

    //initiating start thread
    public synchronized void start() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }

    }

    public void startClient(BluetoothDevice device, UUID uuid) {
        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth", "Please wait...", true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    private class ConnectedThread extends  Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tempin = null;
            OutputStream tempout = null;

            //dismiss the progressdialog when connection is established
            mProgressDialog.dismiss();

            try {
                tempin = mmSocket.getInputStream();
                tempout = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tempin;
            mmOutStream = tempout;
        }

        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String incommingMessage = new String(buffer, 0, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        //call this from the mainactivity to send data to remote device
        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {

            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }
    }

    private void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        //start the thread to manage the connection and perform transmission
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }

    public void write(byte[] out) {
        ConnectedThread r;
        mConnectedThread.write(out);
    }

}
