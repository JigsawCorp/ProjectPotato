package com.jigsawcorp.android.jigsaw.Model;

public class Set {
    private int mReps;
    private double mWeight;

    public Set(double weight, int reps) {
        mWeight = weight;
        mReps = reps;
    }

    public int getReps() {
        return mReps;
    }

    public void setReps(int reps) {
        mReps = reps;
    }

    public double getWeight() {
        return mWeight;
    }

    public void setWeight(double weight) {
        mWeight = weight;
    }
}
