package com.jigsawcorp.android.jigsaw.Model;

public class Set {
    public enum SetCategory {NORMAL, DROPSET, NEGATIVES}
    private int mReps;
    private double mWeight;
    private SetCategory mCategory;

    public Set(double weight, int reps) {
        mWeight = weight;
        mReps = reps;
        mCategory = SetCategory.NORMAL;
    }

    public Set(double weight, int reps, SetCategory category) {
        mWeight = weight;
        mReps = reps;
        mCategory = category;
    }

    public Set(Set set) {
        mWeight = set.getWeight();
        mReps = set.getReps();
        mCategory = set.getCategory();
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
}
