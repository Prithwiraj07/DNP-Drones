package com.prithwirajr07.dnp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] modeNames = { "Loiter", "Takeoff", "Auto", "Arm", "Disarm"};
    double xdir,ydir,zdir;
    private Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference throttle_fr = database.getReference("Throt");
//    DatabaseReference right_fr = database.getReference("hori_tilt");
//    DatabaseReference verti_fr = database.getReference("ver_tilt");
//    DatabaseReference motion_y_database = database.getReference("motion_y");
//    DatabaseReference Passcode=database.getReference("PassCode");
    DatabaseReference combo=database.getReference("combo");






    private gyro gyroscope;
    private accel accelerometer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView textview=findViewById(R.id.textView);
        TextView textview2=findViewById(R.id.textView2);
        TextView textView_acc=findViewById(R.id.textView_accel);
        Switch motion_switch=findViewById(R.id.switch_motion);
        ToggleButton cam_but=findViewById(R.id.toggleButton_cam);
        WebView webView=findViewById(R.id.web_view);



        //>>>>>>>>>>>>>>>>>>>>>>This is for gyroscopic controls<<<<<<<<<<<<<<<<<<<<<//
        //__________________________________________________________________________//
//        gyroscope=new gyro(this);
//        gyroscope.setListner(new gyro.Listner() {
//            @Override
//            public void onRotation(float rx, float ry, float rz) {
//                textview.setText("Gyro data: X "+rz);
//            }
//        });






        //>>>>>>>>>>>>>>>>>>>>>>This is for accelerometer controls<<<<<<<<<<<<<<<<<<<<<//
        //            It has been later on implemented in the motion switch itself     //
        //_____________________________________________________________________________//



        accelerometer=new accel(this);



//        accelerometer.setListner(new accel.Listner() {
//            @Override
//            public void onTranslation(float rx, float ry, float rz) {
//
//  //<<<<<<<<<<<<<<<<<< Converting float values to two decimal points >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
//
//                xdir=Math.round(rx*100)/100.0;
//                ydir=Math.round(ry*100)/100.0;
//                zdir=Math.round(rz*100)/100.0;
//
//
//
//            }
//        });
        //<<<<<<<<<<<<<<<<< Handling the motion swith >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

        motion_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

    //<<<<<<<<<<<<<<<<<<<<<<<<<<< Handling the Accelerometer Sensor >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//


                    accelerometer.register();
                    accelerometer.setListner(new accel.Listner() {
                        @Override
                        public void onTranslation(float rx, float ry, float rz) {

                            //<<<<<<<<<<<<<<<<<< Converting float values to two decimal points >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

                            //xdir=Math.round(rx*100)/100.0;
                            ydir=Math.round(ry*100)/100.0;
                            //zdir=Math.round(rz*100)/100.0;
                            textView_acc.setText("Acc meter: "+ydir);
                            //motion_y_database.setValue(ydir);
                            double y_multi_dir=1000000*ydir;
                            combo.setValue(y_multi_dir);



                        }
                    });

                } else {
                    accelerometer.unregister();
                    //motion_y_database.setValue(0);
                    combo.setValue(0);

                    textView_acc.setText("Motion Sensor OFF");
                }

            }

        });



        //TextView gps_status =(TextView)findViewById(R.id.gpsstat);
        //TextView battery_monitor =(TextView)findViewById(R.id.bmonitor);
       // TextView internet_speed =(TextView)findViewById(R.id.speedometer);
       // TextView warning_popup =(TextView)findViewById(R.id.WarningShow);


        ////////////////////////////////////////////////////////////////////////////////////////////////
//        FirebaseDatabase.getInstance().getReference().child("Warning").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //Toast.makeText(getApplicationContext(),snapshot.getValue(String.class),Toast.LENGTH_LONG).show();
//                //warning_popup.setText(snapshot.getValue(String.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child("Status").child("GPS_sat").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String val = snapshot.getValue(String.class);
//                //gps_status.setText(val);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Database error found !",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child("Status").child("battery").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int val1 = ((Long) snapshot.getValue()).intValue();
//                //battery_monitor.setText(val1 + "%");
//                if(val1 > 29){
//
//                    //Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.battery);
//                    //Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                    //   DrawableCompat.setTint(wrappedDrawable, Color.GREEN);
//                }
//                else if(val1 < 30){
//                    Toast.makeText(getApplicationContext(),"Low Battery !",Toast.LENGTH_LONG).show();
//                    //  Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.battery);
//                    // Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
//                    //DrawableCompat.setTint(wrappedDrawable, Color.RED);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Database error found !",Toast.LENGTH_SHORT).show();
//            }
//        });

//        FirebaseDatabase.getInstance().getReference().child("Status").child("iot_speed").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Integer val2 = snapshot.getValue(Integer.class);
//                //internet_speed.setText(String.valueOf(val2) + "ms");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Database error found !",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        FirebaseDatabase.getInstance().getReference().child("Warning").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //warning_popup.setText((String) snapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(),"Database error found !",Toast.LENGTH_SHORT).show();
//            }
//        });








        ////////////////////////////////////////////////////////////////////////////////////////////////

//        VideoView videoView = findViewById(R.id.videoView);
//        Uri uri = Uri.parse("gs://udr-databse.appspot.com/vlc.ts");
//
//        videoView.setVideoURI(uri);
//        videoView.start();




        ////////////////////////////////////////////////////////////////////////////////////////////////

        SeekBar ch6Seek = (SeekBar) findViewById(R.id.seekBar);
        ch6Seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                //throttle_fr.setValue(progressChangedValue);
                int plus_progChange=progressChangedValue+1000;
                combo.setValue(plus_progChange);
                textview.setText("Throttle " +progressChangedValue);
//------------------------ use writing firebase here if continuous update required--------------------------------------
//                throttle_fr.setValue(progressChangedValue);
//                textview.setText("Throttle " +progressChangedValue);
// ----------------------------------------------------------------------------------------------------------------------
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //------------------------ use writing firebase here if continuous update not required--------------------------------------
//                throttle_fr.setValue(progressChangedValue);
//                textview.setText("Throttle " +progressChangedValue)
// ----------------------------------------------------------------------------------------------------------------------

            }
        });
        cam_but.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cam_but.isChecked()){
                    webView.setVisibility(View.VISIBLE);
                    webView.setWebViewClient(new WebViewClient());
                    webView.loadUrl("https://www.google.com/");


                }
                else {
                    webView.setVisibility(View.INVISIBLE);
                }
            }
        });


        ////////////////////////////////////////////////////////////////////////////////////////////////
//        Button Modebtn = (Button)findViewById(R.id.button);
//        Modebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase.getInstance().getReference("Channels").child("Mode").setValue("RTL");
//            }
//        });
        ////////////////////////////////////////////////////////////////////////////////////////////////


//        Spinner spinner = (Spinner)findViewById(R.id.modespinner);
//        spinner.setOnItemSelectedListener(this);
//        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,modeNames);
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(aa);



        ////////////////////////////////////////////////////////////////////////////////////////////////


        //JoystickView joystickLeft = (JoystickView) findViewById(R.id.joystickView_left);
        JoystickView joystickRight = (JoystickView) findViewById(R.id.joystickView_right);


//        joystickLeft.setOnMoveListener(new JoystickView.OnMoveListener() {
//            @SuppressLint("DefaultLocale")
//            @Override
//            public void onMove(int angle, int strength) {
//                Integer ThrottleX = 30 + (40 * joystickLeft.getNormalizedX()) / 100 ;
//                Integer ThrottleY = 30 + (40 * joystickLeft.getNormalizedY()) / 100 ;
//
////                FirebaseDatabase.getInstance().getReference("Channels").child("Yaw").setValue(ThrottleX);
////                FirebaseDatabase.getInstance().getReference("Channels").child("Throttle").setValue(ThrottleY);
//
//            }
//        });

        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle1, int strength1) {
                Integer DirectionX = 30 + (40 * joystickRight.getNormalizedX()) / 100 ;
                Integer DirectionY = 30 + (40 * joystickRight.getNormalizedY()) / 100 ;
                //right_fr.setValue(DirectionX);
                //verti_fr.setValue(DirectionY);

                int combiDir=1000*DirectionX + DirectionY;
                combo.setValue(combiDir);
                //combo.setValue(DirectionY);
                textview2.setText("X:"+DirectionX+"  Y:"+DirectionY);


//                FirebaseDatabase.getInstance().getReference("Channels").child("Roll").setValue(DirectionX);
//                FirebaseDatabase.getInstance().getReference("Channels").child("Pitch").setValue(DirectionY);

            }
        });
 //--------------------------------------------------------------------------------------------------------------------|
//____________________________________________________________________________________________________________________ |
        //Reference: https://www.youtube.com/watch?v=wQAD-jBw5CE&t=224s
//___________________________________________________________________________________________________________________  |
//-------------------------------------------------------------------------------------------------------------------- |




        ////////////////////////////////////////////////////////////////////////////////////////////////

    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //FirebaseDatabase.getInstance().getReference("Channels").child("Mode").setValue(modeNames[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onResume(){
        super.onResume();
        //gyroscope.register();
        accelerometer.register();
    }
    @Override
    protected void onPause(){
        super.onPause();
        //gyroscope.unregister();
        accelerometer.unregister();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),login_activity.class));
        finish();
    }
}