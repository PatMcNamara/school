package edu.umsl.pjm8cd.alarm.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import edu.umsl.pjm8cd.alarm.R;

/**
 * Created by Pat on 5/1/2016.
 *
 * Activity for the AlarmDetailsView fragment.
 */
public class AlarmDetailActivity extends AppCompatActivity {
    private AlarmDetailsView alarmDetailsView;

    public static final String UUID = "UUID";

    /* Used to bring up add a new contact view. */
    public static Intent newIntent(Context currentContext) {
        Intent i = new Intent(currentContext, AlarmDetailActivity.class);
        return i;
    }

    /* This will simply display the timer info and an edit button. */
    public static Intent newIntent(Context currentContext, Alarm timer) {
        Intent i = AlarmDetailActivity.newIntent(currentContext);
        i.putExtra(UUID, timer.getId().toString());
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);

        FragmentManager manager = getSupportFragmentManager();
        alarmDetailsView = (AlarmDetailsView) manager.findFragmentById(R.id.fragment_detail_holder);

        if (alarmDetailsView == null) {
            alarmDetailsView = new AlarmDetailsView();
            manager.beginTransaction()
                    .add(R.id.fragment_detail_holder, alarmDetailsView)
                    .commit();
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        checkEditMode();
    }*/

    /* Make sure that our edit mode is the same as the fragments. */
    /*private void checkEditMode() {
        if(alarmDetailsView.isInEditMode()) {
            enableEditing();
        } else {
            disableEditing();
        }
    }*/

    /* Will allow the user to change the fields in the given contact */
    /*private void enableEditing() {
        saveButton.setText("Save");
        cancelButton.setText("Cancel");
        cancelButton.setVisibility(Button.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add contact with the given info into the database.
                Alarm c = alarmDetailsView.buildContact();
                if (c.hasNoName()) {
                    Toast.makeText(getApplicationContext(), "Alarm must have a name.", Toast.LENGTH_SHORT).show();
                } else {
                    database.updateOrAddAlarm(c);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }*/

    /* Use this if you are just reading the contact details. */
    /*private void disableEditing() {
        saveButton.setText("Edit");
        cancelButton.setVisibility(Button.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmDetailsView.setEditable(true);
                enableEditing();
            }
        });
    }*/
}
