package com.jigsawcorp.android.jigsaw.Database.PerformedExercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PerformedExerciseLab {
    private static PerformedExerciseLab sPerformedExerciseLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private PerformedExerciseLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static PerformedExerciseLab get(Context context) {
        if (sPerformedExerciseLab == null) {
            sPerformedExerciseLab = new PerformedExerciseLab(context);
        }
        return sPerformedExerciseLab;
    }

    public void addPerformedExercise(PerformedExercise performedExercise) {
        ContentValues contentValues = getContentValues(performedExercise);
        mDatabase.insert(DatabaseSchema.PerformedExerciseTable.NAME, null, contentValues);
    }

    public void addPerformedExercises(List<PerformedExercise> performedExercises) {
        for (PerformedExercise exercise: performedExercises) {
            addPerformedExercise(exercise);
        }
    }

    public void removePerformedExercise(PerformedExercise performedExercise) {
        mDatabase.delete(DatabaseSchema.PerformedExerciseTable.NAME, DatabaseSchema.PerformedExerciseTable.Cols.UUID + " = ?", new String[] {performedExercise.getmId().toString()});
    }

    public PerformedExercise getPerformedExercise(UUID id) {
        PerformedExerciseCursorWrapper cursor = queryPerformedExercises(DatabaseSchema.PerformedExerciseTable.Cols.UUID + " = ?", new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getPerformedExercise();
        } finally {
            cursor.close();
        }
    }

    public List<PerformedExercise> getPerformedExercises(List<UUID> uuids) {
        List<PerformedExercise> performedExercises = new ArrayList<>();
        for (UUID uuid: uuids) {
            performedExercises.add(getPerformedExercise(uuid));
        }
        return performedExercises;
    }

    public List<PerformedExercise> getAllPerformedExercises() {
        List<PerformedExercise> performedExercises = new ArrayList<>();

        PerformedExerciseCursorWrapper cursor = queryPerformedExercises(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                performedExercises.add(cursor.getPerformedExercise());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return performedExercises;
    }

    public void updatePerformedExercise(PerformedExercise performedExercise) {
        ContentValues values = getContentValues(performedExercise);

        mDatabase.update(DatabaseSchema.PerformedExerciseTable.NAME, values, DatabaseSchema.PerformedExerciseTable.Cols.UUID + " = ?", new String[] { performedExercise.getmId().toString()});
    }

    public void updatePerformedExercises(List<PerformedExercise> performedExercises) {
        for (PerformedExercise exercise : performedExercises) {
            updatePerformedExercise(exercise);
        }
    }

    private PerformedExerciseCursorWrapper queryPerformedExercises(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.PerformedExerciseTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new PerformedExerciseCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(PerformedExercise performedExercise) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.UUID, performedExercise.getmId().toString());
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.START_DATE, performedExercise.getStartDate().getTime());
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.END_DATE, performedExercise.getEndDate() == null ? 0 : 1);
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.EXERCISE, performedExercise.getExercise().toString());
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.WORKOUT, performedExercise.getWorkout().toString());
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.PERFORMED_SETS, new Gson().toJson(performedExercise.getPerformedSets()));
        values.put(DatabaseSchema.PerformedExerciseTable.Cols.NOTES, performedExercise.getNotes());
        return values;
    }
}

