package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.jigsawcorp.android.jigsaw.Fragments.ExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.R;

import java.util.Arrays;

public class ExerciseListActivity extends AppCompatActivity {
    private static final String EXTRA_NEED_SELECTABLE = "com.jigsawcorp.android.jigsaw.need_selectables";

    public static Intent newIntent(Context packageContext, boolean needSelectables) {
        Intent intent = new Intent(packageContext, ExerciseListActivity.class);
        intent.putExtra(EXTRA_NEED_SELECTABLE, needSelectables);
        return intent;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise_list);

        Spinner bodyPartsSpinner = (Spinner) findViewById(R.id.spinner_body_part);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.body_parts_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bodyPartsSpinner.setAdapter(adapter);

        if (getIntent().getBooleanExtra(EXTRA_NEED_SELECTABLE, true)) {
            changeFragment(new SelectableExerciseListFragment());
        }
        else {
            changeFragment(new ExerciseListFragment());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                SelectableExerciseListFragment currentFragment = (SelectableExerciseListFragment) getSupportFragmentManager().findFragmentById(R.id.activity_exercise_list_fragment_container);
                currentFragment.sendExercises();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_exercise_list_fragment_container, fragment).commit();
    }
}
