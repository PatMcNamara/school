package com.umsl.simon;

import android.animation.Animator;
import android.animation.AnimatorInflater;

import java.util.ArrayList;

/**
 * Created by Pat on 2/23/2016.
 */
public class MainModel {
    private ArrayList<Integer> sequence = new ArrayList<Integer>();
 //   int position = 0;
    private ArrayList<Animator> animator = new ArrayList<Animator>();//This might not go in the model

    public MainModel() {
        for(int i = 0; i < 4; i++) {
            Animator a = AnimatorInflater.loadAnimator(this, );
        }
    }

    public void addToSequence(int id) {
        sequence.add(id);
    }

    public int get(int position) {
        return sequence.get(position);
    }
/*    public String getNext() {
        if(position == sequence.size()) {
            return null;
        }
        return sequence.get(position++);
    }

    public void resetPosition() {
        position = 0;
    }*/
}
