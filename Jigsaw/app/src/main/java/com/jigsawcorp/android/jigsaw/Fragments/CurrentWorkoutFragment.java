package com.jigsawcorp.android.jigsaw.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.Activities.ExerciseListActivity;
import com.jigsawcorp.android.jigsaw.Activities.PerformedExerciseEditActivity;
import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.PerformedExerciseAdapter;
import com.jigsawcorp.android.jigsaw.View.SetHolder;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentWorkoutFragment extends Fragment {
    private String TAG = "CurrentWorkoutFragment";
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
    private PerformedExerciseAdapter mAdapter;

    public interface Callbacks {
        public void onPerformedExerciseDeleted(PerformedExercise performedExercise);
    }

    // Model
    private User mUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        getChildFragmentManager().beginTransaction().replace(R.id.fragment_current_workout_edit_set_container, new EditSetFragment(), "EditSetFragment").commit();





        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Current Workout");
        updateUI();
        menuCreate.close(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_current_workout_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_current_workout_fragment_finish_workout:
                finishWorkout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                UserLab.get(getContext()).updateUser(mUser);
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
        List<PerformedExercise> performedExercises = new ArrayList<>();

        if (mUser.getActiveWorkout() == null) {
            mWorkout = null;
        }
        else {
            mWorkout = WorkoutLab.get(getContext()).getWorkout(mUser.getActiveWorkout());
            performedExercises = PerformedExerciseLab.get(getContext()).getPerformedExercises(mWorkout.getPerformedExercises());

        }
        if (mAdapter == null) {
            mAdapter = new PerformedExerciseAdapter(performedExercises, getContext());
            mPerformedExercisesRecyclerView.setAdapter(mAdapter);
            mAdapter.setPerformedExerciseListEventListener(new OnPerformedExerciseListEventListener() {
                @Override
                public void onExerciseClicked(PerformedExercise performedExercise) {
                    startActivityForResult(PerformedExerciseEditActivity.newIntent(getContext(), performedExercise.getmId()), RequestCodes.REQUEST_CODE_EDIT_PERFORMED_EXERCISE);
                }
            });
            SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(mAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
            touchHelper.attachToRecyclerView(mPerformedExercisesRecyclerView);
        }
        else {
            mAdapter.setPerformedExercises(performedExercises);
            mAdapter.notifyDataSetChanged();
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

    private void finishWorkout() {
        Log.i(TAG, "finishWorkout");
        mWorkout.setPerformedExercises(PerformedExercise.toUUIDs(((PerformedExerciseAdapter) mPerformedExercisesRecyclerView.getAdapter()).getPerformedExercises()));
        WorkoutLab.get(getContext()).updateWorkout(mWorkout);
        mWorkout = null;
        mUser.setActiveWorkout(null);
        UserLab.get(getContext()).updateUser(mUser);
        updateUI();
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
    }


    private void hideEditSetFragment() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        EditSetFragment frag = (EditSetFragment) getChildFragmentManager().findFragmentByTag("EditSetFragment");
        ft.hide(frag).commit();
    }

    private void showEditSetFragment() {
        getChildFragmentManager().beginTransaction().show((EditSetFragment) getChildFragmentManager().findFragmentByTag("EditSetFragment")).commit();

    }
}
