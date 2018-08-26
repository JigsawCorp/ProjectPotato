package com.jigsawcorp.android.jigsaw.Database.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Database.DataBaseHelper;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseCursorWrapper;
import com.jigsawcorp.android.jigsaw.Model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLab {
    private static UserLab sUserLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;


    private UserLab(Context context) {
        mDatabase = new DataBaseHelper(context).getWritableDatabase();
        mContext = context;
    }

    public static UserLab get(Context context) {
        if (sUserLab == null) {
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }

    public void addUser(User user) {
        ContentValues contentValues = getContentValues(user);
        mDatabase.insert(DatabaseSchema.UserTable.NAME, null, contentValues);
    }

    public User getUser() {
        User user = null;
        UserCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursor.getUser();
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return user;
    }


    public void updateUser(User user) {
        ContentValues values = getContentValues(user);

        mDatabase.update(DatabaseSchema.UserTable.NAME, values, null, null);
    }

    private  UserCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DatabaseSchema.UserTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor, mContext);
    }
    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseSchema.UserTable.Cols.DATE_OF_BIRTH, user.getDateOfBirth().getTime());
        values.put(DatabaseSchema.UserTable.Cols.ACTIVE_WORKOUT, user.getActiveWorkout() == null ? null : user.getActiveWorkout().toString());
        values.put(DatabaseSchema.UserTable.Cols.ACTIVE_PROGRAM, user.getActiveProgram() == null ? null : user.getActiveProgram().toString());
        return values;
    }

}
