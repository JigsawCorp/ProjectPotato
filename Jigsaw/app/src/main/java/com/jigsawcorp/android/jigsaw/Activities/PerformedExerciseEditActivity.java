package com.jigsawcorp.android.jigsaw.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.RecyclerViewHelper.Adapters.SetAdapter;

import org.w3c.dom.Text;

import java.util.UUID;

public class PerformedExerciseEditActivity extends AppCompatActivity {
    private static final String EXTRA_PERFORMED_EXERCISE_UUID = "com.jigsawcorp.android.jigsaw.performed_exercise_uuid";
    private PerformedExercise mPerformedExercise;
    private TextView mExerciseTitle;
    private RecyclerView mRecyclerView;
    private SetAdapter mAdapter;
    private Button mAddSetButton;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_performed_exercise_edit_edit_set_container, new EditSetFragment(), "EditSetFragment").commit();
        // Setup RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_performed_exercise_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SetAdapter(this, mPerformedExercise.getSets(), new SetAdapter.OnItemClickListener() {
            @Override
            public void onItemSelected(Set set) {
                if (set == null) {
                    hideEditSetFragment();
                }
                else {
                    showEditSetFragment();
                }
                ((EditSetFragment) getSupportFragmentManager().findFragmentById(R.id.activity_performed_exercise_edit_edit_set_container)).setSet(set);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAddSetButton = (Button) findViewById(R.id.activity_performed_exercise_button_add_set);
        mAddSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set newSet = new Set(0,0);
                ((EditSetFragment) getSupportFragmentManager().findFragmentById(R.id.activity_performed_exercise_edit_edit_set_container)).addNewSet(findLatestSet());
                mAdapter.addSet(newSet);
                showEditSetFragment();
            }
        });
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

        hideEditSetFragment();

    }

    @Override
    public void onPause() {
        super.onPause();
        PerformedExerciseLab.get(this).updatePerformedExercise(mPerformedExercise);
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

    private void updateUI() {
        mAdapter.setSets(mPerformedExercise.getSets());
        mAdapter.notifyDataSetChanged();
    }

    private Set findLatestSet() {
        if (mPerformedExercise.getSets() == null || mPerformedExercise.getSets().size() == 0) {
            // Check to see if any sets were performed
            // if so, return that last set
            return null;
        }
        else {
            return mPerformedExercise.getSets().get(mPerformedExercise.getSets().size() - 1);
        }
    }

    private void hideEditSetFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        EditSetFragment frag = (EditSetFragment) getSupportFragmentManager().findFragmentByTag("EditSetFragment");
        ft.hide(frag).commit();
    }

    private void showEditSetFragment() {
        getSupportFragmentManager().beginTransaction().show((EditSetFragment) getSupportFragmentManager().findFragmentByTag("EditSetFragment")).commit();
    }

}
