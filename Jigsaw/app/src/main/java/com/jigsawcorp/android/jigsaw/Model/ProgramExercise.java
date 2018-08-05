package com.jigsawcorp.android.jigsaw.Model;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramExercise {
    private UUID mExercise;
    private String mNotes;
    private List<Sets> mSets;

    public ProgramExercise(UUID exercise) {
        mExercise = exercise;
        mNotes = "";
        mSets = new ArrayList<>();
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public List<Sets> getSets() {
        return mSets;
    }

    public void setSets(List<Sets> sets) {
        mSets = sets;
    }
}
