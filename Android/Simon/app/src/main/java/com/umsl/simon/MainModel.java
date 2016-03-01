package com.umsl.simon;

import java.util.ArrayList;

/**
 * Created by Pat on 2/23/2016.
 */
public class MainModel {
    private ArrayList<Integer> sequence = new ArrayList<Integer>();

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

    // Should only be used to get the values for putting into a bundle
    public int[] getSequence() {
        int[] ret = new int[sequence.size()];
        for(int i = 0; i < sequence.size(); i++) {
            ret[i] = sequence.get(i);
        }
        return ret;
    }

    // Should only be used for initializing a model with given values (presumably the same ones put in a bundle after calling getSequence
    public void setSequence(int[] array) {
        for(int i : array) {
            sequence.add(i);
        }
    }
}
