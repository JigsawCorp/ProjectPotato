package com.jigsawcorp.android.jigsaw.Model;

import java.util.Date;
import java.util.List;

public class PerformedExercise {
    private Exercise mExercise;
    private Date mDate;
    private List<Set> mSets;
    private String mNotes;

    public PerformedExercise(Exercise exercise, Date date) {
        mExercise = exercise;
        mDate = date;
    }

    public Exercise getExercise() {
        return mExercise;
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public List<Set> getSets() {
        return mSets;
    }

    public void setSets(List<Set> sets) {
        mSets = sets;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }
}
