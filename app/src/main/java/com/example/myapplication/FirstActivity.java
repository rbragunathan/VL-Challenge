package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    Button btn;
    private SensorManager sm;
    private float acelVal;
    private float acelLast;
    private float shake;
    double lat;
    double lon;

    DataBaseHelper myDb1;


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        myDb1 =new DataBaseHelper(this);
        btn = (Button)findViewById(R.id.button);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake=0.00f;


    }

    public final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acelLast =acelVal;
            acelVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = acelVal-acelLast;
            shake =shake * 0.9f + delta;

            if(shake > 12)
            {
                GPStracker g= new GPStracker(getApplicationContext());
                Location l = g.getLocation();
                if(l != null)
                {
                    lat = l.getLatitude();
                    lon = l.getLongitude();

                    Toast toast =Toast.makeText(getApplicationContext(), "LAT:"+lat+ " \nLON:"+lon, Toast.LENGTH_LONG);
                   toast.show();

                 int permissionCheck = ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.SEND_SMS);
                    if(permissionCheck == PackageManager.PERMISSION_GRANTED)
                    {
                        MyMessage();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions( FirstActivity.this, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }


                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void btnClick(View view) {

        Intent i = new Intent(this,AddNumber.class);
        startActivity(i);

    }

    public void MyMessage()
    {
        Cursor res = myDb1.getAllData();
        SmsManager smsManager = SmsManager.getDefault();
        if(res != null && res.getCount()>0) {
            if(res.moveToFirst()) {
                do {
            //  String num = res.getString(1);
                smsManager.sendTextMessage(res.getString(1), null, "Latitude:" + lat + "Longitude:" + lon, null, null);

            }while (res.moveToNext());
            res.close();
            Toast.makeText(this,"Message Sent",Toast.LENGTH_LONG).show();

        }}
            else
            {
                Toast.makeText(this,"No Number To Send ",Toast.LENGTH_LONG).show();
            }

        }



}
