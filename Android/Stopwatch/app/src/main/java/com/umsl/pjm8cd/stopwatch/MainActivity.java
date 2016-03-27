package com.umsl.pjm8cd.stopwatch;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements StopwatchViewFragment.StopwatchFragmentDelegate,
        StopwatchModel.StopwatchModelDelegate,
        LapViewFragment.LapViewDelegate {

    StopwatchModel model;
    StopwatchViewFragment stopwatchView;
    LapViewFragment lapView;

    public static final String MODEL_TAG = "MODEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        FragmentManager manager = getSupportFragmentManager();

        model = (StopwatchModel) manager.findFragmentByTag(MODEL_TAG);
        if(model == null) {
            model = new StopwatchModel();
            manager.beginTransaction().add(model, MODEL_TAG).commit();
        }
        model.setDelegate(this);

        stopwatchView = (StopwatchViewFragment) manager.findFragmentById(R.id.timer_layout);
        if(stopwatchView == null) {
            stopwatchView = new StopwatchViewFragment();
            manager.beginTransaction().add(R.id.timer_layout, stopwatchView).commit();
        }
        stopwatchView.setDelegate(this);

        lapView = (LapViewFragment) manager.findFragmentById(R.id.lap_layout);
        if(lapView == null) {
            lapView = new LapViewFragment();
            manager.beginTransaction().add(R.id.lap_layout, lapView).commit();
        }
        lapView.setDelegate(this);

        if(model.getLaps().size() != 0) {
            showLapView();
        }
    }

    /* The onDestroy and OnStart methods make sure that the handler is stopped and started when timer updates aren't needed. */
    @Override
    protected void onStart() {
        super.onStart();
        model.updateTimer();
        if(model.isRunning()) {
            model.startRunning();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        model.stopRunning();
    }

    @Override
    public void startStop(Date time) {
        model.startStop(time);
    }

    @Override
    public void lapReset(Date time) {
        model.lapReset(time);

    }

    @Override
    public void updateTime(int totalElapsedTime, int totalLapTime) {
        stopwatchView.updateTime(totalElapsedTime, totalLapTime);
    }

    public void showLapView() {
        findViewById(R.id.lap_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLapView() {
        findViewById(R.id.lap_layout).setVisibility(View.GONE);
    }

    @Override
    public List<String> getLapTimes() {
        return model.getLaps();
    }
}
