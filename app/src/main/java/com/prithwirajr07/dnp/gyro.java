package com.prithwirajr07.dnp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class gyro {
    public interface Listner{
        void onRotation(float rx,float ry, float rz);
    }
    private Listner listner;

    public void setListner(Listner l) {
        listner = l;
    }

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;

    gyro(Context context){
        sensorManager=(SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(listner!=null){
                    listner.onRotation(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    public void register(){
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void unregister(){
        sensorManager.unregisterListener(sensorEventListener);
    }

}
