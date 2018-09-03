package com.jigsawcorp.android.jigsaw.Fragments.shared_tabs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Activities.ExerciseListActivity;
import com.jigsawcorp.android.jigsaw.Activities.PerformedExerciseEditActivity;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Fragments.SelectableExerciseListFragment;
import com.jigsawcorp.android.jigsaw.Fragments.tab_current_workout.CurrentWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.PerformedExerciseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EditWorkoutFragment extends Fragment implements EditSetFragment.OnSetModifiedListener{

    // Extras
    private static final String EXTRA_WORKOUT_ID = "workout_id";


    // View
    private FloatingActionMenu menuCreate;
    private FloatingActionButton fabAddRoutine;
    private FloatingActionButton fabAddExercise;
    private FloatingActionButton fab3;
    private RecyclerView mPerformedExercisesRecyclerView;
    private Workout mWorkout;
    private ConstraintLayout mEditSetContainer;

    private BottomNavigationView mBottomNavigationView;
    // Controller
    private EditWorkoutFragmentEventListener mListener;
    private PerformedExerciseAdapter mAdapter;
    private EditSetFragment mEditSetFragment;

    public interface EditWorkoutFragmentEventListener {
        public void onPerformedExerciseDeleted(PerformedExercise performedExercise);
        void onWorkoutCreated(UUID workout);
    }

    public static EditWorkoutFragment newInstance(UUID workout) {
        Bundle bundle = new Bundle();
        if (workout == null) {
            bundle.putString(EXTRA_WORKOUT_ID, "");
        }
        else {
            bundle.putString(EXTRA_WORKOUT_ID, workout.toString());
        }
        EditWorkoutFragment fragment = new EditWorkoutFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    // Model
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().getString(EXTRA_WORKOUT_ID).isEmpty()) {
            mWorkout = null;
        }
        else {
            mWorkout = WorkoutLab.get(getContext()).getWorkouts(UUID.fromString(getArguments().getString(EXTRA_WORKOUT_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_workout, container, false);

        menuCreate = (FloatingActionMenu) v.findViewById(R.id.fragment_edit_workout_action_menu_add);

        fabAddRoutine = (FloatingActionButton) v.findViewById(R.id.fragment_edit_workout_fab_add_routine);
        fabAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabAddExercise = (FloatingActionButton) v.findViewById(R.id.fragment_edit_workout_fab_add_exercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ExerciseListActivity.newIntent(getContext(), true), RequestCodes.REQUEST_CODE_ADD_EXERCISE);
            }
        });

        fab3 = (FloatingActionButton) v.findViewById(R.id.fragment_edit_workout_fab_edit);

        mBottomNavigationView = (BottomNavigationView) v.findViewById(R.id.fragment_edit_workout_bottom_navigation);
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

        mPerformedExercisesRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_edit_workout_recycler_view);
        mPerformedExercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEditSetContainer = (ConstraintLayout) v.findViewById(R.id.fragment_edit_workout_edit_set_container);
        mEditSetFragment = new EditSetFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_edit_workout_edit_set_container, mEditSetFragment, "EditSetFragment").commit();
        mEditSetContainer.setVisibility(View.INVISIBLE);
        final ViewTreeObserver vto = mEditSetContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mEditSetContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mEditSetContainer.setVisibility(View.GONE);
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mEditSetContainer.getVisibility() == View.VISIBLE) {
            hideEditSetFragment();
        }
        mPerformedExercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        menuCreate.close(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveUI();
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
            if (mWorkout == null) {
                mWorkout = new Workout();
                WorkoutLab.get(getContext()).addWorkout(mWorkout);
                Log.i("BLABLABLA", String.valueOf(mWorkout == null));
                if (mListener != null) {
                    mListener.onWorkoutCreated(mWorkout.getId());
                }
            }

            Log.i("BLABLABLA", String.valueOf(mWorkout == null));
            List<PerformedExercise> newPerformedExercises = PerformedExercise.createFromExercises(SelectableExerciseListFragment.getSelectedExercises(data), mWorkout.getId(), new Date());
            PerformedExerciseLab.get(getContext()).addPerformedExercises(newPerformedExercises);
            mWorkout.addPerformedExercises(newPerformedExercises);
            WorkoutLab.get(getContext()).updateWorkout(mWorkout);


            updateUI();
        }
        else if (requestCode == RequestCodes.REQUEST_CODE_EDIT_PERFORMED_EXERCISE) {
            if (data == null) {
                return;
            }
        }

    }

    @Override
    public void onSetChanged(PerformedSet performedSet) {
        mAdapter.updateSelectedSet(performedSet);
    }


    // Update all variables that hold model data that can be changed in other fragments/activities. Will be called at onResume()
    public void updateUI() {
        List<PerformedExercise> performedExercises = new ArrayList<>();


        if (mWorkout != null) {
            performedExercises = PerformedExerciseLab.get(getContext()).getPerformedExercises(mWorkout.getPerformedExercises());
            if (mPerformedExercisesRecyclerView.getAdapter() == null) {
                mAdapter = new PerformedExerciseAdapter(performedExercises, getContext());
                mPerformedExercisesRecyclerView.setAdapter(mAdapter);
                mAdapter.setPerformedExerciseListEventListener(new PerformedExerciseAdapter.OnPerformedExerciseListEventListener() {
                    @Override
                    public void onExerciseClicked(PerformedExercise performedExercise) {
                        startActivityForResult(PerformedExerciseEditActivity.newIntent(getContext(), performedExercise.getmId()), RequestCodes.REQUEST_CODE_EDIT_PERFORMED_EXERCISE);
                    }

                    @Override
                    public void onSetClicked(PerformedSet performedSet, Boolean sameSet) {
                        if (sameSet) {
                            hideEditSetFragment();
                        } else {
                            if (mEditSetContainer.getVisibility() == View.GONE || mEditSetContainer.getVisibility() == View.INVISIBLE) {
                                showEditSetFragment();
                            }
                            mEditSetFragment.setPerformedSet(performedSet);
                        }
                    }
                });
                SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(mAdapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
                touchHelper.attachToRecyclerView(mPerformedExercisesRecyclerView);
            } else {
                mAdapter.setPerformedExercises(performedExercises);
                mAdapter.notifyDataSetChanged();
            }
        }
        else {
            if (mEditSetContainer.getVisibility() == View.VISIBLE) {
                hideEditSetFragment();
            }
        }

    }

    // Updates the database to match any changed data.
    public void saveUI() {
        if (mWorkout != null) {
            PerformedExerciseLab.get(getContext()).updatePerformedExercises(mAdapter.getPerformedExercises());
            mWorkout.setPerformedExercises(PerformedExercise.toUUIDs(((PerformedExerciseAdapter) mPerformedExercisesRecyclerView.getAdapter()).getPerformedExercises()));

            WorkoutLab.get(getContext()).updateWorkout(mWorkout);
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



    private void hideEditSetFragment() {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,  // fromYDelta
                mEditSetContainer.getHeight());                // toYDelta
        animate.setDuration(500);
        // animate.setFillAfter(true);

        TranslateAnimation animate1 = new TranslateAnimation(0,0,mBottomNavigationView.getHeight(),0);
        animate1.setDuration(500);
        // animate1.setFillAfter(true);
        mBottomNavigationView.startAnimation(animate1);
        mEditSetContainer.startAnimation(animate);
        mBottomNavigationView.setVisibility(View.VISIBLE);
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

        TranslateAnimation animate1 = new TranslateAnimation(0,0,0,mBottomNavigationView.getHeight());
        animate1.setDuration(500);
        animate1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mBottomNavigationView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                1f, 0.8f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(500);
        animate1.setFillAfter(true);
        mBottomNavigationView.setVisibility(View.GONE);
        mEditSetContainer.setVisibility(View.VISIBLE);
        mBottomNavigationView.startAnimation(animate1);
        mEditSetContainer.startAnimation(animate);
        //mPerformedExercisesRecyclerView.startAnimation(anim);
    }

    public void displayRecyclerView(boolean display) {
        if (display) {
            mPerformedExercisesRecyclerView.setVisibility(View.VISIBLE);
        }
        else {
            mPerformedExercisesRecyclerView.setVisibility(View.GONE);
        }
    }

    public void setListener(EditWorkoutFragmentEventListener listener) {
        mListener = listener;
    }

    public void setWorkout(UUID workout) {
        if (workout == null) {
            mWorkout = null;
        }
        else {
            mWorkout = WorkoutLab.get(getContext()).getWorkouts(workout);
        }
        updateUI();
    }
}
