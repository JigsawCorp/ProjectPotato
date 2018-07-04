package com.jigsawcorp.android.jigsaw.Database.Exercise;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExerciseCursorWrapper extends CursorWrapper {
    private Context mContext;
    public ExerciseCursorWrapper(Cursor cursor ,Context context) {
        super(cursor);
        mContext = context;
    }

    public Exercise getExercise() {
        //List<PerformedExercise> exercises = new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.PERFORMED_EXERCISES)), new TypeToken<ArrayList<PerformedExercise>>(){}.getType());
        Exercise exercise = new Exercise(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.UUID))),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.NAME)),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.CATEGORY)),
                (List<UUID>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.PERFORMED_EXERCISES)), new TypeToken<ArrayList<UUID>>(){}.getType()));
        return exercise;
    }

}
