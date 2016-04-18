package edu.umsl.pjm8cd.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

        return view;
    }

    public Contact getContact() {
        String first = firstName.getText().toString();//TODO this belongs in the view
        String last = lastName.getText().toString();
        String addr = email.getText().toString();
        if(first == null || last == null || addr == null) {
            return null;
        }
        Contact newContact = new Contact();
        newContact.setFirstName(first);
        newContact.setLastName(last);
        newContact.setEmail(addr);
        return newContact;
    }

    /*public void finish() {
        getActivity().finish();
    }*/
}
