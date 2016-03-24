package com.umsl.pjm8cd.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by pat on 3/4/2016.
 */
public class StopwatchViewFragment extends Fragment {
    interface StopwatchFragmentDelegate {
        void startStop(Date time);
        void lapReset(Date time);
    }

    StopwatchFragmentDelegate delegate;
    TextView elapsedTime;
    boolean isRunning;

    public void setDelegate(StopwatchFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        isRunning = false;
        elapsedTime = (TextView) view.findViewById(R.id.total_timer);
        final Button startStop = (Button) view.findViewById(R.id.start_button);
        final Button lapReset = (Button) view.findViewById(R.id.reset_button);

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call start/stop with the current date
                if (delegate != null) {
                    delegate.startStop(new Date());
                    if (isRunning) {
                        startStop.setText("Start");
                        lapReset.setText("Reset");
                        isRunning = false;
                    } else {
                        startStop.setText("Stop");
                        lapReset.setText("Lap");
                        isRunning = true;
                    }
                } else {
                    Log.e("Stopwatch", "Stop/start button pressed while view fragment has no delegate set.");
                }

            }
        });
        lapReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call reset with the current date
                if (delegate != null) {
                    delegate.lapReset(new Date());
                } else {
                    Log.e("Stopwatch", "Lap/reset button pressed while view fragment has no delegate set.");
                }
            }
        });
        return view;
    }

    public void updateTime(int millisecondsElapsed) {
        int milliseconds = millisecondsElapsed % 1000;
        millisecondsElapsed /= 1000;
        int seconds = millisecondsElapsed % 60;//TODO instead use %16000 and don't update millisecondsEllapsed.
        millisecondsElapsed /= 60;
        elapsedTime.setText(millisecondsElapsed + ":" + seconds + ":" + milliseconds);
        elapsedTime.setText(String.format("%02d:%02d:%03d", millisecondsElapsed, seconds, milliseconds));
    }
}
