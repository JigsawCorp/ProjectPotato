package com.jigsawcorp.android.jigsaw.Activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.jigsawcorp.android.jigsaw.Fragments.ExerciseListFragment;
import com.jigsawcorp.android.jigsaw.R;

import java.util.Arrays;

public class ExerciseListActivity extends AppCompatActivity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        Spinner bodyPartsSpinner = (Spinner) findViewById(R.id.spinner_body_part);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.body_parts_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyPartsSpinner.setAdapter(adapter);

        changeFragment(new ExerciseListFragment());
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("Exercises");
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_exercise_list_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_exercise_list_fragment_container, fragment).commit();
    }
}
