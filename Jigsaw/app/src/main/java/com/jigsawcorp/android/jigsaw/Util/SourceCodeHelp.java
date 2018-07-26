package com.jigsawcorp.android.jigsaw.Util;

import android.content.Context;

import com.google.gson.Gson;
import com.jigsawcorp.android.jigsaw.Model.Exercise;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceCodeHelp {
    public static void writeDefaultExercises() {
        List<Exercise> defaultExercises = new ArrayList<>();
        defaultExercises.add(new Exercise("Bench Press", "Chest" ));
        defaultExercises.add(new Exercise("Close Grip Bench Press", "Chest" ));
        defaultExercises.add(new Exercise("Tricep Pushdown", "Triceps" ));
        defaultExercises.add(new Exercise("Bicep Dumbell curl", "Biceps"));
        defaultExercises.add(new Exercise("Ab Crunch", "Abs" ));
        defaultExercises.add(new Exercise("allah", "chest"));
        String mJsonResponse = new Gson().toJson(defaultExercises);
        try {
            FileWriter file = new FileWriter("defaultExercises.json");
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
