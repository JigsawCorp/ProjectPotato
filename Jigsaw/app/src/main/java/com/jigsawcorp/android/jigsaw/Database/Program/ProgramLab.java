package com.jigsawcorp.android.jigsaw.Database.Program;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramLab {
    private static ProgramLab sProgramLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    private ProgramLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static ProgramLab get(Context context) {
        if (sProgramLab == null) {
            sProgramLab = new ProgramLab(context);
        }
        return sProgramLab;
    }

    public void addProgram(Program program) {
        ContentValues contentValues = getContentValues(program);
        mDatabase.insert(DatabaseSchema.ProgramTable.NAME, null, contentValues);
    }

    public void removeProgram(Program program) {
        mDatabase.delete(DatabaseSchema.ProgramTable.NAME, DatabaseSchema.ProgramTable.Cols.UUID + " = ?", new String[] {program.getId().toString()});
    }

    public Program getProgram(UUID id) {
        ProgramCursorWrapper cursor = queryPrograms(DatabaseSchema.ProgramTable.Cols.UUID + " = ?", new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getProgram();
        } finally {
            cursor.close();
        }
    }

    public List<Program> getPrograms() {
        List<Program> exercises = new ArrayList<>();

        ProgramCursorWrapper cursor = queryPrograms(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                exercises.add(cursor.getProgram());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return exercises;
    }

    /*
    public List<Program> getExercises(String bodyPart) {
        List<Exercise> exercises = new ArrayList<>();

        ExerciseCursorWrapper cursor = queryCrimes(DatabaseSchema.ExercisesTable.Cols.CATEGORY + " = ?", new String[] {bodyPart});

        try {
            if (cursor.getCount() == 0) {
                return  exercises;
            }

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
    */

    public void updateProgram(Program program) {
        String uuidString = program.getId().toString();
        ContentValues values = getContentValues(program);

        mDatabase.update(DatabaseSchema.ProgramTable.NAME, values, DatabaseSchema.ProgramTable.Cols.UUID + " = ?", new String[] { uuidString});
    }

    private ProgramCursorWrapper queryPrograms(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.ProgramTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                DatabaseSchema.ProgramTable.Cols.NAME + " ASC"
        );
        return new ProgramCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(Program program) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ProgramTable.Cols.UUID, program.getId().toString());
        values.put(DatabaseSchema.ProgramTable.Cols.NAME, program.getName());
        values.put(DatabaseSchema.ProgramTable.Cols.PROGRAM_WORKOUTS, new Gson().toJson(program.getProgramWorkouts()));
        values.put(DatabaseSchema.ProgramTable.Cols.TRAINING_TYPE, program.getTrainingType().toString());
        values.put(DatabaseSchema.ProgramTable.Cols.DIFFICULTY_LEVEL, program.getDifficultyLevel().toString());
        values.put(DatabaseSchema.ProgramTable.Cols.SPLIT_TYPE, program.getSpliType().toString());
        values.put(DatabaseSchema.ProgramTable.Cols.IS_DAY_BASED, program.isDayBased() ? 1 : 0);
        values.put(DatabaseSchema.ProgramTable.Cols.DURATION, program.getDuration());
        values.put(DatabaseSchema.ProgramTable.Cols.DAYS_PER_WEEK, program.getDaysPerWeek());
        values.put(DatabaseSchema.ProgramTable.Cols.DESCRIPTION, program.getDescription());
        return values;
    }


}
