package edu.umsl.pjm8cd.contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pat on 4/17/2016.
 */
public class ViewFragment extends Fragment {
    RecyclerView contactRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_layout, container, false);
        contactRecyclerView = (RecyclerView) view.findViewById(R.id.contacts_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactRecyclerView.setAdapter(new LapAdapter());
        return view;
    }

    private class ContactHolder extends RecyclerView.ViewHolder {
        private TextView fName;
        private TextView lName;
        private TextView email;
        //TODO photo, send message.

        public ContactHolder(View itemView) {

            super(itemView);
            //time = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    private class LapAdapter extends RecyclerView.Adapter<ContactHolder> {
        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.contact_item_layout, parent, false);
            return new ContactHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            ;
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}
