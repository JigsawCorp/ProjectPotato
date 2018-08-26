package com.jigsawcorp.android.jigsaw.Database.User;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UserCursorWrapper extends CursorWrapper {
    private Context mContext;
    public UserCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        mContext = context;
    }

    public User getUser() {
        UUID activeWorkout, activeProgram;
        try {
            activeWorkout = UUID.fromString(getString(getColumnIndex(DatabaseSchema.UserTable.Cols.ACTIVE_WORKOUT)));

        }catch (IllegalArgumentException |NullPointerException exception) {
            activeWorkout = null;
        }

        try {
            activeProgram = UUID.fromString(getString(getColumnIndex(DatabaseSchema.UserTable.Cols.ACTIVE_PROGRAM)));

        }catch (IllegalArgumentException |NullPointerException exception) {
            activeProgram = null;
        }

        User user = new User(
                new Date(getLong(getColumnIndex(DatabaseSchema.UserTable.Cols.DATE_OF_BIRTH))),
                activeWorkout,
                activeProgram);
        return user;
    }
}
