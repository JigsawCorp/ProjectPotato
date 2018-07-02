package com.jigsawcorp.android.jigsaw.Database.Exercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ExerciseLab {
    private static ExerciseLab sExerciseLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    private ExerciseLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static ExerciseLab get(Context context) {
        if (sExerciseLab == null) {
            sExerciseLab = new ExerciseLab(context);
        }
        return sExerciseLab;
    }

    public void addExercise(Exercise exercise) {
        ContentValues contentValues = getContentValues(exercise);
        mDatabase.insert(DatabaseSchema.ExercisesTable.NAME, null, contentValues);
    }

    public void removeExercise(Exercise exercise) {
        mDatabase.delete(DatabaseSchema.ExercisesTable.NAME, DatabaseSchema.ExercisesTable.Cols.UUID + " = ?", new String[] {exercise.getId().toString()});
    }

    public Exercise getExercise(UUID id) {
        ExerciseCursorWrapper cursor = queryCrimes(DatabaseSchema.ExercisesTable.Cols.UUID + " = ?", new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getExercise();
        } finally {
            cursor.close();
        }
    }

    public List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        ExerciseCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                exercises.add(cursor.getExercise());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return exercises;
    }

    public void updateExercise(Exercise exercise) {
        String uuidString = exercise.getId().toString();
        ContentValues values = getContentValues(exercise);

        mDatabase.update(DatabaseSchema.ExercisesTable.NAME, values, DatabaseSchema.ExercisesTable.Cols.UUID + " = ?", new String[] { uuidString});
    }

    private ExerciseCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.ExercisesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ExerciseCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ExercisesTable.Cols.UUID, exercise.getId().toString());
        values.put(DatabaseSchema.ExercisesTable.Cols.NAME, exercise.getName());
        values.put(DatabaseSchema.ExercisesTable.Cols.CATEGORY, exercise.getCategory());
        values.put(DatabaseSchema.ExercisesTable.Cols.PERFORMED_EXERCISES, new Gson().toJson(exercise.getPerformedExercises()));
        return values;
    }

    public void addDefaultExercises() {
        List<Exercise> defaultExercises = Exercise.getDefaultExercises(mContext);
        for (int i = 0; i < defaultExercises.size(); ++i) {
            addExercise(defaultExercises.get(i));
        }
    }

}
