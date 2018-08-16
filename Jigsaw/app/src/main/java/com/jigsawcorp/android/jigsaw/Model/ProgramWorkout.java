package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;
import android.content.res.Resources;

import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.List;

public class ProgramWorkout {
    private String mName, mDescription;
    private ArrayList<ProgramExercise> mExercises;
    private boolean[] selectedMuscleGroups;

    public ProgramWorkout() {
        mName = "";
        mExercises = new ArrayList<>();
        selectedMuscleGroups = new boolean[0];
        mDescription = "";
    }
    public boolean[] getSelectedMuscleGroups() {
        return selectedMuscleGroups;
    }

    public void setSelectedMuscleGroups(boolean[] selectedMuscleGroups) {
        this.selectedMuscleGroups = selectedMuscleGroups;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ArrayList<String> getWorkedMuscleGroups(Context context) {
        ArrayList<String> workedMuscleGroups = new ArrayList<>();
        context.getResources().getStringArray(R.array.muscle_groups_array);
        for (int i = 0; i < selectedMuscleGroups.length; ++i) {
            if (selectedMuscleGroups[i]) {

            }
        }
        return null;
    }
}
