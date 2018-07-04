package com.jigsawcorp.android.jigsaw.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Activities.ExerciseListActivity;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;

import java.util.Date;
import java.util.List;

public class CurrentWorkoutFragment extends Fragment {
    // View
    private FloatingActionMenu menuCreate;
    private FloatingActionButton fabAddRoutine;
    private FloatingActionButton fabAddExercise;
    private FloatingActionButton fab3;
    private RecyclerView mPerformedExercisesRecyclerView;
    private Workout mWorkout;

    private TextView mWarningTextView;


    private BottomNavigationView mBottomNavigationView;
    private MenuItem mPrevMenuItem;
    // Controller
    private Callbacks mCallbacks;

    public interface Callbacks {
        public void onPerformedExerciseDeleted(PerformedExercise performedExercise);
    }

    // Model
    private User mUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        mUser = UserLab.get(getContext()).getUser();

        mWarningTextView = v.findViewById(R.id.fragment_current_workout_warning_text_view);
        enableNoAcriveWorkoutWarning(true);


        menuCreate = (FloatingActionMenu) v.findViewById(R.id.action_menu_current_workout_add);

        fabAddRoutine = (FloatingActionButton) v.findViewById(R.id.fab_add_routine);

        fabAddExercise = (FloatingActionButton) v.findViewById(R.id.fab_add_exercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(ExerciseListActivity.newIntent(getContext(), true), RequestCodes.REQUEST_CODE_ADD_EXERCISE);
            }
        });
        fab3 = (FloatingActionButton) v.findViewById(R.id.fab12);

        mBottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
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

        mPerformedExercisesRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_current_workout_recycler_view);
        mPerformedExercisesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));




        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Current Workout");
        updateUI();
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
            }
                WorkoutLab.get(getContext()).addWorkout(mWorkout);
                mUser.setActiveWorkout(mWorkout.getId());
                List<PerformedExercise> newPerformedExercises = PerformedExercise.createFromExercises(SelectableExerciseListFragment.getSelectedExercises(data), mWorkout.getId(), new Date());
                PerformedExerciseLab.get(getContext()).addPerformedExercises(newPerformedExercises);
                mWorkout.addPerformedExercises(newPerformedExercises);



        }
        saveUI();
        updateUI();
    }

    private void enableNoAcriveWorkoutWarning(boolean needWarning) {
        if (!needWarning) {
            mWarningTextView.setVisibility(View.GONE);
        }
        else {
            mWarningTextView.setVisibility(View.VISIBLE);
        }
    }

    // Update all variables that hold model data that can be changed in other fragments/activities. Will be called at onResume()
    public void updateUI() {
        mUser = UserLab.get(getContext()).getUser();
        if (mUser.getActiveWorkout() == null) {
            mWorkout = null;
        }
        else {
            mWorkout = WorkoutLab.get(getContext()).getWorkout(mUser.getActiveWorkout());
            PerformedExerciseAdapter adapter = new PerformedExerciseAdapter(PerformedExerciseLab.get(getContext()).getPerformedExercises(mWorkout.getPerformedExercises()));
            mPerformedExercisesRecyclerView.setAdapter(adapter);
            SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
            touchHelper.attachToRecyclerView(mPerformedExercisesRecyclerView);


        }
        enableNoAcriveWorkoutWarning(mWorkout == null);

    }

    // Updates the database to match any changed data.
    private void saveUI() {
        UserLab.get(getContext()).updateUser(mUser);
        if (mWorkout != null) {
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
    }

    public interface ActionCompletionContract {
        void onViewMoved(int oldPosition, int newPosition);
        }

    private class PerformedExerciseAdapter extends RecyclerView.Adapter<PerformedExerciseAdapter.PerformedExerciseHolder> implements ActionCompletionContract{
        private List<PerformedExercise> mPerformedExercises;

        public PerformedExerciseAdapter(List<PerformedExercise> performedExercises) {
            mPerformedExercises = performedExercises;
        }

        @Override
        public PerformedExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PerformedExerciseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PerformedExerciseHolder holder, int position) {
            PerformedExercise performedExercise = mPerformedExercises.get(position);
            holder.bind(performedExercise);
        }

        @Override
        public int getItemCount() {
            return mPerformedExercises.size();
        }

        @Override
        public void onViewMoved(int oldPosition, int newPosition) {
            PerformedExercise performedExercise = mPerformedExercises.get(oldPosition);
            mPerformedExercises.remove(oldPosition);
            mPerformedExercises.add(newPosition, performedExercise);
            notifyItemMoved(oldPosition, newPosition);
        }

        public void setPerformedExercises(List<PerformedExercise> exercises) {
            mPerformedExercises = exercises;
        }

        public List<PerformedExercise> getPerformedExercises() {
            return mPerformedExercises;
        }

        class PerformedExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected PerformedExercise mPerformedExercise;

            protected TextView mTitleTextView;
            // protected TextView mDateTextView;
            //private ImageView mSolvedImageView;


            public PerformedExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_performed_exercise, parent, false));
                itemView.setOnClickListener(this);

                mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_performed_exercise_title);
                // mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
                //mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            }


            public void bind(PerformedExercise performedExercise){
                mPerformedExercise = performedExercise;
                mTitleTextView.setText(ExerciseLab.get(getContext()).getExercise(performedExercise.getExercise()).getName());
                //mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
                //mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onClick(View view) {
                //code to start a routine activity;
            }
        }
    }
}
