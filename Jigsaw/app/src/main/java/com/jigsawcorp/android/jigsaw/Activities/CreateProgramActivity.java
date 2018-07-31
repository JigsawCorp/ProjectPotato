package com.jigsawcorp.android.jigsaw.Activities;

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

import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;

public class CreateProgramActivity extends AppCompatActivity {
    private Spinner mTrainingTypesSpinner, mDaysPerWeekSpinner;
    private Program mProgram;

    private MenuItem mCreateProgramButton;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);
        mTrainingTypesSpinner = (Spinner) findViewById(R.id.activity_create_program_spinner_training_types);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.array_training_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTrainingTypesSpinner.setAdapter(adapter);
        mTrainingTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                switch (parent.getId()) {
                    case R.id.activity_create_program_spinner_training_types:
                        mProgram.setTrainingType(Program.TrainingTypes.values()[position]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDaysPerWeekSpinner = (Spinner) findViewById(R.id.activity_create_program_spinner_days_per_week);
        String[] daysPerWeekStringArray = new String[]{"1", "2", "3", "4", "5", "6", "7", getResources().getString(R.string.variable)};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, daysPerWeekStringArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDaysPerWeekSpinner.setAdapter(adapter2);
        mProgram = new Program();

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
                finish();
                return true;
            case R.id.menu_create_workout_save_program:
                if (verifyAllFields()) {
                    // save workout
                }
                else {
                    Toast.makeText(getParent(),"Please enter all fields", Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean verifyAllFields() {
        return true;
    }
}
