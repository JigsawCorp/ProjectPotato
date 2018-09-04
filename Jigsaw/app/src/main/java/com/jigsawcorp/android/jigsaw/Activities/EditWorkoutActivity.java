package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.shared_tabs.EditWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;

import java.util.UUID;

public class EditWorkoutActivity extends AppCompatActivity {
    private static final String EXTRA_WORKOUT_ID = "workout_id";

    private Workout mWorkout;
    private EditWorkoutFragment mEditWorkoutFragment;

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

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        String workoutId = getIntent().getStringExtra(EXTRA_WORKOUT_ID);
        if (workoutId.isEmpty()) {
            mWorkout = new Workout();
        }
        else {
            mWorkout = WorkoutLab.get(this).getWorkouts(UUID.fromString(workoutId));
        }
        mEditWorkoutFragment = EditWorkoutFragment.newInstance(UUID.fromString(workoutId));
        mEditWorkoutFragment.setListener(new EditWorkoutFragment.EditWorkoutFragmentEventListener() {
            @Override
            public void onPerformedExerciseDeleted(PerformedExercise performedExercise) {

            }

            @Override
            public void onWorkoutCreated(UUID workout) {

            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_edit_workout_edit_workout_container, mEditWorkoutFragment, "EditWorkoutFragment").commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Edit Workout");
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_edit_workout_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
    }

    @Override
    public void onPause() {
        super.onPause();
        saveUI();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveUI() {
        mEditWorkoutFragment.saveUI();
    }

}
