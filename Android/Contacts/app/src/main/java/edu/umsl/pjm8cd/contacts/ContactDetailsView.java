package edu.umsl.pjm8cd.contacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by Pat on 4/17/2016.
 *
 * This fragment handles the displaying of detailed contact information and allows user to edit those
 * details.
 */
public class ContactDetailsView extends Fragment {
    private EditText firstName, lastName, email;
    private ImageView pictureView;
    private ImageButton camButton;

    // The picture will either be a valid bitmap or null if there is no picture.
    private Bitmap picture;

    private boolean inEditMode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.edit_contact, container, false);

        firstName = (EditText) view.findViewById(R.id.firstNameEdit);
        lastName = (EditText) view.findViewById(R.id.lastNameEdit);
        email = (EditText) view.findViewById(R.id.emailEdit);
        pictureView = (ImageView) view.findViewById(R.id.contactImage);
        camButton = (ImageButton) view.findViewById(R.id.cameraButton);

        String specifiedUUID = getActivity().getIntent().getStringExtra(DetailEditActivity.UUID);
        if(specifiedUUID != null) { // Is user viewing an existing contact or creating a new one?
            setEditable(false);
            Contact c = DBWrapper.get(getActivity()).getContactFromUUID(UUID.fromString(specifiedUUID));
            inEditMode = false;
            if(c.getPicture() == null) {
                Log.d("EDIT", "Database gave us a contact without a picture, using default.");
            }
            fillFields(c);
        } else {
            inEditMode = true;
            setEditable(true);
        }

        updatePictureView();
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(captureImage, 0);
            }
        });

        return view;
    }

    /* Set to the given photo or to a default image. */
    private void updatePictureView() {
        if(picture == null) {
            pictureView.setBackgroundResource(R.drawable.ben);
        } else {
            pictureView.setBackgroundResource(android.R.color.white);
            pictureView.setImageBitmap(picture);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) { // Returned from the camera.
            picture = (Bitmap) data.getExtras().get("data");
            updatePictureView();
        }
    }

    /* Fills the text and photo areas in the view with the given contact info. */
    private void fillFields(Contact c) {
        firstName.setText(c.getFirstName());
        lastName.setText(c.getLastName());
        email.setText(c.getEmail());
        picture = c.getPicture();
        updatePictureView();
    }

    /* Allows the user to edit the data. */
    public void setEditable(boolean editable) {
        if(editable) {
            firstName.setInputType(InputType.TYPE_CLASS_TEXT);
            lastName.setInputType(InputType.TYPE_CLASS_TEXT);
            email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            camButton.setVisibility(View.VISIBLE);
        } else {
            firstName.setInputType(InputType.TYPE_NULL);
            lastName.setInputType(InputType.TYPE_NULL);
            email.setInputType(InputType.TYPE_NULL);
            camButton.setVisibility(View.GONE);
        }
        inEditMode = editable;
    }

    /* Takes the view fields and builds a Contact. */
    public Contact buildContact() {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String addr = email.getText().toString();

        Contact newContact;
        // If we are updating an existing contact, use the same UUID
        String contactUUID = getActivity().getIntent().getStringExtra(DetailEditActivity.UUID);
        if(contactUUID == null) {
            newContact = new Contact();
        } else {
            newContact = new Contact(UUID.fromString(contactUUID));
        }

        newContact.setFirstName(first);
        newContact.setLastName(last);
        newContact.setEmail(addr);
        newContact.setPicture(picture);
        return newContact;
    }

    public boolean isInEditMode() {
        return inEditMode;
    }
}
