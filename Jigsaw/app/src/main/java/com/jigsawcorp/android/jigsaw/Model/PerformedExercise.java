package com.jigsawcorp.android.jigsaw.Model;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PerformedExercise {
    private UUID mId;
    private UUID mExercise;
    private UUID mWorkout;
    private Date mStartDate;
    private Date mEndDate;
    private List<Set> mSets;
    private String mNotes;

    // Constructor when adding from a workout
    public PerformedExercise(UUID exercise, UUID workout, Date startDate) {
        mId = UUID.randomUUID();
        mExercise = exercise;
        mWorkout = workout;
        mStartDate = startDate;
        mEndDate = null;
        mNotes = "";
        mSets = new ArrayList<>();
    }

    // Constructor from the database
    public PerformedExercise(UUID id, UUID exercise, UUID workout, Date startDate, Date endDate, List<Set> sets, String notes) {
        mId = id;
        mExercise = exercise;
        mWorkout = workout;
        mStartDate = startDate;
        mEndDate = endDate;
        mSets = sets;
        mNotes = notes;
    }

    public static List<PerformedExercise> createFromExercises(List<UUID> exercises, UUID workout, Date startDate ) {
        List<PerformedExercise> performedExercises = new ArrayList<>();
        for (UUID exercise: exercises) {
            performedExercises.add(new PerformedExercise(exercise, workout, startDate));
        }
        return performedExercises;
    }

    public static List<UUID> toUUIDs(List<PerformedExercise> exercises) {
        List<UUID> uuids = new ArrayList<>();
        for(PerformedExercise exercise: exercises) {
            uuids.add(exercise.getmId());
        }
        return uuids;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public UUID getExercise() {
        return mExercise;
    }

    public void setExercise(UUID exercise) {
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

    public void addSet(Set set) {
        mSets.add(set);
    }

    public void updateSet(Set set) {
        mSets.set(mSets.indexOf(set), set);
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public UUID getWorkout() {
        return mWorkout;
    }

    public void setWorkout(UUID Workout) {
        mWorkout = Workout;
    }
}
