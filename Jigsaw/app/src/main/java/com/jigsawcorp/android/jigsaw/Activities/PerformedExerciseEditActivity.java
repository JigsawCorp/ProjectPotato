package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.R;

import org.w3c.dom.Text;

import java.util.UUID;

public class PerformedExerciseEditActivity extends AppCompatActivity {
    private static final String EXTRA_PERFORMED_EXERCISE_UUID = "com.jigsawcorp.android.jigsaw.performed_exercise_uuid";
    private PerformedExercise mPerformedExercise;
    private TextView mExerciseTitle;

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, PerformedExerciseEditActivity.class);
        intent.putExtra(EXTRA_PERFORMED_EXERCISE_UUID, uuid.toString());
        return intent;
    }
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performed_exercise_edit);
        mPerformedExercise = PerformedExerciseLab.get(this).getPerformedExercise(UUID.fromString(getIntent().getStringExtra(EXTRA_PERFORMED_EXERCISE_UUID)));
        getLayoutInflater().inflate(R.layout.list_item_performed_exercise,(ViewGroup) findViewById(R.id.activity_performed_exercise_edit_performed_exercise_container));
        ((TextView) findViewById(R.id.list_item_performed_exercise_position_indicator)).setVisibility(View.GONE);
        mExerciseTitle = (TextView) findViewById(R.id.list_item_performed_exercise_title);
        mExerciseTitle.setText(ExerciseLab.get(this).getExercise(mPerformedExercise.getExercise()).getName());
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_performed_exercise_edit_edit_set_container, new EditSetFragment()).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle("");
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_performed_exercise_edit_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
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
