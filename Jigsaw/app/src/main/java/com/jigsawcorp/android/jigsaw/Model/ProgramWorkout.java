package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;

import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.SharedEnums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ProgramWorkout {
    private UUID mId;
    private String mName, mDescription;
    private ArrayList<SharedEnums.MuscleGroups> mWorkedMuscleGroups;

    public ProgramWorkout() {
        mId = UUID.randomUUID();
        mName = "";
        mWorkedMuscleGroups = new ArrayList<>(Arrays.asList(SharedEnums.MuscleGroups.ABS, SharedEnums.MuscleGroups.BACK, SharedEnums.MuscleGroups.CHEST));
        mDescription = "";
    }

    public ProgramWorkout(UUID id, String name, ArrayList<SharedEnums.MuscleGroups> workedMuscleGroups, String description) {
        mId = id;
        mName = name;
        mWorkedMuscleGroups = workedMuscleGroups;
        mDescription = description;
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

    public UUID getId() {
        return mId;
    }
}
