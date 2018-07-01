package com.jigsawcorp.android.jigsaw.Database.User;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.User;

import java.util.ArrayList;

public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        User user = new User(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.UUID))),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.NAME)),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.CATEGORY)),
                exercises);
        return user;
    }
}
