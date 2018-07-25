package com.jigsawcorp.android.jigsaw.Database.Workout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseCursorWrapper;
import com.jigsawcorp.android.jigsaw.Model.Workout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import hirondelle.date4j.DateTime;

public class WorkoutLab {
    private static WorkoutLab sWorkoutLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    private WorkoutLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static WorkoutLab get(Context context) {
        if (sWorkoutLab == null) {
            sWorkoutLab = new WorkoutLab(context);
        }
        return sWorkoutLab;
    }

    public void addWorkout(Workout workout) {
        ContentValues contentValues = getContentValues(workout);
        mDatabase.insert(DatabaseSchema.WorkoutsTable.NAME, null, contentValues);
    }

    public void removeWorkout(Workout workout) {
        mDatabase.delete(DatabaseSchema.WorkoutsTable.NAME, DatabaseSchema.WorkoutsTable.Cols.UUID + " = ?", new String[] {workout.getId().toString()});
    }

    public Workout getWorkout(UUID id) {
        WorkoutCursorWrapper cursor = queryCrimes(DatabaseSchema.WorkoutsTable.Cols.UUID + " = ?", new String[] { id.toString()});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getWorkout();
        } finally {
            cursor.close();
        }
    }

    public List<Workout> getWorkout(DateTime date) {
        //Log.i("Blyat", "Start day: " + date.getStartOfDay().getMilliseconds(TimeZone.getDefault()) + ", end day: " + date.getEndOfDay().getMilliseconds(TimeZone.getDefault()));
        List<Workout> workouts = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + DatabaseSchema.WorkoutsTable.NAME+ " WHERE " + DatabaseSchema.WorkoutsTable.Cols.START_DATE + " > " + date.getStartOfDay().getMilliseconds(TimeZone.getDefault())
                        + " AND " + DatabaseSchema.WorkoutsTable.Cols.START_DATE + " < " + date.getEndOfDay().getMilliseconds(TimeZone.getDefault()), null);
        WorkoutCursorWrapper cursorWrapper = new WorkoutCursorWrapper(cursor, mContext);

        try {
            if (cursorWrapper.getCount() == 0) {
                return workouts;
            }
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                workouts.add(cursorWrapper.getWorkout());
                Log.i("Blyat", "Found " + cursorWrapper.getWorkout().getId());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return workouts;
    }

    public List<Workout> getWorkout(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        long startOfDay = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, 1);
        long startNextDay = calendar.getTimeInMillis();

        List<Workout> workouts = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(
                "SELECT * FROM " + DatabaseSchema.WorkoutsTable.NAME+ " WHERE " + DatabaseSchema.WorkoutsTable.Cols.START_DATE + " > " + startOfDay
                        + " AND " + DatabaseSchema.WorkoutsTable.Cols.START_DATE + " < " + startNextDay, null);
        WorkoutCursorWrapper cursorWrapper = new WorkoutCursorWrapper(cursor, mContext);

        try {
            if (cursorWrapper.getCount() == 0) {
                return workouts;
            }
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                workouts.add(cursorWrapper.getWorkout());
                Log.i("Blyat", "Found " + cursorWrapper.getWorkout().getId());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return workouts;
    }

    public List<Workout> getWorkouts() {
        List<Workout> workouts = new ArrayList<>();

        WorkoutCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                workouts.add(cursor.getWorkout());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return workouts;
    }

    public void updateWorkout(Workout workout) {
        String uuidString = workout.getId().toString();
        ContentValues values = getContentValues(workout);

        mDatabase.update(DatabaseSchema.WorkoutsTable.NAME, values, DatabaseSchema.WorkoutsTable.Cols.UUID + " = ?", new String[] { uuidString});
    }

    private WorkoutCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.WorkoutsTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new WorkoutCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(Workout workout) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.WorkoutsTable.Cols.UUID, workout.getId().toString());
        values.put(DatabaseSchema.WorkoutsTable.Cols.START_DATE, workout.getStartDate().getTime());
        values.put(DatabaseSchema.WorkoutsTable.Cols.END_DATE, workout.getEndDate() == null ? 0 : workout.getEndDate().getTime());
        values.put(DatabaseSchema.WorkoutsTable.Cols.PERFORMED_EXERCISES, new Gson().toJson(workout.getPerformedExercises()));
        values.put(DatabaseSchema.WorkoutsTable.Cols.NOTES, workout.getNotes());
        return values;
    }
}
