package com.jigsawcorp.android.jigsaw.Model;

import java.util.ArrayList;
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
        mNotes = "";
        mSets = new ArrayList<>();
    }

    public PerformedExercise(Exercise exercise, Date date, String notes) {
        mExercise = exercise;
        mDate = date;
        mNotes = notes;
        mSets = new ArrayList<>();
    }

    public PerformedExercise(Exercise exercise, Date date, List<Set> sets) {
        mExercise = exercise;
        mDate = date;
        mNotes = "";
        mSets = sets;
    }

    public PerformedExercise(Exercise exercise, Date date, List<Set> sets, String notes) {
        mExercise = exercise;
        mDate = date;
        mNotes = notes;
        mSets = sets;
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
