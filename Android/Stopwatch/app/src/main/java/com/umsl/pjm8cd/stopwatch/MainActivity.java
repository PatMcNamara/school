package com.umsl.pjm8cd.stopwatch;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements StopwatchViewFragment.StopwatchFragmentDelegate, StopwatchModel.StopwatchModelDelegate{

    StopwatchModel model;
    StopwatchViewFragment stopwatchView;

    public static final String MODEL_TAG = "MODEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        FragmentManager manager = getSupportFragmentManager();

        stopwatchView = (StopwatchViewFragment) manager.findFragmentById(R.id.blank_layout);
        if(stopwatchView == null) {
            stopwatchView = new StopwatchViewFragment();
            manager.beginTransaction().add(R.id.blank_layout, stopwatchView).commit();
            stopwatchView.setDelegate(this);
        }

        model = (StopwatchModel) manager.findFragmentByTag(MODEL_TAG);//TODO create the tag
        if(model == null) {
            model = new StopwatchModel();
            manager.beginTransaction().add(model, MODEL_TAG).commit();
            model.setDelegate(this);
        }
    }


    @Override
    public void startStop(Date time) {
        model.startStop(time);
    }

    @Override
    public void lapReset(Date time) {
        model.lapReset();
    }

    @Override
    public void updateTime(int elapsedTime) {
        stopwatchView.updateTime(elapsedTime);
    }
}
