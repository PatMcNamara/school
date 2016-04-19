package edu.umsl.pjm8cd.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    public static final String UUID = "UUID";

    public static Intent newIntent(Context currentContext) {
        Intent i = new Intent(currentContext, EditContactActivity.class);
        return i;
    }

    public static Intent newIntent(Context currentContext, Contact contact) {
        Intent i = EditContactActivity.newIntent(currentContext);
        i.putExtra(UUID, contact.getId().toString());
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);

        database = new DBWrapper(getApplicationContext());

        saveButton = (Button) findViewById(R.id.bottomButtonLeft);
        cancelButton = (Button) findViewById(R.id.bottomButtonRight);

        if(getIntent().getStringExtra(UUID) == null) {
            enableEditing();
        } else {
            disableEditing();
        }

        FragmentManager manager = getSupportFragmentManager();
        editFragment = (EditFragment) manager.findFragmentById(R.id.contacts_listing_layout);

        if (editFragment == null) {
            editFragment = new EditFragment();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, editFragment)
                    .commit();
        }
    }

    private void enableEditing() {
        saveButton.setText("Save");
        cancelButton.setText("Cancel");
        cancelButton.setVisibility(Button.VISIBLE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact c = editFragment.getContact();
                if (c == null) {
                    Toast.makeText(getApplicationContext(), "All 3 fields must be used.", Toast.LENGTH_SHORT);
                } else {
                    database.updateOrAddContact(c);
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
    }

    private void disableEditing() {
        saveButton.setText("Edit");
        cancelButton.setVisibility(Button.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editFragment.setEditable(true);
                enableEditing();
            }
        });
    }

    public void sendMessage(View v){
        Contact c = editFragment.getContact();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, c.getEmail());
        i.setType("*/*");
        i = Intent.createChooser(i, "Choose messaging application");

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }
}
