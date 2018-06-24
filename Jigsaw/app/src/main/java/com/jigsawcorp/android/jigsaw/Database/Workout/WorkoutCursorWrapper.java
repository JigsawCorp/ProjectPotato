package com.jigsawcorp.android.jigsaw.Database.Workout;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Workout;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WorkoutCursorWrapper extends CursorWrapper {
    public WorkoutCursorWrapper(Cursor cursor) {
            super(cursor);
        }
        public Workout getWorkout() {
            return new Workout(
                    UUID.fromString(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.UUID))),
                    new Date(getLong(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.START_DATE))),
                    new Date(getLong(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.END_DATE))),
                    (List<PerformedExercise>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.PERFORMED_EXERCISES)), new TypeToken<ArrayList<PerformedExercise>>(){}.getType()),
                    getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.NOTES)));
        }

    }

