package com.jigsawcorp.android.jigsaw.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jigsawcorp.android.jigsaw.Activities.MainActivity;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema.ExercisesTable;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema.WorkoutsTable;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private Context mContext;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onCreateDatabase();
    }

    public static final String TABLE_EXERCISES = "exercises_table";
    private static final String SQL_CREATE_TABLE_EXERCISES = "create table if not exists " + ExercisesTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            ExercisesTable.Cols.UUID + ", " +
            ExercisesTable.Cols.NAME + ", " +
            ExercisesTable.Cols.CATEGORY + ", " +
            ExercisesTable.Cols.PERFORMED_EXERCISES +
            ")";

    private static final String SQL_CREATE_TABLE_WORKOUTS = "create table if not exists " + WorkoutsTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            WorkoutsTable.Cols.UUID + ", " +
            WorkoutsTable.Cols.START_DATE + ", " +
            WorkoutsTable.Cols.END_DATE + ", " +
            WorkoutsTable.Cols.PERFORMED_EXERCISES + ", " +
            WorkoutsTable.Cols.NOTES +
            ")";

    private static final String SQL_CREATE_TABLE_CONFIGURATION = "create table if not exists " + DatabaseSchema.ConfigurationTable.NAME + "(" +
            DatabaseSchema.ConfigurationTable.Cols.CONFIGURATION_OPTION + ", " +
            DatabaseSchema.ConfigurationTable.Cols.VALUE +
            ")";

    private static final String SQL_CREATE_TABLE_USER = "create table if not exists " + DatabaseSchema.UserTable.NAME + "(" +
            DatabaseSchema.UserTable.Cols.DATE_OF_BIRTH + ", " +
            DatabaseSchema.UserTable.Cols.ACTIVE_WORKOUT +
            ")";

    private static final String SQL_CREATE_TABLE_PERFORMED_EXERCISES = "create table if not exists " + DatabaseSchema.PerformedExerciseTable.NAME + "(" +
            DatabaseSchema.PerformedExerciseTable.Cols.UUID + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.EXERCISE + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.WORKOUT + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.START_DATE + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.END_DATE + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.PERFORMED_SETS + ", " +
            DatabaseSchema.PerformedExerciseTable.Cols.NOTES +
            ")";

    private static final String SQL_CREATE_TABLE_PROGRAMS = "create table if not exists " + DatabaseSchema.ProgramTable.NAME + "(" +
            DatabaseSchema.ProgramTable.Cols.UUID + ", " +
            DatabaseSchema.ProgramTable.Cols.NAME + ", " +
            DatabaseSchema.ProgramTable.Cols.PROGRAM_WORKOUTS + ", " +
            DatabaseSchema.ProgramTable.Cols.TRAINING_TYPE + ", " +
            DatabaseSchema.ProgramTable.Cols.DIFFICULTY_LEVEL + ", " +
            DatabaseSchema.ProgramTable.Cols.SPLIT_TYPE + ", " +
            DatabaseSchema.ProgramTable.Cols.IS_DAY_BASED + ", " +
            DatabaseSchema.ProgramTable.Cols.DURATION + ", " +
            DatabaseSchema.ProgramTable.Cols.DAYS_PER_WEEK + ", " +
            DatabaseSchema.ProgramTable.Cols.DESCRIPTION  +
            ")";

    private static final String SQL_CREATE_TABLE_PROGRAM_WORKOUTS = "create table if not exists " + DatabaseSchema.ProgramWorkoutTable.NAME + "(" +
            DatabaseSchema.ProgramWorkoutTable.Cols.UUID + ", " +
            DatabaseSchema.ProgramWorkoutTable.Cols.NAME + ", " +
            DatabaseSchema.ProgramWorkoutTable.Cols.WORKED_MUSCLE_GROUPS + ", " +
            DatabaseSchema.ProgramWorkoutTable.Cols.PROGRAM_EXERCISES + ", " +
            DatabaseSchema.ProgramWorkoutTable.Cols.DESCRIPTION +
            ")";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        // Verifies if the context implements the database callbacks
        if (context instanceof MainActivity) {
            mCallbacks = (Callbacks) context;
        }
        else {
            mCallbacks = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_EXERCISES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_WORKOUTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONFIGURATION);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USER);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PERFORMED_EXERCISES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PROGRAMS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PROGRAM_WORKOUTS);
        addDefaultUser(sqLiteDatabase);
        mCallbacks.onCreateDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void addDefaultUser(SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.UserTable.Cols.DATE_OF_BIRTH, "");
        values.put(DatabaseSchema.UserTable.Cols.ACTIVE_WORKOUT, "");
        database.insert(DatabaseSchema.UserTable.NAME, null, values);
    }
}
