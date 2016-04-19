package edu.umsl.pjm8cd.contacts;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ContactsActivityControler extends AppCompatActivity {
    private ListViewFragment listViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);
        FragmentManager manager = getSupportFragmentManager();

        listViewFragment = (ListViewFragment) manager.findFragmentById(R.id.contacts_listing_layout);
        if (listViewFragment == null) {
            listViewFragment = new ListViewFragment();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, listViewFragment)
                    .commit();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        listViewFragment.updateView();
        checkDisplayEmpty();
    }

    /* Called from the add contact button. */
    public void newContact(View v) {
        Intent i = DetailEditActivity.newIntent(this);
        startActivity(i);
        checkDisplayEmpty();
    }

    /* If there are no contacts, this will display no contacts */
    void checkDisplayEmpty() {
        TextView notify = (TextView) findViewById(R.id.no_contacts);
        if (listViewFragment.isEmpty()) {
            notify.setVisibility(View.VISIBLE);
        } else {
            notify.setVisibility(View.GONE);
        }
    }
}
