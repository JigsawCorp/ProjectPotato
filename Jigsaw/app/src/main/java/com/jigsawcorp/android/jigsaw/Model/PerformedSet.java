package com.jigsawcorp.android.jigsaw.Model;

public class PerformedSet {
    public enum SetCategory {NORMAL, DROPSET, NEGATIVES}
    private int mReps;
    private double mWeight;
    private SetCategory mCategory;
    private boolean mIsPerformed;

    public PerformedSet(double weight, int reps) {
        mWeight = weight;
        mReps = reps;
        mCategory = SetCategory.NORMAL;
        mIsPerformed = false;
    }

    public PerformedSet(double weight, int reps, SetCategory category) {
        mWeight = weight;
        mReps = reps;
        mCategory = category;
        mIsPerformed = false;
    }

    public PerformedSet(PerformedSet performedSet) {
        mWeight = performedSet.getWeight();
        mReps = performedSet.getReps();
        mCategory = performedSet.getCategory();
        mIsPerformed = performedSet.isPerformed();
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
