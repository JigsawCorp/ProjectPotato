package com.jigsawcorp.android.jigsaw.Model;

import java.util.List;
import java.util.UUID;

public class Exercise {
    private UUID mId;
    private String mName;
    //private String[] mMajorBodyPartsTargeted;
    //private String[] mMinorBodyPartsTargeted;
    private String mCategory;
    private List<PerformedExercise> mPerformedExercises;

    public Exercise(String name, String category) {
        mName = name;
        //mMajorBodyPartsTargeted = majorBodyPartsTargeted;
        //mMinorBodyPartsTargeted = minorBodyPartsTargeted;
        mCategory = category;
        mId = UUID.randomUUID();
    }

    public Exercise(UUID id, String name, String category) {
        mName = name;
        //mMajorBodyPartsTargeted = majorBodyPartsTargeted;
        //mMinorBodyPartsTargeted = minorBodyPartsTargeted;
        mCategory = category;
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public UUID getId() {
        return mId;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
}
