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
    TextView lapTime; //TODO make lap timer visually different from the elapsed time.
    boolean isRunning;

    public void setDelegate(StopwatchFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_view, container, false);

        isRunning = false;
        elapsedTime = (TextView) view.findViewById(R.id.total_timer);
        lapTime = (TextView) view.findViewById(R.id.lap_timer);

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

    public void updateTime(int totalMillisecondsElapsed, int lapMillisecondsElapsed) {
//        int milliseconds = totalMillisecondsElapsed % 1000;//TODO this should be a method. Takes a long, returns a string. Can also be used from stopwatch model.
//        totalMillisecondsElapsed /= 1000;
//        int seconds = totalMillisecondsElapsed % 60;//TODO instead use %16000 and don't update millisecondsEllapsed.
//        totalMillisecondsElapsed /= 60;
//        //elapsedTime.setText(totalMillisecondsElapsed + ":" + seconds + ":" + milliseconds);
//        elapsedTime.setText(String.format("%02d:%02d:%03d", totalMillisecondsElapsed, seconds, milliseconds));
        elapsedTime.setText(formatTimeString(totalMillisecondsElapsed));

       /* milliseconds = lapMillisecondsElapsed % 1000;
        lapMillisecondsElapsed /= 1000;
        seconds = lapMillisecondsElapsed % 60;//TODO instead use %16000 and don't update millisecondsEllapsed.
        lapMillisecondsElapsed /= 60;
        lapTime.setText(String.format("%02d:%02d:%03d", lapMillisecondsElapsed, seconds, milliseconds));*/
        lapTime.setText(formatTimeString(lapMillisecondsElapsed));
    }

    /* Takes in a number of milliseconds and gives a time string of min:sec:milliseconds. */
    public static String formatTimeString(long milliseconds) {
        return String.format("%02d:%02d:%03d", (milliseconds / 60000), (milliseconds / 1000) % 60, milliseconds % 1000);
    }
}
