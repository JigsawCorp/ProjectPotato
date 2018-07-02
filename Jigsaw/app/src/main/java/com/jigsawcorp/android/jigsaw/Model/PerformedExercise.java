package com.jigsawcorp.android.jigsaw.Model;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PerformedExercise {
    private UUID mId;
    private Exercise mExercise;
    private Workout mWorkout;
    private Date mStartDate;
    private Date mEndDate;
    private List<Set> mSets;
    private String mNotes;

    // Constructor when adding from a workout
    public PerformedExercise(Exercise exercise, Workout workout, Date startDate) {
        mId = UUID.randomUUID();
        mExercise = exercise;
        mWorkout = workout;
        mStartDate = startDate;
        mEndDate = null;
        mNotes = "";
        mSets = new ArrayList<>();
    }

    // Constructor from the database
    public PerformedExercise(UUID id, Exercise exercise, Workout workout, Date startDate, Date endDate, List<Set> sets, String notes) {
        mId = id;
        mExercise = exercise;
        mWorkout = workout;
        mStartDate = startDate;
        mEndDate = endDate;
        mSets = sets;
        mNotes = notes;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public Exercise getExercise() {
        return mExercise;
    }

    public void setExercise(Exercise exercise) {
        mExercise = exercise;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date date) {
        mStartDate = date;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date date) {
        mEndDate = date;
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

    public Workout getWorkout() {
        return mWorkout;
    }

    public void setWorkout(Workout Workout) {
        mWorkout = Workout;
    }
}
