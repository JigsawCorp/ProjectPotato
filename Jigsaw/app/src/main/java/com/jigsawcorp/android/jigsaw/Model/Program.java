package com.jigsawcorp.android.jigsaw.Model;

import android.content.res.Resources;

import com.jigsawcorp.android.jigsaw.R;

import java.util.List;
import java.util.UUID;

public class Program {
    public enum TrainingTypes {CARDIO, RESISTANCE, FLEXIBILITY, OTHER}
    public enum DifficultyLevels {BEGINNER, INTERMEDIATE, ADVANCED, EXPERT, UNSPECIFIED}
    private String mName, mDescription;
    private boolean isWeekly, isDayBased;
    private int mDuration;
    private TrainingTypes mTrainingType;
    private DifficultyLevels mDifficultyLevel;
    private List<UUID> mWorkouts;

    public Program() {
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setTrainingType(TrainingTypes type) {
        mTrainingType = type;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public TrainingTypes getTrainingType() {
        return mTrainingType;
    }

    public List<UUID> getWorkouts() {
        return mWorkouts;
    }

}
