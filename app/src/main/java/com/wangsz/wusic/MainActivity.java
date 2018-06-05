package com.wangsz.wusic;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangsz.musicservice.MusicServiceManager;
import com.wangsz.musicservice.constant.Action;
import com.wangsz.wusic.application.App;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MusicServiceManager.getPlayerInterface().action(Action.PLAY);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.tv_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MusicServiceManager.getPlayerInterface().action(Action.PAUSE);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
