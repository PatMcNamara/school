package edu.umsl.pjm8cd.alarm.timer;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import edu.umsl.pjm8cd.alarm.R;
import edu.umsl.pjm8cd.alarm.alarm.ListViewFragment;

/**
 * Created by pat on 3/4/2016.
 */
public class TimerViewFragment extends Fragment
        implements Timer.TimerDelegate {
    interface StopwatchFragmentDelegate {
        void startStop(Date time);
        void lapReset(Date time);
    }


    //TODO when the timer reaches zero it should do something.
    Timer model;

    private TimePicker picker;

    private View pickerLayout;
    private View bodyLayout;

//    private StopwatchFragmentDelegate delegate;
//    private boolean isRunning = false;
//
    private TextView timer;
//    private TextView lapTime;
//    private Button startStopButton;
//    private Button lapResetButton;
//
//    public void setDelegate(StopwatchFragmentDelegate delegate) {
//        this.delegate = delegate;
//    }

    public static TimerViewFragment newInstance() {
        TimerViewFragment fragment = new TimerViewFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/*        setRetainInstance(true);//TODO*/
        View view = inflater.inflate(R.layout.timer_fragment_view, container, false);

        model = new Timer(this);

        picker = (TimePicker) view.findViewById(R.id.picker);
        pickerLayout = (View) view.findViewById(R.id.picker_body);
        bodyLayout = (View) view.findViewById(R.id.timer_body);
        timer = (TextView) view.findViewById(R.id.timer);

        Button cancelButton = (Button) view.findViewById(R.id.reset_button);
        final Button pauseResumeButton = (Button) view.findViewById(R.id.pause_button);
        Button pickButton = (Button) view.findViewById(R.id.picker_button);
        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, min;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = picker.getHour();
                    min = picker.getMinute();
                } else { // If version is before getHour() was implemented
                    Log.e("TIMEPICKER", "We don't actually support that version yet.");
                    return;
                }

                Calendar currentTime = Calendar.getInstance();
                Calendar targetTime = Calendar.getInstance();
//                Log.d("fragment", "current Time = " + c.getTime().toString());
                targetTime.set(Calendar.HOUR_OF_DAY, hour);
                targetTime.set(Calendar.MINUTE, min);
                targetTime.set(Calendar.SECOND, 0);

                if(currentTime.after(targetTime)) { // If timer is set for tomorrow.
                    targetTime.add(Calendar.DATE, 1);
                }

//                Log.d("fragment", "finish Time = " + c.getTime().toString());

                model.setTimeFinished(targetTime.getTime());// Set time in the model

                pickerLayout.setVisibility(View.GONE);//TODO this is going to make it hard to save state.
                bodyLayout.setVisibility(View.VISIBLE);

                updateTime();
                model.threadStart();
            }
        });

        pauseResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.isPaused()) {
                    model.threadStart();
                    pauseResumeButton.setText("Pause");
                } else {
                    model.threadPause();
                    pauseResumeButton.setText("Resume");
                }
                updateTime();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.threadStop();
                //TODO stop everything
                pickerLayout.setVisibility(View.VISIBLE);
                bodyLayout.setVisibility(View.GONE);
            }
        });

//
//        elapsedTime = (TextView) view.findViewById(R.id.total_timer);
//        lapTime = (TextView) view.findViewById(R.id.lap_timer);
//        startStopButton = (Button) view.findViewById(R.id.start_button);
//        lapResetButton = (Button) view.findViewById(R.id.reset_button);
//
//        startStopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Call start/stop with the current date
//                if (delegate != null) {
//                    delegate.startStop(new Date());
//
//                    // Switch the button text.
//                    if (isRunning) {
//                        isRunning = false;
//                    } else {
//                        isRunning = true;
//                    }
//                    updateButtonText();
//
//                } else {
//                    Log.e("Stopwatch", "Stop/start button pressed while view fragment has no delegate set.");
//                }
//            }
//        });
//        lapResetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Call reset with the current date
//                if (delegate != null) {
//                    delegate.lapReset(new Date());
//                } else {
//                    Log.e("Stopwatch", "Lap/reset button pressed while view fragment has no delegate set.");
//                }
//            }
//        });
        return view;
    }

    public void updateTime() {
        timer.setText(model.getTimeString());
    }

//    public void showTimePickerDialog(/*View v*/) {
//        DialogFragment picker = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                        alarmTime.setText(hourOfDay + ":" + minute);
//                return;
//            }
//        });
//        picker.show(getActivity().getSupportFragmentManager(), "timePicker");
//    }

    @Override
    public void onStart() {
        super.onStart();
//        updateButtonText();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /* Will set the text of the buttons based on if stopwatch is running or not. */
    /*public void updateButtonText() {
        if (isRunning) {
            startStopButton.setText(R.string.stop);
            lapResetButton.setText(R.string.lap);
        } else {
            startStopButton.setText(R.string.start);
            lapResetButton.setText(R.string.reset);
        }
    }*/

    /* Sets total and lap time to the specified values */
    /*public void updateTime(int totalMillisecondsElapsed, int lapMillisecondsElapsed) {
        elapsedTime.setText(formatTimeString(totalMillisecondsElapsed));
        lapTime.setText(formatTimeString(lapMillisecondsElapsed));
    }*/


}
