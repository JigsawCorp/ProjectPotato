package com.jigsawcorp.android.jigsaw.Model;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProgramExercise {
    private UUID mExercise;
    private String mNotes;
    private List<ProgramSet> mSets;

    public ProgramExercise(UUID exercise) {
        mExercise = exercise;
        mNotes = "";
        mSets = new ArrayList<>();
    }

    public static List<ProgramExercise> createFromExercises(List<UUID> exercises) {
        List<ProgramExercise> programExercises = new ArrayList<>();
        for (UUID exercise: exercises) {
            programExercises.add(new ProgramExercise(exercise));
        }
        return programExercises;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public List<ProgramSet> getSets() {
        return mSets;
    }

    public void setSets(List<ProgramSet> sets) {
        mSets = sets;
    }

    public UUID getExercise() {
        return mExercise;
    }

    public void addSet(ProgramSet set) {
        mSets.add(set);
    }
}
