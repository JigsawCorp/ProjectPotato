package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Workout {
    private UUID mId;
    private Date mStartDate;
    private Date mEndDate;
    private List<UUID> mPerformedExercises;
    private String mNotes;

    // Constructor when user creates new workout session
    public Workout() {
        mStartDate = new Date();
        mEndDate = new Date();
        mId = UUID.randomUUID();
        mPerformedExercises = new ArrayList<>();
        mNotes = "";
    }

    // Constructor from the database
    public Workout(UUID id, Date startDate, Date endDate, List<UUID> performedExercises, String notes) {
        mId = id;
        mStartDate = startDate;
        mEndDate = endDate;
        mPerformedExercises = performedExercises;
        mNotes = notes;
    }


    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public List<UUID> getPerformedExercisesIds() {
        return mPerformedExercises;
    }

    public List<UUID> getPerformedExercises() {
        return mPerformedExercises;
    }

    public void setPerformedExercises(List<UUID> performedExercises) {
        mPerformedExercises = performedExercises;
    }

    public void addPerformedExercises(List<PerformedExercise> performedExercises) {
        for (PerformedExercise exercise: performedExercises) {
            mPerformedExercises.add(exercise.getmId());
        }
    }

    public void removePerformedExercise(UUID performedExercise) {
        mPerformedExercises.remove(performedExercise);
    }

    public void addPerformedExercise(UUID performedExercise) {
        mPerformedExercises.add(performedExercise);
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
