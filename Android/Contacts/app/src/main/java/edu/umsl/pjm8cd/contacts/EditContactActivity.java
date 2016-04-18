package edu.umsl.pjm8cd.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Pat on 4/17/2016.
 */
public class EditContactActivity extends AppCompatActivity {
    EditFragment editFragment;
    DBWrapper database;
    Button saveButton, cancelButton;

    public static Intent newIntent(Context currentContext) {
        Intent i = new Intent(currentContext, EditContactActivity.class);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);

        database = new DBWrapper(getApplicationContext());

        saveButton = (Button) findViewById(R.id.bottomButtonLeft);
        cancelButton = (Button) findViewById(R.id.bottomButtonRight);

        saveButton.setText("Save");
        cancelButton.setText("Cancel");
        cancelButton.setVisibility(Button.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = editFragment.getContact();
                if(c == null) {
                    Toast.makeText(getApplicationContext(), "All 3 fields must be used.", Toast.LENGTH_SHORT);
                } else {
                    database.addContact(c);
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

        FragmentManager manager = getSupportFragmentManager();
        editFragment = (EditFragment) manager.findFragmentById(R.id.contacts_listing_layout);

        if (editFragment == null) {
            editFragment = new EditFragment();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, editFragment)
                    .commit();
        }

    }
}
