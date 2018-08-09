package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Fragments.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.HomeFragment;
import com.jigsawcorp.android.jigsaw.Fragments.PlanFragment;
import com.jigsawcorp.android.jigsaw.Fragments.ProgressFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_history.HistoryFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.ProgramsFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;

import java.util.UUID;

public class DetailedProgramActivity extends AppCompatActivity {
    private static final String EXTRA_PROGRAM_MODIFIED = "program_modified";
    private static final String EXTRA_PROGRAM_ID = "program";

    private BottomNavigationView mBottomNavigationView;

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, DetailedProgramActivity.class);
        intent.putExtra(EXTRA_PROGRAM_ID, uuid.toString());
        return intent;
    }


    private EditProgramFragment mEditProgramFragment;
    private Program mProgram;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_program);
        mProgram = ProgramLab.get(this).getProgram(UUID.fromString(getIntent().getStringExtra(EXTRA_PROGRAM_ID)));

        mBottomNavigationView = findViewById(R.id.activity_detailed_program_bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_current_workout:
                        break;
                    case R.id.bottom_navigation_workout_log:
                        break;
                    case R.id.bottom_navigation_routines:
                        break;
                    case R.id.bottom_navigation_progress:
                        break;
                }
                return true;
            }
        });

       // mEditProgramFragment = new EditProgramFragment();
        //mEditProgramFragment.setProgram(mProgram);
       // getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_program_edit_program_container, mEditProgramFragment, "EditProgramFragment").commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(mProgram.getName());
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_detailed_program_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_program_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setProgramModifiedResult(false);
                finish();
                return true;
            case R.id.menu_create_workout_save_program:
                if (mEditProgramFragment.verifyFields()) {
                    ProgramLab.get(this).updateProgram(mEditProgramFragment.getProgram());
                    setProgramModifiedResult(true);
                    finish();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setProgramModifiedResult(boolean isModified) {
        Intent data = new Intent();
        data.putExtra(EXTRA_PROGRAM_MODIFIED, isModified);
        setResult(RESULT_OK, data);
    }

    public static boolean wasProgramModified(Intent result) {
        return result.getBooleanExtra(EXTRA_PROGRAM_MODIFIED, false);
    }
}
