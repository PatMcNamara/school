package com.umsl.pjm8cd.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.Date;

/**
 * Created by tmp on 3/21/2016.
 */
public class StopwatchModel extends Fragment {
    interface StopwatchModelDelegate {
        void updateTime(int elapsedTime);
    }

    Date timeStarted;
    Date timePaused; // Should be null if timer is running.

    Handler handler;
    Tick runnable = new Tick();

    StopwatchModelDelegate delegate;

    public StopwatchModel() {
        setRetainInstance(true);
        handler = new Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setDelegate(StopwatchModelDelegate delegate) {
        this.delegate = delegate;
    }

    void startStop(Date time) {
        if(timeStarted == null) { // Start timer
            timeStarted = time;
            handler.postDelayed(runnable, 1);
        } else if(timePaused != null) { // unpause timer
            long elapsedPauseTime = System.currentTimeMillis() - timePaused.getTime();
            timeStarted.setTime(timeStarted.getTime() + elapsedPauseTime);
            updateTimer();
            handler.postDelayed(runnable, 1);
            timePaused = null;
        } else { // Stop timer
            timePaused = new Date();
            updateTimer();
            handler.removeCallbacks(runnable);
        }
    }

    void lapReset() {
        if(timePaused != null) { /* Reset*/
            timeStarted = timePaused = null;
            updateTimer();
        } else {/* lap */
            //TODO
        }
    }

    void updateTimer() {
        if(timeStarted == null) {
            delegate.updateTime(0);
        } else {
            delegate.updateTime((int) (System.currentTimeMillis() - timeStarted.getTime()));
        }
    }

    private class Tick implements Runnable {//TODO You could probably just move all this to the class insted of subclassing it.
        @Override
        public void run() {
            updateTimer();
            handler.postDelayed(this, 1);
        }
    }
}
