package com.umsl.simon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MainModel sequence;// = new MainModel();
    private Button ulButton, urButton, blButton, brButton;
    public static final String MODEL_KEY = "com.umsl.simon.model";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ulButton = (Button) findViewById(R.id.top_left_button);
        urButton = (Button) findViewById(R.id.top_right_button);
        blButton = (Button) findViewById(R.id.bottom_left_button);
        brButton = (Button) findViewById(R.id.bottom_right_button);

        //if((sequence = (MainModel) savedInstanceState.get(MODEL_KEY)) == null) {
            sequence = new MainModel();
        //}
    }

    public void topLeftButtonClicked(View v) {

    }
    public void topRightButtonClicked(View v) {

    }
    public void bottomLeftButtonClicked(View v) {

    }
    public void bottomRightButtonClicked(View v) {

    }

    private Runnable displayRunnable = new Runnable() {
        int position = 0;
        @Override
        public void run() {
            //update color
            findViewById(sequence.get(position));

        }
    };
}
