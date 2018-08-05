package com.jigsawcorp.android.jigsaw.Database.Program;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramCursorWrapper extends CursorWrapper {
    private Context mContext;
    public ProgramCursorWrapper(Cursor cursor , Context context) {
        super(cursor);
        mContext = context;
    }

    public Program getProgram() {
        //List<PerformedExercise> exercises = new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.WorkoutsTable.Cols.PERFORMED_EXERCISES)), new TypeToken<ArrayList<PerformedExercise>>(){}.getType());
        Program program = new Program(
                UUID.fromString(getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.UUID))),
                getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.NAME)),
                (List<UUID>) new Gson().fromJson(getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.PROGRAM_WORKOUTS)), new TypeToken<ArrayList<UUID>>(){}.getType()),
                Program.TrainingTypes.valueOf(getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.TRAINING_TYPE))),
                Program.DifficultyLevels.valueOf(getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.DIFFICULTY_LEVEL))),
                Program.SplitTypes.valueOf(getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.SPLIT_TYPE))),
                getInt(getColumnIndex(DatabaseSchema.ProgramTable.Cols.IS_DAY_BASED)) != 0,
                getInt(getColumnIndex(DatabaseSchema.ProgramTable.Cols.DURATION)),
                getInt(getColumnIndex(DatabaseSchema.ProgramTable.Cols.DAYS_PER_WEEK)),
                getString(getColumnIndex(DatabaseSchema.ProgramTable.Cols.DESCRIPTION)));
        return program;
    }
}
