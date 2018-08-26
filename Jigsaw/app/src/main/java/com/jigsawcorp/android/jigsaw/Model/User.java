package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;

public class User {

    private Date mDateOfBirth;
    private UUID mActiveWorkout, mActiveProgram;

    public User(Date dateOfBirth, UUID activeWorkout, UUID activeProgram) {
        mDateOfBirth = dateOfBirth;
        mActiveWorkout = activeWorkout;
        mActiveProgram = activeProgram;
    }

    public void addWorkout(Workout workout, boolean isCurrentWorkout) {
        if (isCurrentWorkout) {
            mActiveWorkout = workout.getId();
        }
    }

    private void updateUser() {

    }
    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(Date mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public UUID getActiveWorkout() {
        return mActiveWorkout;
    }

    public void setActiveWorkout(UUID activeWorkout) {
        mActiveWorkout = activeWorkout;
    }

    public UUID getActiveProgram() {
        return mActiveProgram;
    }

    public void setActiveProgram(UUID activeProgram) {
        mActiveProgram = activeProgram;
    }
}
