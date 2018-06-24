package com.jigsawcorp.android.jigsaw.Database.Exercise;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Exercise;

import java.util.UUID;

public class ExerciseCursorWrapper extends CursorWrapper {
    public ExerciseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Exercise getExercise() {
        Exercise exercise = new Exercise(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.UUID))),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.NAME)),
                getString(getColumnIndex(DatabaseSchema.ExercisesTable.Cols.CATEGORY)));
        return exercise;
    }

}
