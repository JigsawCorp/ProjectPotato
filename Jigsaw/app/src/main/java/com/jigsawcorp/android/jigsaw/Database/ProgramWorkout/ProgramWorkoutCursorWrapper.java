package com.jigsawcorp.android.jigsaw.Database.ProgramWorkout;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.Model.ProgramExercise;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.Util.SharedEnums;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramWorkoutCursorWrapper extends CursorWrapper {
    private Context mContext;
    public ProgramWorkoutCursorWrapper(Cursor cursor , Context context) {
        super(cursor);
        mContext = context;
    }

    public ProgramWorkout getProgramWorkout() {
        ProgramWorkout programWorkout = new ProgramWorkout(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.ProgramWorkoutTable.Cols.UUID))),
                getString(getColumnIndex(DatabaseSchema.ProgramWorkoutTable.Cols.NAME)),
                (ArrayList<SharedEnums.MuscleGroups>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.ProgramWorkoutTable.Cols.WORKED_MUSCLE_GROUPS)), new TypeToken<ArrayList<SharedEnums.MuscleGroups>>(){}.getType()),
                (ArrayList<ProgramExercise>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.ProgramWorkoutTable.Cols.PROGRAM_EXERCISES)), new TypeToken<ArrayList<ProgramExercise>>(){}.getType()),
                getString(getColumnIndex(DatabaseSchema.ProgramWorkoutTable.Cols.DESCRIPTION)));
        return programWorkout;
    }
}
