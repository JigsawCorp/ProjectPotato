package com.jigsawcorp.android.jigsaw.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Fragments.ExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.ProgramsAdapter;

public class CreateProgramWorkoutActivity extends AppCompatActivity {

    private EditProgramWorkoutFragment mEditProgramWorkoutFragment;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CreateProgramWorkoutActivity.class);
        return intent;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_program_workout);

        mEditProgramWorkoutFragment = new EditProgramWorkoutFragment();
        mEditProgramWorkoutFragment.setProgramWorkout(new ProgramWorkout());

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_program_workout_edit_workout_container, mEditProgramWorkoutFragment, "EditProgramWorkoutFragment").commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Create New Workout Day");
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_create_program_workout_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
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

}
