package com.example.aammu.homeautomation;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TEST";
    Switch f1, f2, f3, f4,  l1, l2, l3, l4 ,afOn, alOn, afOff , alOff;
    private DatabaseReference mDatabase;
    ValueEventListener getDataListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.id_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                getFirebaseData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        f1 = (Switch) findViewById(R.id.id_fan1);
        f2 = (Switch) findViewById(R.id.id_fan2);
        f3 = (Switch) findViewById(R.id.id_fan3);
        f4 = (Switch) findViewById(R.id.id_fan4);
        afOn = (Switch) findViewById(R.id.id_all_fans_on);
        afOff = (Switch) findViewById(R.id.id_all_fans_off);
        l1 = (Switch) findViewById(R.id.id_light1);
        l2 = (Switch) findViewById(R.id.id_light2);
        l3 = (Switch) findViewById(R.id.id_light3);
        l4 = (Switch) findViewById(R.id.id_light4);
        alOn = (Switch) findViewById(R.id.id_all_lights_on);
        alOff = (Switch) findViewById(R.id.id_all_lights_off);
        getFirebaseData();
        f1.setOnClickListener(this);
        f2.setOnClickListener(this);
        f3.setOnClickListener(this);
        f4.setOnClickListener(this);
        afOn.setOnClickListener(this);
        afOff.setOnClickListener(this);
        l1.setOnClickListener(this);
        l2.setOnClickListener(this);
        l3.setOnClickListener(this);
        l4.setOnClickListener(this);
        alOn.setOnClickListener(this);
        alOff.setOnClickListener(this);
    }
    private void getFirebaseData() {
        if (AppStatus.getInstance(this).isOnline()) {
            Toast.makeText(getApplicationContext(),"Intializing....",Toast.LENGTH_LONG).show();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("MyHome");
            getDataListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Home home = dataSnapshot.getValue(Home.class);
                    System.out.println(home.getFan2());
                    if(home.getFan1())
                        f1.setChecked(true);
                    else
                        f1.setChecked(false);
                    if(home.getFan2())
                        f2.setChecked(true);
                    else
                        f2.setChecked(false);
                    if(home.getFan3())
                        f3.setChecked(true);
                    else
                        f3.setChecked(false);
                    if(home.getFan4())
                        f4.setChecked(true);
                    else
                        f4.setChecked(false);
                    if(home.getLight1())
                        l1.setChecked(true);
                    else
                        l1.setChecked(false);
                    if(home.getLight2())
                        l2.setChecked(true);
                    else
                        l2.setChecked(false);
                    if(home.getLight3())
                        l3.setChecked(true);
                    else
                        l3.setChecked(false);
                    if(home.getLight4())
                        l4.setChecked(true);
                    else
                        l4.setChecked(false);
                    if(home.getFan2()&&home.getFan3()&&home.getFan4()&&home.getFan1()) {
                        f1.setChecked(true);
                        f2.setChecked(true);
                        f3.setChecked(true);
                        f4.setChecked(true);
                        afOn.setChecked(true);
                        afOff.setChecked(false);
                    }
                    else if(!home.getFan2()&&!home.getFan3()&&!home.getFan4()&&!home.getFan1()){
                        f1.setChecked(false);
                        f2.setChecked(false);
                        f3.setChecked(false);
                        f4.setChecked(false);
                        afOn.setChecked(false);
                        afOff.setChecked(true);

                    }
                    if(home.getLight1()&&home.getLight2()&&home.getLight3()&&home.getLight4()){
                        l1.setChecked(true);
                        l2.setChecked(true);
                        l3.setChecked(true);
                        l4.setChecked(true);
                        alOn.setChecked(true);
                        alOff.setChecked(false);
                    }
                    else if(!home.getLight1()&&!home.getLight2()&&!home.getLight3()&&!home.getLight4()){
                        l1.setChecked(false);
                        l2.setChecked(false);
                        l3.setChecked(false);
                        l4.setChecked(false);
                        alOn.setChecked(false);
                        alOff.setChecked(false);
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            mDatabase.addListenerForSingleValueEvent(getDataListener);


        } else {

            Toast.makeText(this,"Enable Internet Connection...\nThen\n... Swipe Down to Refresh",Toast.LENGTH_SHORT).show();
        }
    }




    private void valueChanged(View v) {
        final View view = v;
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Home home = dataSnapshot.getValue(Home.class);
                switch (view.getId()) {

                    case R.id.id_fan1:
                        if (f1.isChecked()) {
                            home.setFan1(true);
                            afOff.setChecked(false);
                        } else {
                            home.setFan1(false);
                            afOn.setChecked(false);
                        }
                        break;
                    case R.id.id_fan2:
                        if (f2.isChecked()) {
                            home.setFan2(true);
                            afOff.setChecked(false);
                        } else {
                            home.setFan2(false);
                            afOn.setChecked(false);
                        }
                        break;
                    case R.id.id_fan3:
                        if (f3.isChecked()) {
                            home.setFan3(true);
                            afOff.setChecked(false);
                        } else {
                            home.setFan3(false);
                            afOn.setChecked(false);
                        }
                        break;
                    case R.id.id_fan4:
                        if (f4.isChecked()) {
                            home.setFan4(true);
                            afOff.setChecked(false);
                        } else {
                            home.setFan4(false);
                            afOn.setChecked(false);
                        }
                        break;
                    case R.id.id_light1:
                        if (l1.isChecked()) {
                            home.setLight1(true);
                            alOff.setChecked(false);
                        } else {
                            home.setLight1(false);
                            alOn.setChecked(false);
                        }
                        break;
                    case R.id.id_light2:
                        if (l2.isChecked()) {
                            home.setLight2(true);
                            alOff.setChecked(false);
                        } else {
                            home.setLight2(false);
                            alOn.setChecked(false);
                        }
                        break;
                    case R.id.id_light3:
                        if (l3.isChecked()) {
                            home.setLight3(true);
                            alOff.setChecked(false);
                        } else {
                            home.setLight3(false);
                            alOn.setChecked(false);
                        }
                        break;
                    case R.id.id_light4:
                        if (l4.isChecked()) {
                            home.setLight4(true);
                            alOff.setChecked(false);
                        } else {
                            home.setLight4(false);
                            alOn.setChecked(false);
                        }
                        break;
                    case R.id.id_all_fans_on:
                        if(afOn.isChecked()){
                            home.setFan1(true);
                            home.setFan2(true);
                            home.setFan3(true);
                            home.setFan4(true);
                            f1.setChecked(true);
                            f2.setChecked(true);
                            f3.setChecked(true);
                            f4.setChecked(true);
                            afOff.setChecked(false);
                        }
                        break;
                    case R.id.id_all_fans_off:
                        if(afOff.isChecked())
                        {
                            home.setFan1(false);
                            home.setFan2(false);
                            home.setFan3(false);
                            home.setFan4(false);
                            f1.setChecked(false);
                            f2.setChecked(false);
                            f3.setChecked(false);
                            f4.setChecked(false);
                            afOn.setChecked(false);
                        }
                        break;
                    case R.id.id_all_lights_on:
                        if(alOn.isChecked()){
                            home.setLight1(true);
                            home.setLight2(true);
                            home.setLight3(true);
                            home.setLight4(true);
                            l1.setChecked(true);
                            l2.setChecked(true);
                            l3.setChecked(true);
                            l4.setChecked(true);
                            alOff.setChecked(false);
                        }
                        break;
                    case R.id.id_all_lights_off:
                        if(alOff.isChecked()) {
                            home.setLight1(false);
                            home.setLight2(false);
                            home.setLight3(false);
                            home.setLight4(false);
                            l1.setChecked(false);
                            l2.setChecked(false);
                            l3.setChecked(false);
                            l4.setChecked(false);
                            alOn.setChecked(false);
                        }
                        break;

                }
                mDatabase.setValue(home);
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
