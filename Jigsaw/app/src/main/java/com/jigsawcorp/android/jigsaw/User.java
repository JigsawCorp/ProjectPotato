package com.jigsawcorp.android.jigsaw;

import android.database.sqlite.SQLiteDatabase;

import java.util.Date;
import java.util.List;

public class User {
    private Date mDateOfBirth;
    private SQLiteDatabase mDataBase;
    private List<Exercise> mExercises;
}
