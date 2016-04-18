package edu.umsl.pjm8cd.contacts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Pat on 4/17/2016.
 */
public class EditContactActivity extends AppCompatActivity {
    EditFragment editFragment;

    public static Intent newIntent(Context currentContext) {
        Intent i = new Intent(currentContext, EditContactActivity.class);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_layout);

        FragmentManager manager = getSupportFragmentManager();
        editFragment = (EditFragment) manager.findFragmentById(R.id.contacts_listing_layout);

        if (editFragment == null) {
            editFragment = new EditFragment();
            manager.beginTransaction()
                    .add(R.id.contacts_listing_layout, editFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
