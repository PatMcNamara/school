package com.umsl.pjm8cd.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tmp on 3/21/2016.
 */
public class StopwatchModel extends Fragment {
    interface StopwatchModelDelegate {
        void updateTime(int totalElapsedTime, int totalLapTime);
        void showLapView();
        void hideLapView();
    }

    Date timeStarted;
    Date lapStarted;
    Date timePaused; // Should be null if timer is running.

    ArrayList<String> laps = new ArrayList<>();

    Handler handler;
    Tick runnable = new Tick();

    StopwatchModelDelegate delegate;

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
            handler.postDelayed(runnable, 1);
        } else if(timePaused != null) { // unpause timer
            long elapsedPauseTime = time.getTime() - timePaused.getTime();
//            Log.v("STOPWATCH", "Elapsed time = " + elapsedPauseTime);
            timeStarted.setTime(timeStarted.getTime() + elapsedPauseTime);
            lapStarted.setTime(lapStarted.getTime() + elapsedPauseTime);
            updateTimer();
            handler.postDelayed(runnable, 1);
            timePaused = null;
        } else { // Stop timer
            timePaused = time;
            updateTimer();
            handler.removeCallbacks(runnable);
        }
    }

    void lapReset(Date time) {
        if(timePaused != null) { /* Reset*/
            timeStarted = timePaused = lapStarted = null;
            delegate.hideLapView();
            updateTimer();
        } else {/* Lap */
            laps.add(StopwatchViewFragment.formatTimeString(time.getTime() - lapStarted.getTime()));
            lapStarted = time;
            delegate.hideLapView();//TODO This is hacky
            delegate.showLapView();
        }
    }

    void updateTimer() {
        if(timeStarted == null) {
            delegate.updateTime(0, 0);
        } else {//TODO send time started and do the calculation in the view.
            delegate.updateTime((int) (System.currentTimeMillis() - timeStarted.getTime()), (int) (System.currentTimeMillis() - lapStarted.getTime()));
        }
    }

    private class Tick implements Runnable {//TODO You could probably just move all this to the class instead of subclassing it.
        @Override
        public void run() {
            updateTimer();
            handler.postDelayed(this, 1);
        }
    }
    public ArrayList<String> getLaps() {
        return laps;
    }
}
