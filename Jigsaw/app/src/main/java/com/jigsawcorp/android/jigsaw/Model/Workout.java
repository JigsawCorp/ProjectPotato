package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

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
    private PerformedExerciseLab mPerformedExerciseLab;
    private String mNotes;

    // Constructor when user creates new workout session
    public Workout(Date startDate) {
        mStartDate = startDate;
        mEndDate = null;
        mId = UUID.randomUUID();
        mPerformedExercises = new ArrayList<>();
        mNotes = "";
    }

    // Constructor from the database
    public Workout(Context context, UUID id, Date startDate, Date endDate, List<UUID> performedExercises, String notes) {
        mId = id;
        mStartDate = startDate;
        mEndDate = endDate;
        mPerformedExercises = performedExercises;
        mPerformedExerciseLab = PerformedExerciseLab.get(context);
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

    public List<UUID> getPerformedExercises() {
        return mPerformedExercises;
    }

    public void setPerformedExercises(List<UUID> performedExercises) {
        mPerformedExercises = performedExercises;
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
