package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Fragments.EditProgramFragment;
import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;

public class CreateProgramActivity extends AppCompatActivity {
    private static final String EXTRA_PROGRAM_CREATED = "program_created";


    private MenuItem mCreateProgramButton;
    private EditProgramFragment mEditProgramFragment;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);
        mEditProgramFragment = new EditProgramFragment();
        mEditProgramFragment.setProgram(new Program());
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_create_program_edit_program_container, mEditProgramFragment, "EditProgramFragment").commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("New Program");
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_create_program_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_create_program_fragment, menu);
        mCreateProgramButton = (MenuItem) menu.findItem(R.id.menu_current_workout_fragment_finish_workout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setProgramCreatedResult(false);
                finish();
                return true;
            case R.id.menu_create_workout_save_program:
                if (mEditProgramFragment.verifyFields()) {
                    ProgramLab.get(this).addProgram(mEditProgramFragment.getProgram());
                    setProgramCreatedResult(true);
                    finish();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setProgramCreatedResult(boolean isCreated) {
        Intent data = new Intent();
        data.putExtra(EXTRA_PROGRAM_CREATED, isCreated);
        setResult(RESULT_OK, data);
    }

    public static boolean wasProgramCreated(Intent result) {
        return result.getBooleanExtra(EXTRA_PROGRAM_CREATED, false);
    }
}
