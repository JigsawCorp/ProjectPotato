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
    private ExerciseLab mExerciseLab;
    private WorkoutLab mWorkoutLab;
    private UUID mActiveWorkout;

    public User(Context context, Date dateOfBirth, UUID activeWorkout) {
        mDateOfBirth = dateOfBirth;
        mWorkoutLab = WorkoutLab.get(context);
        mExerciseLab = ExerciseLab.get(context);
        mActiveWorkout = activeWorkout;
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
}
