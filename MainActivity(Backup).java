package com.example.aammu.homeautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener{
    private static final String TAG = "TEST";
    Switch f1, f2, f3, f4,  l1, l2, l3, l4 ;
    Button afOn, afOff,alOn,alOff;
    private DatabaseReference mDatabase;
    ValueEventListener getDataListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f1 = (Switch) findViewById(R.id.id_fan1);
        f2 = (Switch) findViewById(R.id.id_fan2);
        f3 = (Switch) findViewById(R.id.id_fan3);
        f4 = (Switch) findViewById(R.id.id_fan4);
        afOn = (Button) findViewById(R.id.id_all_fans_on);
        afOff = (Button) findViewById(R.id.id_all_fans_off);
        l1 = (Switch) findViewById(R.id.id_light1);
        l2 = (Switch) findViewById(R.id.id_light2);
        l3 = (Switch) findViewById(R.id.id_light3);
        l4 = (Switch) findViewById(R.id.id_light4);
        alOn = (Button) findViewById(R.id.id_all_lights_on);
        alOff = (Button) findViewById(R.id.id_all_lights_off);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("MyHome");

        getDataFromFirebase();


        getDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Home home = dataSnapshot.getValue(Home.class);
                System.out.println(home.getF1());
                /*if(home.getF1())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getF2())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getF3())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getF4())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getL1())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getL2())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getL3())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);
                if(home.getL4())
                    f1.setChecked(true);
                else
                    f1.setChecked(false);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(getDataListener);
        f1.setOnCheckedChangeListener(this);
        f2.setOnCheckedChangeListener(this);
        f3.setOnCheckedChangeListener(this);
        f4.setOnCheckedChangeListener(this);
        afOn.setOnClickListener(this);
        afOff.setOnClickListener(this);
        l1.setOnCheckedChangeListener(this);
        l2.setOnCheckedChangeListener(this);
        l3.setOnCheckedChangeListener(this);
        l4.setOnCheckedChangeListener(this);
        alOn.setOnClickListener(this);
        alOff.setOnClickListener(this);

    }

    private void getDataFromFirebase() {

    }


    @Override
    public void onCheckedChanged(CompoundButton v, boolean b) {

        valueChanged(v);

    }

    private void valueChanged(View v) {
        final View view = v;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Home home = dataSnapshot.getValue(Home.class);
                switch (view.getId()) {
                    case R.id.id_all_fans_on:
                        mDatabase.child("fan1").setValue(true);
                        mDatabase.child("fan2").setValue(true);
                        mDatabase.child("fan3").setValue(true);
                        mDatabase.child("fan4").setValue(true);
                        f1.setChecked(true);
                        f2.setChecked(true);
                        f3.setChecked(true);
                        f4.setChecked(true);
                    break;
                    case R.id.id_all_lights_on:
                        mDatabase.child("light1").setValue(true);
                        mDatabase.child("light2").setValue(true);
                        mDatabase.child("light3").setValue(true);
                        mDatabase.child("light4").setValue(true);
                        l1.setChecked(true);
                        l2.setChecked(true);
                        l3.setChecked(true);
                        l4.setChecked(true);
                    break;
                    case R.id.id_fan1:
                        if (f1.isChecked()) {
                            mDatabase.child("fan1").setValue(true);
                        } else {
                            mDatabase.child("fan1").setValue(false);
                        }
                        break;
                    case R.id.id_fan2:
                        if (f2.isChecked()) {
                            mDatabase.child("fan2").setValue(true);
                        } else {
                            mDatabase.child("fan2").setValue(false);
                        }
                        break;
                    case R.id.id_fan3:
                        if (f3.isChecked()) {
                            mDatabase.child("fan3").setValue(true);
                        } else {
                            mDatabase.child("fan3").setValue(false);
                        }
                        break;
                    case R.id.id_fan4:
                        if (f4.isChecked()) {
                            mDatabase.child("fan4").setValue(true);
                        } else {
                            mDatabase.child("fan4").setValue(false);
                        }
                        break;
                    case R.id.id_all_fans_off:
                        mDatabase.child("fan1").setValue(false);
                        mDatabase.child("fan2").setValue(false);
                        mDatabase.child("fan3").setValue(false);
                        mDatabase.child("fan4").setValue(false);
                        f1.setChecked(false);
                        f2.setChecked(false);
                        f3.setChecked(false);
                        f4.setChecked(false);
                        break;
                    case R.id.id_light1:
                        if (l1.isChecked()) {
                            mDatabase.child("light1").setValue(true);
                        } else {
                            mDatabase.child("light1").setValue(false);
                        }
                        break;
                    case R.id.id_light2:
                        if (l2.isChecked()) {
                            mDatabase.child("light2").setValue(true);
                        } else {
                            mDatabase.child("light2").setValue(false);
                        }
                        break;
                    case R.id.id_light3:
                        if (l3.isChecked()) {
                            mDatabase.child("light3").setValue(true);
                        } else {
                            mDatabase.child("light3").setValue(false);
                        }
                        break;
                    case R.id.id_light4:
                        if (l4.isChecked()) {
                            mDatabase.child("light4").setValue(true);
                        } else {
                            mDatabase.child("light4").setValue(false);
                        }
                        break;
                    case R.id.id_all_lights_off:
                        mDatabase.child("light1").setValue(false);
                        mDatabase.child("light2").setValue(false);
                        mDatabase.child("light3").setValue(false);
                        mDatabase.child("light4").setValue(false);
                        l1.setChecked(false);
                        l2.setChecked(false);
                        l3.setChecked(false);
                        l4.setChecked(false);
                        break;

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        valueChanged(v);
    }
}

