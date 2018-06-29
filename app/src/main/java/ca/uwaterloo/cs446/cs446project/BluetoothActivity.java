package ca.uwaterloo.cs446.cs446project;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends Activity {

    Button On;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        On=(Button)findViewById(R.id.BT_On);
        BA = BluetoothAdapter.getDefaultAdapter();

        On.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!BA.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    //Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
                }

                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);


                pairedDevices = BA.getRemoteDevices();

                ArrayList list = new ArrayList();

                for(BluetoothDevice bt : pairedDevices) {
                    list.add(bt.getName());
                    System.out.println(bt.getName());
                }


                //Toast.makeText(getApplicationContext(), "Showing Paired Devices",Toast.LENGTH_SHORT).show();

                //final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);

                //lv.setAdapter(adapter);


            }
        });

    }

}
