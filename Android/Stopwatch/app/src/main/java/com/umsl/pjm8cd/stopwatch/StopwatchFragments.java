package com.umsl.pjm8cd.stopwatch;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pat on 3/4/2016.
 */
public class StopwatchFragments extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View window = inflater.inflate(R.layout.activity_main, container, false);
        
        return window;
    }
}
