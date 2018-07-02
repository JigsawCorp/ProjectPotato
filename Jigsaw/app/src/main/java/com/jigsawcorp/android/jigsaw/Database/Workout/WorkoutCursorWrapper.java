package com.jigsawcorp.android.jigsaw.Database.Workout;

import android.content.Context;
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
    private Context mContext;
    public WorkoutCursorWrapper(Cursor cursor, Context context) {
            super(cursor);
            mContext = context;
        }
        public Workout getWorkout() {
            return new Workout(
                    mContext,
                    UUID.fromString(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.UUID))),
                    new Date(getLong(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.START_DATE))),
                    new Date(getLong(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.END_DATE))),
                    (List<UUID>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.PERFORMED_EXERCISES)), new TypeToken<ArrayList<UUID>>(){}.getType()),
                    getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.NOTES)));
        }

    }

