package com.jigsawcorp.android.jigsaw.Database.Configuration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConfigurationLab {
    private static ConfigurationLab sConfigurationLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    private ConfigurationLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static ConfigurationLab get(Context context) {
        if (sConfigurationLab == null) {
            sConfigurationLab = new ConfigurationLab(context);
        }
        return sConfigurationLab;
    }

    public void addConfigurationOption(String optionName, String value) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ConfigurationTable.Cols.CONFIGURATION_OPTION, optionName);
        values.put(DatabaseSchema.ConfigurationTable.Cols.VALUE, value);
        mDatabase.insert(DatabaseSchema.ExercisesTable.NAME, null, values);
    }

    public String getConfigurationOption(String optionName ) {
        Cursor cursor = queryConfigurationOptions(DatabaseSchema.ConfigurationTable.Cols.CONFIGURATION_OPTION + " = ?", new String[] { optionName});

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(DatabaseSchema.ConfigurationTable.Cols.VALUE));
        } finally {
            cursor.close();
        }
    }

    public void updateConfigurationOption(String optionName, String value) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.ConfigurationTable.Cols.CONFIGURATION_OPTION, optionName);
        values.put(DatabaseSchema.ConfigurationTable.Cols.VALUE, value);
        mDatabase.update(DatabaseSchema.ConfigurationTable.NAME, values, DatabaseSchema.ConfigurationTable.Cols.VALUE + " = ?", new String[] {optionName});
    }

    private Cursor queryConfigurationOptions(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.ConfigurationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return cursor;
    }

}
