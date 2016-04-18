package com.umsl.pjm8cd.stopwatch;

import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tmp on 3/21/2016.
 */
public class StopwatchModel extends Fragment implements Runnable {
    private StopwatchModelDelegate delegate;
    interface StopwatchModelDelegate {
        void updateTime(int totalElapsedTime, int totalLapTime);
        void showLapView();
        void hideLapView();
    }

    private Date timeStarted, lapStarted;
    private Date timePaused; // Should be null if timer is running.

    private ArrayList<String> laps = new ArrayList<>();

    private Handler handler;

    public StopwatchModel() {
        setRetainInstance(true);
        handler = new Handler();
    }

    public void setDelegate(StopwatchModelDelegate delegate) {
        this.delegate = delegate;
    }

    void startStop(Date time) {
        if(timeStarted == null) { // Start timer
            timeStarted = time;
            lapStarted = (Date) time.clone();
            startRunning();
        } else if(timePaused != null) { // unpause timer
            long elapsedPauseTime = time.getTime() - timePaused.getTime();
            timeStarted.setTime(timeStarted.getTime() + elapsedPauseTime);
            lapStarted.setTime(lapStarted.getTime() + elapsedPauseTime);
            timePaused = null;

            updateTimer();
            startRunning();
        } else { // Stop timer
            timePaused = time;
            updateTimer();
            stopRunning();
        }
    }

    void lapReset(Date time) {
        if(timePaused != null) { /* Reset*/
            timeStarted = timePaused = lapStarted = null;
            laps.clear();
            delegate.hideLapView();
            updateTimer();
        } else if(timeStarted != null){ /* Lap */
            laps.add(StopwatchViewFragment.formatTimeString(time.getTime() - lapStarted.getTime()));
            lapStarted = time;
            delegate.hideLapView();
            delegate.showLapView();
        }
    }

    void updateTimer() {
        if(timeStarted == null) {
            delegate.updateTime(0, 0);
        } else {
            long start, lap;
            if(timePaused == null) {
                start = timeStarted.getTime();
                lap = lapStarted.getTime();
            } else { // If we are stopped, we must take the time we have spent paused into account.
                start = (System.currentTimeMillis() - timePaused.getTime()) + timeStarted.getTime();
                lap = (System.currentTimeMillis() - timePaused.getTime()) + lapStarted.getTime();
            }
            delegate.updateTime((int) (System.currentTimeMillis() - start), (int) (System.currentTimeMillis() - lap));
        }
    }

    public void stopRunning() {
        handler.removeCallbacks(this);
    }
    public void startRunning() {
        handler.postDelayed(this, 1);
    }

    @Override
    public void run() {
        updateTimer();
        handler.postDelayed(this, 1);
    }

    public ArrayList<String> getLaps() {
        return laps;
    }

    public boolean isRunning() {
        if(timeStarted != null && timePaused == null)
            return true;
        return false;
    }
}
