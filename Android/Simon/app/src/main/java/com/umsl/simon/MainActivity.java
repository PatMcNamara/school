package com.umsl.simon;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MainModel sequence;
    private ArrayList<Animator> animator;
    private ArrayList<Button> buttonList;
    private int position = 0; //TODO this should probably go in the model
    private SharedPreferences highScorePrefs;
    private int animationSpeed;
    private final float animationSpeedChange = (float) .8;
    public static final String MODEL_KEY = "com.umsl.simon.model";
    public static final String HIGH_SCORE_PREFS = "HighScore";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonList = new ArrayList<Button>();
        buttonList.add((Button) findViewById(R.id.top_left_button));
        buttonList.add((Button) findViewById(R.id.top_right_button));
        buttonList.add((Button) findViewById(R.id.bottom_left_button));
        buttonList.add((Button) findViewById(R.id.bottom_right_button));

        animationSpeed = 600;

        //if((sequence = (MainModel) savedInstanceState.get(MODEL_KEY)) == null) {
            sequence = new MainModel();
        //}
        animator = new ArrayList<Animator>();
        for(int i = 0; i < 4; i++) {
            Animator a;

            if (i == 0) {
                a = AnimatorInflater.loadAnimator(this, R.animator.green);
            } else if (i == 1) {
                a = AnimatorInflater.loadAnimator(this, R.animator.red);
            } else if (i == 2) {
                a = AnimatorInflater.loadAnimator(this, R.animator.blue);
            } else {
                a = AnimatorInflater.loadAnimator(this, R.animator.orange);
            }
            a.setTarget(buttonList.get(i));
            animator.add(a);
        }

        highScorePrefs = getSharedPreferences(HIGH_SCORE_PREFS, 0);
        if( !highScorePrefs.contains("HighScore") ) {
            highScorePrefs.edit().putInt("HighScore", 0).apply();
        }

        // We can't call displayPopUp until the entire view is set up so we delay the call.
        handler.postDelayed(displayStartScreen, 250);
    }

    public void topLeftButtonClicked(View v) {
        buttonClicked(0);
    }
    public void topRightButtonClicked(View v) {
        buttonClicked(1);
    }
    public void bottomLeftButtonClicked(View v) {
        buttonClicked(2);
    }
    public void bottomRightButtonClicked(View v) {
        buttonClicked(3);
    }

    public void buttonClicked(int clicked) {
        animator.get(clicked).start();
        if(sequence.get(position) == clicked) {
            if(++position == sequence.getSize()) { // The user got the entire sequence right
                sequence.addRandomElementToSequence();
                position = 0;
                animationSpeed *= animationSpeedChange;
                startSequenceAnimation();
            }
        } else {
            displayPopUp("Game Over", true);
        }
    }

    private void displayPopUp(String title, boolean displayCurrentScore) {
        int currentScore = sequence.getSize() - 1;
        int highScore = highScorePrefs.getInt("HighScore", 0);

        disableColorButtons();

        View inflatedView = getLayoutInflater().inflate(R.layout.activity_pop_up, null, false);
        final PopupWindow popup = new PopupWindow(inflatedView, ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        popup.showAtLocation(buttonList.get(0).getRootView(), Gravity.CENTER, 0, 0);
        if(highScore < currentScore) {
            highScorePrefs.edit().putInt("HighScore", currentScore).apply();
            highScore = currentScore;
            title = "New High Score";
        }

        ((TextView) inflatedView.findViewById(R.id.popup_title)).setText(title);
        ((TextView) inflatedView.findViewById(R.id.high_score_text)).setText("High Score: " + highScore);
        if(displayCurrentScore) {
            ((TextView) inflatedView.findViewById(R.id.current_score_text)).setText("Current Score: " + currentScore);
        }

        ((Button) inflatedView.findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableColorButtons();
                sequence.clear();
                position = 0;
                animationSpeed = 600;
                sequence.addRandomElementToSequence();
                popup.dismiss();
                startSequenceAnimation();
            }
        });
    }

    private void disableColorButtons() {
        for(Button b : buttonList) {
            b.setEnabled(false);
        }
    }
    private void enableColorButtons() {
        for(Button b : buttonList) {
            b.setEnabled(true);
        }
    }

    private void startSequenceAnimation() {
        disableColorButtons();
        handler.postDelayed(displayRunnable, animationSpeed);
    }

    private void setTempAnimateDuration(Animator animator, int duration) {
        long oldDuration = animator.getDuration();
        animator.setDuration(duration).start();
        animator.setDuration(oldDuration);
    }

    private Runnable displayRunnable = new Runnable() {
        int animatePosition = 0;
        @Override
        public void run() {
            if(animatePosition < sequence.getSize() ) {
                //animator.get(sequence.get(animatePosition++)).setDuration(animationSpeed).start();
                setTempAnimateDuration(animator.get(sequence.get(animatePosition++)), animationSpeed);
                handler.postDelayed(displayRunnable, animationSpeed * 2);
            } else { // we are at the end of the sequence
                animatePosition = 0;
                enableColorButtons();
            }
        }
    };

    private Runnable displayStartScreen = new Runnable() {
        @Override
        public void run() {
            displayPopUp("Welcome", false);
        }
    };
}
