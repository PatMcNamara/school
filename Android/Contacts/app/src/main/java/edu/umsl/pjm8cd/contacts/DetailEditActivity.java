package edu.umsl.pjm8cd.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Pat on 4/17/2016.
 *
 * Activity for the ContactDetailsView fragment.
 */
public class DetailEditActivity extends AppCompatActivity {
    private ContactDetailsView contactDetailsView;
    private DBWrapper database;
    private Button saveButton, cancelButton;

    public static final String UUID = "UUID";

    /* Used to bring up add a new contact view. */
    public static Intent newIntent(Context currentContext) {
        Intent i = new Intent(currentContext, DetailEditActivity.class);
        return i;
    }

    /* This will simply display the contact info and an edit button. */
    public static Intent newIntent(Context currentContext, Contact contact) {
        Intent i = DetailEditActivity.newIntent(currentContext);
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

        /* Are we adding a new contact or bring up an edit contact screen? */
        if(getIntent().getStringExtra(UUID) == null) {
            enableEditing();
        } else {
            disableEditing();
        }

        FragmentManager manager = getSupportFragmentManager();
        contactDetailsView = (ContactDetailsView) manager.findFragmentById(R.id.contacts_listing_layout);

        if (contactDetailsView == null) {
            contactDetailsView = new ContactDetailsView();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, contactDetailsView)
                    .commit();
        }
    }

    /* Will allow the user to change the fields in the given contact */
    private void enableEditing() {
        saveButton.setText("Save");
        cancelButton.setText("Cancel");
        cancelButton.setVisibility(Button.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add contact with the given info into the database.
                Contact c = contactDetailsView.buildContact();
                if (c.hasNoName()) {
                    Toast.makeText(getApplicationContext(), "Contact must have a name.", Toast.LENGTH_SHORT).show();
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

    /* Use this if you are just reading the contact details. */
    private void disableEditing() {
        saveButton.setText("Edit");
        cancelButton.setVisibility(Button.GONE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDetailsView.setEditable(true);
                enableEditing();
            }
        });
    }

    public void sendMessage(View v){
        Contact c = contactDetailsView.buildContact();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, c.getEmail());
        i.setType("*/*");
        i = Intent.createChooser(i, "Choose messaging application");

        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        }
    }
}
