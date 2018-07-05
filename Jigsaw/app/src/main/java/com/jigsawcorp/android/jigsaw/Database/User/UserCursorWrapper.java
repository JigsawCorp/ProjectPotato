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
        UUID uuid;
        try {
            uuid = UUID.fromString(getString(getColumnIndex(DatabaseSchema.UserTable.Cols.ACTIVE_WORKOUT)));

        }catch (IllegalArgumentException |NullPointerException exception) {
            uuid = null;
        }

        User user = new User(
                new Date(getLong(getColumnIndex(DatabaseSchema.UserTable.Cols.DATE_OF_BIRTH))),
                uuid);
        return user;
    }
}
