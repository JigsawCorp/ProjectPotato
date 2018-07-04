package com.jigsawcorp.android.jigsaw.Database.PerformedExercise;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Set;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PerformedExerciseCursorWrapper extends CursorWrapper {
    private Context mContext;
    public PerformedExerciseCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public PerformedExercise getPerformedExercise() {
        Date endDate;
        if (getLong(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.END_DATE)) == 0) {
            endDate = null;
        }
        else {
            endDate = new Date(getLong(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.END_DATE)));
        }
        PerformedExercise performedExercise = new PerformedExercise(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.UUID))),
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.EXERCISE))),
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.WORKOUT))),
                new Date(getLong(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.START_DATE))),
                endDate,
                (List<Set>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.SETS)), new TypeToken<ArrayList<Set>>(){}.getType()),
                getString(getColumnIndex(DatabaseSchema.PerformedExerciseTable.Cols.NOTES)));

        return performedExercise;
    }

}