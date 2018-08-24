package com.jigsawcorp.android.jigsaw.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.ProgramWorkout.ProgramWorkoutLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_current_workout.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_programs.EditProgramSetFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.ProgramExercise;
import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.RecyclerViewHelper.Adapters.SetAdapter;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.WorkoutExerciseAdapter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ProgramWorkoutActivity extends AppCompatActivity {
    // Extras
    private static final String EXTRA_PROGRAM_WORKOUT_ID = "program_workout_id";

    // Model
    private ProgramWorkout mProgramWorkout;

    // View
    private FloatingActionMenu menuCreate;
    private FloatingActionButton fabAddExercise;
    private RecyclerView mProgramExercisesRecyclerView;
    private WorkoutExerciseAdapter mAdapter;
    private ConstraintLayout mEditSetContainer;
    private EditProgramSetFragment mEditProgramSetFragment;
    private Button mAddSetButton;

    public static Intent newIntent(Context packageContext, UUID programWorkoutId) {
        Intent intent = new Intent(packageContext, ProgramWorkoutActivity.class);
        intent.putExtra(EXTRA_PROGRAM_WORKOUT_ID, programWorkoutId.toString());
        return intent;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_workout);

        mProgramWorkout = ProgramWorkoutLab.get(this).getProgramWorkout(UUID.fromString(getIntent().getStringExtra(EXTRA_PROGRAM_WORKOUT_ID)));

        mProgramExercisesRecyclerView = (RecyclerView) findViewById(R.id.activity_program_workout_recycler_view);
        mProgramExercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WorkoutExerciseAdapter(mProgramWorkout.getProgramExercises(), this);
        mAdapter.setWorkoutExercisesListEventListener(new WorkoutExerciseAdapter.WorkoutExerciseAdapterEventListener() {
            @Override
            public void onExerciseClicked(ProgramExercise programExercise) {

            }

            @Override
            public void onSetClicked(ProgramSet programSet, Boolean sameSet) {
                if (sameSet) {
                    hideEditSetFragment();
                }
                else {
                    if (mEditSetContainer.getVisibility() == View.GONE || mEditSetContainer.getVisibility() == View.INVISIBLE) {
                        showEditSetFragment();
                    }
                    mEditProgramSetFragment.setProgramSet(programSet);
                }
            }
        });
        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        touchHelper.attachToRecyclerView(mProgramExercisesRecyclerView);
        mProgramExercisesRecyclerView.setAdapter(mAdapter);
        mEditSetContainer = (ConstraintLayout) findViewById(R.id.activity_program_workout_edit_set_container);
        mEditProgramSetFragment = new EditProgramSetFragment();
        mEditProgramSetFragment.setOnEventListener(new EditProgramSetFragment.OnEventListener() {
            @Override
            public void onSetChanged(ProgramSet programSet) {
                mAdapter.updateSelectedSet(programSet);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_program_workout_edit_set_container, mEditProgramSetFragment, "EditProgramSetFragment").commit();
        mEditSetContainer.setVisibility(View.INVISIBLE);
        final ViewTreeObserver vto = mEditSetContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mEditSetContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mEditSetContainer.setVisibility(View.GONE);
            }
        });

        menuCreate = (FloatingActionMenu) findViewById(R.id.activity_program_workout_fab_menu);

        fabAddExercise = (FloatingActionButton) findViewById(R.id.activity_program_workout_fab_add_exercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ExerciseListActivity.newIntent(getApplicationContext(), true), RequestCodes.REQUEST_CODE_ADD_EXERCISE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(mProgramWorkout.getName());
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.activity_program_workout_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));

         hideEditSetFragment();

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


    private void updateUI() {
        mAdapter.setProgramExercises(mProgramWorkout.getProgramExercises());
        Log.i("BLABLABLA" , "updateUI, size " + mProgramWorkout.getProgramExercises().size());
    }

    private void saveUI() {
        mProgramWorkout.setProgramExercises(mAdapter.getProgramExercises());
        ProgramWorkoutLab.get(this).updateProgramWorkout(mProgramWorkout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == RequestCodes.REQUEST_CODE_ADD_EXERCISE) {
            if (data == null) {
                return;
            }

            List<UUID> exercises = SelectableExerciseListFragment.getSelectedExercises(data);
            mProgramWorkout.addExercises(ProgramExercise.createFromExercises(exercises));


            updateUI();
        }
        else if (requestCode == RequestCodes.REQUEST_CODE_EDIT_PERFORMED_EXERCISE) {
            if (data == null) {
                return;
            }
        }

    }

    public class SwipeAndDragHelper extends ItemTouchHelper.Callback {

        private ActionCompletionContract contract;

        public SwipeAndDragHelper(ActionCompletionContract contract) {
            this.contract = contract;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            contract.onViewMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public void onChildDraw(Canvas c,
                                RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX,
                                float dY,
                                int actionState,
                                boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
                viewHolder.itemView.setAlpha(alpha);
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public interface ActionCompletionContract {
        void onViewMoved(int oldPosition, int newPosition);
    }

    public interface OnPerformedExerciseListEventListener {
        void onExerciseClicked(PerformedExercise performedExercise);
        void onSetClicked(PerformedSet performedSet, Boolean sameSet);
    }


    private void hideEditSetFragment() {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                mEditSetContainer.getHeight());                // toYDelta
        animate.setDuration(500);
        // animate.setFillAfter(true);
        // animate1.setFillAfter(true);
        mEditSetContainer.startAnimation(animate);
        mEditSetContainer.setVisibility(View.GONE);
    }

    private void showEditSetFragment() {
        mEditSetContainer.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                mEditSetContainer.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);


        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                1f, 0.8f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        mEditSetContainer.setVisibility(View.VISIBLE);
        mEditSetContainer.startAnimation(animate);
        //mPerformedExercisesRecyclerView.startAnimation(anim);
    }

}
