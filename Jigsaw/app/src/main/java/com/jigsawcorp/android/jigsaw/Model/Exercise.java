package com.jigsawcorp.android.jigsaw.Model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Exercise {
    public static enum BodyPart {ABS, BACK, BICEPS, CALVES, CARDIO, CHEST, FOREARMS, LEGS, SHOULDERS, TRICEPS}
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
        Log.i("Exercise: " + name, "Small constructor");
    }

    public Exercise(UUID id, String name, String category) {
        mName = name;
        //mMajorBodyPartsTargeted = majorBodyPartsTargeted;
        //mMinorBodyPartsTargeted = minorBodyPartsTargeted;
        mCategory = category;
        mId = id;
        Log.i("Exercise: " + name, "Big constructor");
    }

    public Exercise(UUID id, String name, String category, List<PerformedExercise> performedExercises) {
        mName = name;
        //mMajorBodyPartsTargeted = majorBodyPartsTargeted;
        //mMinorBodyPartsTargeted = minorBodyPartsTargeted;
        mCategory = category;
        mId = id;
        mPerformedExercises = performedExercises;
        Log.i("Exercise: " + name, "Bigger constructor");
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

    public List<PerformedExercise> getPerformedExercises() {
        return mPerformedExercises;
    }

    public void setPerformedExercises(List<PerformedExercise> performedExercises) {
        mPerformedExercises = performedExercises;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public static List<Exercise> getDefaultExercises(Context context) {
        String jsonString = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Exercise.class, new GsonUtil.ExerciseDeserializer()).create();
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


        return  (List<Exercise>) gson.fromJson(jsonString, new TypeToken<ArrayList<Exercise>>(){}.getType());
    }


}
