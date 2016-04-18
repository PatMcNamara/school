package edu.umsl.pjm8cd.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pat on 4/17/2016.
 */
public class ViewFragment extends Fragment {
    RecyclerView contactRecyclerView;
    List<Contact> contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_layout, container, false);
        contactRecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contacts = DBWrapper.get(getContext()).getAllContacts();
        contactRecyclerView.setAdapter(new ContactsAdapter());//TODO you must update the adapter when you update something
        return view;
    }

    public void updateView() {
        contacts = DBWrapper.get(getContext()).getAllContacts();
        contactRecyclerView.setAdapter(new ContactsAdapter());
    }

    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameView;
        private TextView emailView;
        //TODO photo, send message.
        private Contact contact;

        public ContactHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            nameView = (TextView) itemView.findViewById(R.id.NameText);
            emailView = (TextView) itemView.findViewById(R.id.emailText);
        }

        public void bindContact(Contact c) {
            String name = c.getFirstName() + ", " + c.getLastName();
            nameView.setText(name);
            emailView.setText(c.getEmail());
            contact = c;
        }

        @Override
        public void onClick(View view) {
            Intent intent = EditContactActivity.newIntent(getContext(), contact);
            Log.d("", "Put value " + intent.getStringExtra(EditContactActivity.UUID) + " into intent.");
            startActivity(intent);
        }
    }

    private class ContactsAdapter extends RecyclerView.Adapter<ContactHolder> {
        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.contact_item_layout, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact c = contacts.get(position);
            holder.bindContact(c);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }
    }
}
