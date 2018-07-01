package com.jigsawcorp.android.jigsaw.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_EXERCISES);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_WORKOUTS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CONFIGURATION);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USER);
        mCallbacks.onCreateDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
