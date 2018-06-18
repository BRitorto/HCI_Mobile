package com.bassanidevelopment.santiago.hci_movil;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

public class NotificationActivity extends AppCompatActivity {

    private Switch blind, lamp, oven, ac, door, refrigerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        blind = findViewById(R.id.blind_notifications);
        lamp = findViewById(R.id.lamp_notifications);
        oven = findViewById(R.id.oven_notifications);
        ac = findViewById(R.id.ac_notifications);
        door = findViewById(R.id.door_notifications);
        refrigerator = findViewById(R.id.refri_notifications);

        blind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("eu0v2xgprrhhg41g");
                else {
                    if(NotificationHandler.allowedTypes.contains("eu0v2xgprrhhg41g"))
                        NotificationHandler.allowedTypes.remove("eu0v2xgprrhhg41g");
                }
            }
        });

        lamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("go46xmbqeomjrsjr");
                else{
                    if(NotificationHandler.allowedTypes.contains("go46xmbqeomjrsjr"))
                    NotificationHandler.allowedTypes.remove("go46xmbqeomjrsjr");
                }
            }
        });

        oven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("im77xxyulpegfmv8");
                else
                {
                    if(NotificationHandler.allowedTypes.contains("im77xxyulpegfmv8"))
                        NotificationHandler.allowedTypes.remove("im77xxyulpegfmv8");
                }
            }
        });

        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("li6cbv5sdlatti0j");
                else
                {
                    if(NotificationHandler.allowedTypes.contains("li6cbv5sdlatti0j"))
                        NotificationHandler.allowedTypes.remove("li6cbv5sdlatti0j");
                }
            }
        });

        door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("lsf78ly0eqrjbz91");
                else
                {
                    if(NotificationHandler.allowedTypes.contains("lsf78ly0eqrjbz91"))
                        NotificationHandler.allowedTypes.remove("lsf78ly0eqrjbz91");
                }
            }
        });

        refrigerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blind.isChecked())
                    NotificationHandler.allowedTypes.add("rnizejqr2di0okho");
                else
                {
                    if(NotificationHandler.allowedTypes.contains("rnizejqr2di0okho"))
                        NotificationHandler.allowedTypes.remove("rnizejqr2di0okho");
                }
            }
        });


        setupSwitched();
    }

    private void setupSwitched(){
        blind.setChecked(NotificationHandler.allowedTypes.contains("eu0v2xgprrhhg41g"));
        lamp.setChecked(NotificationHandler.allowedTypes.contains("go46xmbqeomjrsjr"));
        oven.setChecked(NotificationHandler.allowedTypes.contains("im77xxyulpegfmv8"));
        ac.setChecked(NotificationHandler.allowedTypes.contains("li6cbv5sdlatti0j"));
        door.setChecked(NotificationHandler.allowedTypes.contains("lsf78ly0eqrjbz91"));
        refrigerator.setChecked(NotificationHandler.allowedTypes.contains("rnizejqr2di0okho"));
    }


}
