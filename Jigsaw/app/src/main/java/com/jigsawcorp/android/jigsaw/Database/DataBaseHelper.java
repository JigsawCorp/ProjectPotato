package com.jigsawcorp.android.jigsaw.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema.ExercisesTable;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema.WorkoutsTable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";

    public static final String TABLE_EXERCISES = "exercises_table";
    private static final String SQL_CREATE_TABLE_EXERCISES = "create table " + ExercisesTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            ExercisesTable.Cols.UUID + ", " +
            ExercisesTable.Cols.NAME + ", " +
            ExercisesTable.Cols.CATEGORY +
            ")";

    private static final String SQL_CREATE_TABLE_WORKOUTS = "create table " + WorkoutsTable.NAME + "(" +
            "_id integer primary key autoincrement, " +
            WorkoutsTable.Cols.UUID + ", " +
            WorkoutsTable.Cols.START_DATE + ", " +
            WorkoutsTable.Cols.END_DATE + ", " +
            WorkoutsTable.Cols.PERFORMED_EXERCISES + ", " +
            WorkoutsTable.Cols.NOTES + " " +
            ")";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_EXERCISES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_WORKOUTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
