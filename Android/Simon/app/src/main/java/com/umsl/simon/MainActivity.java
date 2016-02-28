package com.umsl.simon;

//TODO you need to have more contrast between the yellows

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

    private MainModel sequence;// = new MainModel();
    private Button ulButton, urButton, blButton, brButton;
    private ArrayList<Animator> animator;// = new ArrayList<Animator>();
    private int position = 0; //TODO this should probably go in the model
    private SharedPreferences highScorePrefs;
    public static final String MODEL_KEY = "com.umsl.simon.model";
    public static final String HIGH_SCORE_PREFS = "HighScore";

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ulButton = (Button) findViewById(R.id.top_left_button);//TODO this should just be an array list of buttons
        urButton = (Button) findViewById(R.id.top_right_button);
        blButton = (Button) findViewById(R.id.bottom_left_button);
        brButton = (Button) findViewById(R.id.bottom_right_button);

        //if((sequence = (MainModel) savedInstanceState.get(MODEL_KEY)) == null) {
            sequence = new MainModel();
        //}
        animator = new ArrayList<Animator>(); //TODO this should be a map between R.id.* and the animator.
        for(int i = 0; i < 4; i++) {
            Animator a;

            if (i == 0) {
                a = AnimatorInflater.loadAnimator(this, R.animator.green);
                a.setTarget(ulButton);
            } else if (i == 1) {
                a = AnimatorInflater.loadAnimator(this, R.animator.red);
                a.setTarget(urButton);
            } else if (i == 2) {
                a = AnimatorInflater.loadAnimator(this, R.animator.blue);
                a.setTarget(blButton);
            } else {
                a = AnimatorInflater.loadAnimator(this, R.animator.yellow);
                a.setTarget(brButton);
            }
            animator.add(a);
        }
        sequence.addRandomElementToSequence();//TODO These 2 lines should be joined with the lines in the if in buttonClicked
        handler.postDelayed(displayRunnable, 1000);

        highScorePrefs = getSharedPreferences(HIGH_SCORE_PREFS, 0);
        if( !highScorePrefs.contains("HighScore") ) {
            highScorePrefs.edit().putInt("HighScore", 0).apply();
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

    //TODO add start display sequence that will both post delay and disable the buttons

    public void buttonClicked(int clicked) {
        animator.get(clicked).start();
        if(sequence.get(position) == clicked) {
            if(++position == sequence.getSize()) { // The user got the entire sequence right
                sequence.addRandomElementToSequence();
                position = 0;
                handler.postDelayed(displayRunnable, 1000);
            }
        } else {
            int currentScore = sequence.getSize() - 1;
            int highScore = highScorePrefs.getInt("HighScore", 0);

            //TODO set up high score shit.
            //TODO this should be constructed in onStart so we can pull it up at the begining. Or just put it in its own method.
            View inflatedView = getLayoutInflater().inflate(R.layout.activity_pop_up, null, false);
            final PopupWindow popup = new PopupWindow(inflatedView, ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            popup.showAtLocation(blButton.getRootView(), Gravity.CENTER, 0, 0);
            if(highScore < currentScore) {//TODO need a high score indicator, add it to the initial screen
                highScorePrefs.edit().putInt("HighScore", currentScore).apply();
            }

            ((TextView) inflatedView.findViewById(R.id.current_score_text)).setText("Current Score: " + currentScore);
            ((TextView) inflatedView.findViewById(R.id.high_score_text)).setText("High Score: " + highScore);
            ((Button) inflatedView.findViewById(R.id.start_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sequence.clear();
                    position = 0;
                    sequence.addRandomElementToSequence();
                    handler.postDelayed(displayRunnable, 1000);
                    popup.dismiss();
                }
            });
//            /*TextView currentScore =*/ ((TextView) findViewById(R.id.current_score_text)).setText(R.string.current_score /* + sequence.getSize()*/);
        }
    }

    private Runnable displayRunnable = new Runnable() {
        int animatePosition = 0;
        @Override
        public void run() {
            if(animatePosition < sequence.getSize() ) {
                animator.get(sequence.get(animatePosition++)).start();
                handler.postDelayed(displayRunnable, 1000); //TODO both of these 2 animators should be time controlled to be longer then get shorter
            } else { // we are at the end of the sequence
                animatePosition = 0;
            }
        }
    };
}
