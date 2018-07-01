package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class User {
    private Date mDateOfBirth;
    private ExerciseLab mExerciseLab;
    private WorkoutLab mWorkoutLab;
    private Boolean mActiveWorkout;

    public User(Context context) {
        mExerciseLab = ExerciseLab.get(context);
    }



    public List<Exercise> getExercises() {
        return mExerciseLab.getExercises();
    }
}
