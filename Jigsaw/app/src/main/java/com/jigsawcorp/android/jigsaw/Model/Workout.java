package com.jigsawcorp.android.jigsaw.Model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Workout {
    private UUID mId;
    private Date mStartDate;
    private Date mEndDate;
    private List<PerformedExercise> mPerformedExercises;
    private String mNotes;

    public Workout(Date startDate) {
        mStartDate = startDate;
        mId = UUID.randomUUID();
    }

    public Workout(UUID id, Date startDate, Date endDate, List<PerformedExercise> performedExercises, String notes) {
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

    public List<PerformedExercise> getPerformedExercises() {
        return mPerformedExercises;
    }

    public void setPerformedExercises(List<PerformedExercise> performedExercises) {
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
