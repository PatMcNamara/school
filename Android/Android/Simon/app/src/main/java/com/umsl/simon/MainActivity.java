package com.umsl.simon;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private SharedPreferences highScorePrefs;
    private PopupWindow popup;
    private View inflatedView; // This is the view in the popup window.

    private int position = 0; // Where the user is in replicating the button sequence
    private int animationSpeed;
    private boolean gameRunning;
    private String title = null;
    private int currentScore = 0; // This isn't updated in real time but only when a game finishes.

    // The higher this is the slower the game speeds up and the easier it is.
    private final float animationSpeedChange = (float) .8;

    public static final String MODEL_KEY = "edu.umsl.simon.model";
    public static final String HIGH_SCORE_PREFS = "edu.umsl.simon.HighScore";
    public static final String HIGH_SCORE_KEY = "HighScore";
    public static final String IS_SEQUENCE_RUNNING = "edu.umsl.simon.running";
    public static final String SPEED_KEY = "edu.umsl.simon.speed";
    public static final String POPUP_TITLE_KEY = "edu.umsl.simon.title";
    public static final String POPUP_SCORE_KEY = "edu.umsl.simon.score";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Button array
        buttonList = new ArrayList<Button>();
        buttonList.add((Button) findViewById(R.id.top_left_button));
        buttonList.add((Button) findViewById(R.id.top_right_button));
        buttonList.add((Button) findViewById(R.id.bottom_left_button));
        buttonList.add((Button) findViewById(R.id.bottom_right_button));

        // Set up animator array. animator[i] is the animator for the button at buttonList[i]
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

        sequence = new MainModel();
        if(savedInstanceState == null ){
            animationSpeed = 600;
            gameRunning = false;
            title = getString(R.string.welcome_title);
        } else if(savedInstanceState.getBoolean(IS_SEQUENCE_RUNNING)) {
            // If the saved instance is running, we only need to set up the sequence and animation speed.
            gameRunning = true;
            sequence.setSequence(savedInstanceState.getIntArray(MODEL_KEY));
            currentScore = sequence.getSize();
            animationSpeed = savedInstanceState.getInt(SPEED_KEY, 600);
        } else {
            // We are in a menu, we only need to set up the title and the sequence.
            gameRunning = false;
            title = savedInstanceState.getString(POPUP_TITLE_KEY);
            currentScore = savedInstanceState.getInt(POPUP_SCORE_KEY);
        }

        // Set up animator and pop up window. Actual values for the text field will be filled in in displayPopUp.
        inflatedView = getLayoutInflater().inflate(R.layout.activity_pop_up, (ViewGroup)buttonList.get(0).getParent(), false);
        popup = new PopupWindow(inflatedView, ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);

        // Get the high score from the preferences.
        highScorePrefs = getSharedPreferences(HIGH_SCORE_PREFS, 0);
        if( !highScorePrefs.contains(HIGH_SCORE_KEY) ) {
            highScorePrefs.edit().putInt(HIGH_SCORE_KEY, 0).apply();
        }

        if(gameRunning) { // If the game is running we only need to continue the animation.
            startAnimationSequence();
        } else {
            // We can't call displayPopUp until the entire view is set up so we delay the call.
            handler.postDelayed(displayStartScreen, 1000);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(gameRunning) {
            outState.putBoolean(IS_SEQUENCE_RUNNING, true);
            outState.putIntArray(MODEL_KEY, sequence.getSequence());
            outState.putInt(SPEED_KEY, animationSpeed);
        } else {
            outState.putBoolean(IS_SEQUENCE_RUNNING, false);
            outState.putString(POPUP_TITLE_KEY, title);

            // If a game hasn't yet been played, you don't need to save the score.
            if(currentScore != 0) {
                outState.putInt(POPUP_SCORE_KEY, currentScore);
            }
        }

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
        // Show that the user clicked the button
        animator.get(clicked).start();

        if(sequence.get(position) == clicked) {
            if(++position == sequence.getSize()) { // The user got the entire sequence right
                sequence.addRandomElementToSequence();
                position = 0;
                currentScore = sequence.getSize();
                animationSpeed *= animationSpeedChange;
                startAnimationSequence();
            }
            // Nothing has to happen if the user was correct but haven't finished the sequence
        } else { // The user was wrong
            title = getString(R.string.game_over_title);
            displayPopUp(true);
        }
    }

    // Displays the popUp menu.
    private void displayPopUp(boolean displayCurrentScore) {
        gameRunning = false;
        int highScore = highScorePrefs.getInt(HIGH_SCORE_KEY, 0);

        disableColorButtons();
        popup.showAtLocation(buttonList.get(0).getRootView(), Gravity.CENTER, 0, 0);

        // Has the user set a new high score?
        if(highScore < currentScore) {
            highScorePrefs.edit().putInt(HIGH_SCORE_KEY, currentScore).apply();
            highScore = currentScore;
            title = getString(R.string.high_score_title);
        }

        // Set text of the fields
        ((TextView) inflatedView.findViewById(R.id.popup_title)).setText(title);
        ((TextView) inflatedView.findViewById(R.id.high_score_text)).setText(getString(R.string.high_score_text) + highScore);
        TextView currentScoreView = (TextView) inflatedView.findViewById(R.id.current_score_text);
        if(displayCurrentScore) {
            currentScoreView.setText(getString(R.string.current_score_text) + currentScore);
            currentScoreView.setVisibility(TextView.VISIBLE);
        } else {
            currentScoreView.setVisibility(TextView.INVISIBLE);
        }

        ((Button) inflatedView.findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new session.
                gameRunning = true;
                enableColorButtons();
                sequence.clear();
                position = 0;
                animationSpeed = 600;
                sequence.addRandomElementToSequence();
                popup.dismiss();
                startAnimationSequence();
            }
        });
    }

    // Will disable the 4 color buttons.
    private void disableColorButtons() {
        for(Button b : buttonList) {
            b.setEnabled(false);
        }
    }
    // Enables the 4 colored buttons.
    private void enableColorButtons() {
        for(Button b : buttonList) {
            b.setEnabled(true);
        }
    }

    // Starts the animation.
    private void startAnimationSequence() {
        disableColorButtons();
        handler.postDelayed(displayRunnable, animationSpeed);
    }

    // This will display the given Animator for the given duration without actually changing the animation duration.
    private void setTempAnimateDuration(Animator animator, int duration) {
        long oldDuration = animator.getDuration();
        animator.setDuration(duration).start();
        animator.setDuration(oldDuration);
    }

    // Recursive call for animating. Don't call this directly, instead call startAnimationSequence().
    private Runnable displayRunnable = new Runnable() {
        int animatePosition = 0;
        @Override
        public void run() {
            if(animatePosition < sequence.getSize() ) { // Continue animation.
                setTempAnimateDuration(animator.get(sequence.get(animatePosition++)), animationSpeed);
                handler.postDelayed(displayRunnable, animationSpeed * 2);
            } else { // we are at the end of the sequence
                animatePosition = 0;
                enableColorButtons();
            }
        }
    };

    // This is only called from onCreate.
    private Runnable displayStartScreen = new Runnable() {
        @Override
        public void run() {
            if(title == null) { //This shouldn't ever happen
                Log.d("Simon", "Called displayStartScreen Runnable while title was null.");
                displayPopUp(false);
            } else if(title.equals(getString(R.string.welcome_title))) {// In this case there won't be any saved score.
                displayPopUp(false);
            }else {
                displayPopUp(true);
            }
        }
    };
}
