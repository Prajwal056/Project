package com.example.iotcontrol3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pusher.pushnotifications.PushNotifications;


public class Main2Activity extends AppCompatActivity {

    Button on;
    Button off;
    TextView fire_sensor_data;
    TextView vibration_sensor_status;
    TextView textView4,textView,textView2,textView3;
    Animation right,top;
    DatabaseReference dref;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        PushNotifications.start(getApplicationContext(), "52c6e414-9b94-4339-9105-53323844f1a3");
        PushNotifications.subscribe("hello");

        textView4 = findViewById(R.id.textView4);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        textView4.setAnimation(top);
        textView = findViewById(R.id.textView);
        right = AnimationUtils.loadAnimation(this, R.anim.right);
        textView.setAnimation(right);
        textView2 = findViewById(R.id.textView2);
        right = AnimationUtils.loadAnimation(this, R.anim.right);
        textView2.setAnimation(right);
        textView3 = findViewById(R.id.textView3);
        right = AnimationUtils.loadAnimation(this, R.anim.right);
        textView3.setAnimation(right);


        on=(Button) findViewById(R.id.on);
        on.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Toast.makeText(Main2Activity.this,"On button clicked",Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                myRef.setValue(1);
            }
        });

        fire_sensor_data = (TextView)findViewById(R.id.fd);
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("fire_sensor_data").getValue().toString();
                fire_sensor_data.setText(status);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        vibration_sensor_status = (TextView)findViewById(R.id.vd);
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                status=dataSnapshot.child("vibration_sensor_status").getValue().toString();
                vibration_sensor_status.setText(status);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        off=(Button) findViewById(R.id.off);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this,"Off button clicked",Toast.LENGTH_SHORT).show();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("LED_STATUS");
                myRef.setValue(0);

            }
        });
    }
}




