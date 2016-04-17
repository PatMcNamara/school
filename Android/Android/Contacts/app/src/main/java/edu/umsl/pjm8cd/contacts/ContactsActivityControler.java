package edu.umsl.pjm8cd.contacts;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ContactsActivityControler extends AppCompatActivity {
    ViewFragment viewFragment;
    EditFragment editFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        FragmentManager manager = getSupportFragmentManager();
        viewFragment = (ViewFragment) manager.findFragmentById(R.id.contacts_listing_layout);

        if (viewFragment == null) {
            viewFragment = new ViewFragment();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, viewFragment)
                    .commit();
        }

    }
    public void newContact(View v) {
        Intent i = EditContactActivity.newIntent(this);
        startActivity(i);
    }
}
