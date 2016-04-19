package edu.umsl.pjm8cd.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by Pat on 4/17/2016.
 */
public class EditFragment extends Fragment {
    EditText firstName, lastName, email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_contact, container, false);

        firstName = (EditText) view.findViewById(R.id.firstNameEdit);
        lastName = (EditText) view.findViewById(R.id.lastNameEdit);
        email = (EditText) view.findViewById(R.id.emailEdit);

        String specifiedUUID = getActivity().getIntent().getStringExtra(EditContactActivity.UUID);
        if(specifiedUUID != null) {
            setEditable(false);
            Contact c = DBWrapper.get(getActivity()).getContactFromUUID(UUID.fromString(specifiedUUID));
            fillFields(c);
        } else {
            setEditable(true);
        }

        return view;
    }

    private void fillFields(Contact c) {
        firstName.setText(c.getFirstName());
        lastName.setText(c.getLastName());
        email.setText(c.getEmail());
    }

    public void setEditable(boolean editable) {
        if(editable) {
            firstName.setInputType(InputType.TYPE_CLASS_TEXT);
            lastName.setInputType(InputType.TYPE_CLASS_TEXT);
            email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        } else {
            firstName.setInputType(InputType.TYPE_NULL);
            lastName.setInputType(InputType.TYPE_NULL);
            email.setInputType(InputType.TYPE_NULL);
        }
    }

    public Contact getContact() {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String addr = email.getText().toString();

        // If we are updating an existing contact, use the same UUID
        String contactUUID = getActivity().getIntent().getStringExtra(EditContactActivity.UUID);
        Contact newContact;
        if(contactUUID == null) {
            newContact = new Contact();
        } else {
            newContact = new Contact(UUID.fromString(contactUUID));
        }

        newContact.setFirstName(first);
        newContact.setLastName(last);
        newContact.setEmail(addr);
        return newContact;
    }
}
