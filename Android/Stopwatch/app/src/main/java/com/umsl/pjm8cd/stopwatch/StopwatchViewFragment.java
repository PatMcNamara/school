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
    boolean isRunning = false;

    TextView elapsedTime;
    TextView lapTime;
    Button startStopButton;
    Button lapResetButton;

    public void setDelegate(StopwatchFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);//TODO
        View view = inflater.inflate(R.layout.main_fragment_view, container, false);

//        isRunning = false;
        elapsedTime = (TextView) view.findViewById(R.id.total_timer);
        lapTime = (TextView) view.findViewById(R.id.lap_timer);

        startStopButton = (Button) view.findViewById(R.id.start_button);
        lapResetButton = (Button) view.findViewById(R.id.reset_button);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call start/stop with the current date
                if (delegate != null) {
                    delegate.startStop(new Date());

                    if (isRunning) {
                        isRunning = false;
                    } else {
                        isRunning = true;
                    }
                    updateButtonText();
                } else {
                    Log.e("Stopwatch", "Stop/start button pressed while view fragment has no delegate set.");
                }
            }
        });
        lapResetButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onStart() {
        super.onStart();
        updateButtonText();
    }

    /* Will set the text of the buttons based on if stopwatch is running or not. */
    public void updateButtonText() {
        if (isRunning) {
            startStopButton.setText("Stop");
            lapResetButton.setText("Lap");
        } else {
            startStopButton.setText("Start"); //TODO this should use a R.string
            lapResetButton.setText("Reset");
        }
    }

    public void updateTime(int totalMillisecondsElapsed, int lapMillisecondsElapsed) {
        elapsedTime.setText(formatTimeString(totalMillisecondsElapsed));
        lapTime.setText(formatTimeString(lapMillisecondsElapsed));
    }

    /* Takes in a number of milliseconds and gives a time string of min:sec:milliseconds. */
    public static String formatTimeString(long milliseconds) {
        return String.format("%02d:%02d:%03d", (milliseconds / 60000), (milliseconds / 1000) % 60, milliseconds % 1000);
    }
}
