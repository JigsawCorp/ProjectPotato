package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.SharedEnums;

import java.util.ArrayList;
import java.util.Arrays;

public class ProgramWorkout {
    private String mName, mDescription;
    private ArrayList<ProgramExercise> mExercises;
    private ArrayList<SharedEnums.MuscleGroups> mWorkedMuscleGroups;
    private boolean[] selectedMuscleGroups;

    public ProgramWorkout() {
        mName = "";
        mExercises = new ArrayList<>();
        mWorkedMuscleGroups = new ArrayList<>(Arrays.asList(SharedEnums.MuscleGroups.ABS, SharedEnums.MuscleGroups.BACK, SharedEnums.MuscleGroups.CHEST));
        mDescription = "";
    }
    public boolean[] getSelectedMuscleGroups() {
        boolean[] selections = new boolean[SharedEnums.MuscleGroups.values().length];
        Arrays.fill(selections, Boolean.FALSE);
        for (int i = 0; i < mWorkedMuscleGroups.size(); ++i) {
            selections[mWorkedMuscleGroups.get(i).ordinal()] = true;
        }
        return selections;
    }

    public void setSelectedMuscleGroups(boolean[] selectedMuscleGroups) {
        mWorkedMuscleGroups.clear();
        for (int i = 0; i < selectedMuscleGroups.length; ++i) {
            if (selectedMuscleGroups[i]) {
                mWorkedMuscleGroups.add(SharedEnums.MuscleGroups.values()[i]);
            }
        }
    }

    public ArrayList<SharedEnums.MuscleGroups> getWorkedMuscleGroups() {
        return mWorkedMuscleGroups;
    }

    public ArrayList<String> getWorkedMuscleGroupsAsStrings() {
        ArrayList<String> workedMuscles = new ArrayList<>();
        for (int i = 0; i < mWorkedMuscleGroups.size(); ++i) {
            workedMuscles.add(mWorkedMuscleGroups.get(i).toString());
        }
        return workedMuscles;
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
