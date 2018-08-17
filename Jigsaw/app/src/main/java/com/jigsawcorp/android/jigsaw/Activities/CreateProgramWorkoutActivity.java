package com.jigsawcorp.android.jigsaw.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Database.ProgramWorkout.ProgramWorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.ExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.ProgramsAdapter;

import java.util.UUID;

public class CreateProgramWorkoutActivity extends AppCompatActivity {
    private static final String EXTRA_WORKOUT_CREATED = "workout_created";
    private static final String EXTRA_PROGRAM_ID = "program_id";
    private EditProgramWorkoutFragment mEditProgramWorkoutFragment;
    private Program mProgram;
    private MenuItem mCreateProgramWorkoutButton;

    public static Intent newIntent(Context packageContext, UUID programId) {
        Intent intent = new Intent(packageContext, CreateProgramWorkoutActivity.class);
        intent.putExtra(EXTRA_PROGRAM_ID, programId.toString());
        return intent;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_program_workout);

        mEditProgramWorkoutFragment = new EditProgramWorkoutFragment();
        mEditProgramWorkoutFragment.setProgramWorkout(new ProgramWorkout());

        mProgram = ProgramLab.get(this).getProgram(UUID.fromString(getIntent().getStringExtra(EXTRA_PROGRAM_ID)));


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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_program_workout_activity, menu);
        mCreateProgramWorkoutButton = (MenuItem) menu.findItem(R.id.menu_create_program_workout_save_workout);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setWorkoutCreatedResult(false);
                finish();
                return true;
            case R.id.menu_create_program_workout_save_workout:
                if (mEditProgramWorkoutFragment.verifyFields()) {
                    ProgramLab.get(this).getProgram(UUID.fromString(getIntent().getStringExtra(EXTRA_PROGRAM_ID)));
                    mProgram.getProgramWorkouts().add(mEditProgramWorkoutFragment.getProgram().getId());
                    ProgramWorkoutLab.get(this).addProgramWorkout(mEditProgramWorkoutFragment.getProgram());
                    setWorkoutCreatedResult(true);
                    finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setWorkoutCreatedResult(boolean isCreated) {
        Intent data = new Intent();
        data.putExtra(EXTRA_WORKOUT_CREATED, isCreated);
        setResult(RESULT_OK, data);
    }

}
