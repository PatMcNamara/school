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

    public MainModel() {

    }

    public void addRandomElementToSequence() {
        addToSequence((int) (Math.random() * 4));
    }

    public void addToSequence(int id) {
        sequence.add(id);
    }

    public int getSize() { return sequence.size(); }

    public int get(int index) {
        return sequence.get(index);
    }

    public void clear() { sequence.clear(); }
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
