package com.jigsawcorp.android.jigsaw.Model;

public class ProgramSet {
    public enum SetCategory {NORMAL, DROPSET, NEGATIVES}
    private int mMinReps, mMaxReps;
    private PerformedSet.SetCategory mCategory;
    private boolean mIsAmrap, mIsAmrapWithMin;

    public ProgramSet(int minReps, int maxReps, boolean isAmrap, boolean isAmrapWithMin) {
        mMinReps = minReps;
        mMaxReps = maxReps;
        mCategory = PerformedSet.SetCategory.NORMAL;
        mIsAmrap = isAmrap;
        mIsAmrapWithMin = isAmrapWithMin;
    }

    public ProgramSet(int minReps, int maxReps, boolean isAmrap, boolean isAmrapWithMin, PerformedSet.SetCategory category) {
        mMinReps = minReps;
        maxReps = maxReps;
        mCategory = category;
        mIsAmrap = isAmrap;
        mIsAmrapWithMin = isAmrapWithMin;
    }

    public ProgramSet(ProgramSet programSet) {
        mMinReps = programSet.getMinReps();
        mMaxReps = programSet.getMaxReps();
        mCategory = programSet.getCategory();
        mIsAmrap = programSet.isAmrap();
        mIsAmrapWithMin = programSet.isAmrapWithMin();
    }

    public int getMinReps() {
        return  mMinReps;
    }

    public void setMinReps(int minReps) {
        mMinReps = minReps;
    }

    public int getMaxReps() {
        return mMaxReps;
    }

    public void setMaxReps(int maxReps) {
        mMaxReps = maxReps;
    }

    public void setCategory(PerformedSet.SetCategory category) {
        mCategory = category;
    }

    public PerformedSet.SetCategory getCategory() {
        return mCategory;
    }

    public boolean isAmrap() {
        return mIsAmrap;
    }

    public void setIsAmrap(boolean isAmrap) {
        mIsAmrap = isAmrap;
    }

    public boolean isAmrapWithMin() {
        return mIsAmrapWithMin;
    }

    public void setIsAmrapWithMin(boolean amrapWithMin) {
        mIsAmrapWithMin = amrapWithMin;
    }

    public void updateSet(ProgramSet programSet) {
        mMinReps = programSet.getMinReps();
        mMaxReps = programSet.getMaxReps();
        mCategory = programSet.getCategory();
        mIsAmrap = programSet.isAmrap();
        mIsAmrapWithMin = programSet.isAmrapWithMin();
    }
}
