package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        Log.i("Exercise", "Small constructor");
    }

    public Exercise(UUID id, String name, String category) {
        mName = name;
        //mMajorBodyPartsTargeted = majorBodyPartsTargeted;
        //mMinorBodyPartsTargeted = minorBodyPartsTargeted;
        mCategory = category;
        mId = id;
        Log.i("Exercise", "Big constructor");
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

    public static List<Exercise> getDefaultExercises(Context context) {
        String jsonString = null;
        try {
            InputStream is = context.getAssets().open("default_exercises.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        return  (List<Exercise>) new Gson().fromJson(jsonString, new TypeToken<ArrayList<Exercise>>(){}.getType());
    }

    public static void writeDefaultExercises(Context context) {
        
    }
}
