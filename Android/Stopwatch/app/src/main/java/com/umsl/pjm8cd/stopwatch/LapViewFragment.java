package com.umsl.pjm8cd.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tmp on 3/22/2016.
 */
public class LapViewFragment extends Fragment {
    private LapViewDelegate delegate;
    interface LapViewDelegate {
        List<String> getLapTimes();
    }

    RecyclerView lapRecyclerView;

    public void setDelegate(LapViewDelegate delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lap_view_layout, container, false);
        lapRecyclerView = (RecyclerView) view.findViewById(R.id.lap_recycler_view);
        lapRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lapRecyclerView.setAdapter(new LapAdapter()); //TODO what should go in a blank adapter?
        return view;
    }

    public void update() {
        lapRecyclerView.setAdapter(new LapAdapter());
    }

    public void expandView(View lapView) {
        lapView.setVisibility(View.VISIBLE);
    }

    private class LapHolder extends RecyclerView.ViewHolder {
        TextView time;

        public LapHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

    private class LapAdapter extends RecyclerView.Adapter<LapHolder> {
        @Override
        public LapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.lap_item_layout, parent, false);
            return new LapHolder(view);
        }

        @Override
        public void onBindViewHolder(LapHolder holder, int position) {
            holder.time.setText(delegate.getLapTimes().get(position));
        }

        @Override
        public int getItemCount() {
            return delegate.getLapTimes().size();
        }
    }
}
