package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class EditWorkoutActivity extends AppCompatActivity {
    private static final String EXTRA_WORKOUT_ID = "workout_id";

    public static Intent newIntent(Context packageContext, UUID workout) {
        Intent intent = new Intent(packageContext, EditWorkoutActivity.class);
        if (workout == null) {
            intent.putExtra(EXTRA_WORKOUT_ID, "");
        }
        else {
            intent.putExtra(EXTRA_WORKOUT_ID, workout.toString());
        }
        return intent;
    }
}
