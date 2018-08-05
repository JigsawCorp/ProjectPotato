package com.jigsawcorp.android.jigsaw.Model;

public class Set {
    public enum SetCategory {NORMAL, DROPSET, NEGATIVES}
    private int mReps;
    private double mWeight;
    private SetCategory mCategory;
    private boolean mIsPerformed;

    public Set(double weight, int reps) {
        mWeight = weight;
        mReps = reps;
        mCategory = SetCategory.NORMAL;
        mIsPerformed = false;
    }

    public Set(double weight, int reps, SetCategory category) {
        mWeight = weight;
        mReps = reps;
        mCategory = category;
        mIsPerformed = false;
    }

    public Set(Set set) {
        mWeight = set.getWeight();
        mReps = set.getReps();
        mCategory = set.getCategory();
        mIsPerformed = set.isPerformed();
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

    public void setCategory(SetCategory category) {
        mCategory = category;
    }

    public SetCategory getCategory() {
        return mCategory;
    }

    public boolean isPerformed() {
        return mIsPerformed;
    }

    public void setIsPerformed(boolean isPerformed) {
        mIsPerformed = isPerformed;
    }
}
