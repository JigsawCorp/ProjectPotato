package com.jigsawcorp.android.jigsaw.Util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jigsawcorp.android.jigsaw.Database.DatabaseSchema;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GsonUtil {
    public static class ExerciseDeserializer implements JsonDeserializer<Exercise> {

        @Override
        public Exercise deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            if(jsonObject.get("id") == null) {
                Log.i("Deserializer", "no id");
                return new Exercise(jsonObject.get("name").getAsString(), jsonObject.get("category").getAsString());
            }
            else if (jsonObject.get("performed_exercises") == null){
                Log.i("Deserializer", "with id");
                return new Exercise(UUID.fromString(jsonObject.get("id").getAsString()),jsonObject.get("name").getAsString(), jsonObject.get("category").getAsString());
            }
            else {
                Log.i("Deserializer", "with id and exercises");
                List<PerformedExercise> exercises = new Gson().fromJson(jsonObject.get("performed_exercises").getAsString(), new TypeToken<ArrayList<PerformedExercise>>(){}.getType());
                return new Exercise(UUID.fromString(jsonObject.get("id").getAsString()),jsonObject.get("name").getAsString(), jsonObject.get("category").getAsString(), exercises);
            }

        }
    }
}
