package com.jigsawcorp.android.jigsaw.Database.ProgramWorkout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseCursorWrapper;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.Util.SharedEnums;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramWorkoutLab {
    private static ProgramWorkoutLab sProgramWorkoutLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private ProgramWorkoutLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static ProgramWorkoutLab get(Context context) {
        if (sProgramWorkoutLab == null) {
            sProgramWorkoutLab = new ProgramWorkoutLab(context);
        }
        return  sProgramWorkoutLab;
    }

    public void addProgramWorkout(ProgramWorkout programWorkout) {
        ContentValues contentValues = getContentValues(programWorkout);
        mDatabase.insert(DatabaseSchema.ProgramWorkoutTable.NAME, null, contentValues);
    }

    public void removeProgramWorkout(ProgramWorkout programWorkout) {
        mDatabase.delete(DatabaseSchema.ProgramWorkoutTable.NAME, DatabaseSchema.ProgramWorkoutTable.Cols.UUID + " = ?", new String[] {programWorkout.getId().toString()});
    }

    public ProgramWorkout getProgramWorkout(UUID id) {
        ProgramWorkoutCursorWrapper cursor = queryProgramWorkouts(DatabaseSchema.ProgramWorkoutTable.Cols.UUID + " = ?", new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getProgramWorkout();
        } finally {
            cursor.close();
        }
    }

    public void updateProgramWorkout(ProgramWorkout programWorkout) {
        String uuidString = programWorkout.getId().toString();
        ContentValues values = getContentValues(programWorkout);

        mDatabase.update(DatabaseSchema.ProgramWorkoutTable.NAME, values, DatabaseSchema.ProgramWorkoutTable.Cols.UUID + " = ?", new String[] { uuidString});
    }

    private ProgramWorkoutCursorWrapper queryProgramWorkouts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.ProgramWorkoutTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ProgramWorkoutCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(ProgramWorkout programWorkout) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ProgramWorkoutTable.Cols.UUID, programWorkout.getId().toString());
        values.put(DatabaseSchema.ProgramWorkoutTable.Cols.NAME, programWorkout.getName());
        values.put(DatabaseSchema.ProgramWorkoutTable.Cols.WORKED_MUSCLE_GROUPS, new Gson().toJson(programWorkout.getWorkedMuscleGroups()));
        values.put(DatabaseSchema.ProgramWorkoutTable.Cols.DESCRIPTION, programWorkout.getDescription());
        return values;
    }

}
