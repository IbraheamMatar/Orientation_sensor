package com.example.orientationsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //device sensor manager
    private SensorManager sensorManager;
    private Sensor sensorAcc;
    private Sensor sensorMf;
    TextView textView;
    //define the display widget compass picture
    private ImageView image;
    final float[] rotationMatrix = new float[9];
    private float[] arryAcc= new float[3];
    private float[] arrymf= new float[3];
    private float[] values= new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=(ImageView)findViewById(R.id.imageView);
       textView =(TextView)findViewById(R.id.idinfo);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) !=null&&sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) !=null ) {
            sensorMf = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        }else {
            Toast.makeText(this, "is not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            arryAcc = event.values;
        }else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                arrymf = event.values;
        }
        SensorManager.getOrientation(rotationMatrix,values);
        SensorManager.getRotationMatrix(rotationMatrix,null,arryAcc,arrymf);

                int final_val = (int)Math.toDegrees(values[0]);

                if(final_val<0){
                    textView.setText("Val Is "+(final_val+360));
                   image.setRotation(final_val);
                }else {
                    textView.setText("Val Is "+(final_val));
                    image.setRotation(final_val);
                }

        }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
        //not in use
    }




    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensorAcc,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorMf,SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
