package com.jigsawcorp.android.jigsaw.Fragments.tab_current_workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.PerformedExercise.PerformedExerciseLab;
import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Fragments.shared_tabs.EditWorkoutFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedExercise;
import com.jigsawcorp.android.jigsaw.Model.User;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.PerformedExerciseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CurrentWorkoutFragment extends Fragment {

    private EditWorkoutFragment mEditWorkoutFragment;
    private User mUser;
    private TextView mWarningTextView;
    private MenuItem mFinishWorkoutButton;
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

        mEditWorkoutFragment = EditWorkoutFragment.newInstance(mUser.getActiveWorkout());
        mEditWorkoutFragment.setListener(new EditWorkoutFragment.EditWorkoutFragmentEventListener() {
            @Override
            public void onPerformedExerciseDeleted(PerformedExercise performedExercise) {

            }

            @Override
            public void onWorkoutCreated(UUID workout) {
                mUser.setActiveWorkout(workout);
                saveUI();
                updateUI();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_current_workout_container_edit_workout, mEditWorkoutFragment, "EditWorkoutFragment").commit();

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Current Workout");
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_current_workout_fragment, menu);
        mFinishWorkoutButton = (MenuItem) menu.findItem(R.id.menu_current_workout_fragment_finish_workout);
        if (mUser.getActiveWorkout() == null) {
            mFinishWorkoutButton.setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_current_workout_fragment_finish_workout:
                if (mUser.getActiveWorkout() == null) {
                }
                else {
                    finishWorkout();
                }
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
    }

    private void enableNoActiveWorkoutWarning(boolean needWarning) {
        if (!needWarning) {
            mWarningTextView.setVisibility(View.GONE);
            mEditWorkoutFragment.displayRecyclerView(true);
        }
        else {
            mWarningTextView.setVisibility(View.VISIBLE);
            mEditWorkoutFragment.displayRecyclerView(false);
        }
    }

    // Update all variables that hold model data that can be changed in other fragments/activities. Will be called at onResume()
    public void updateUI() {
        mUser = UserLab.get(getContext()).getUser();
        List<PerformedExercise> performedExercises = new ArrayList<>();

        if (mUser.getActiveWorkout() == null) {
            mEditWorkoutFragment.setWorkout(null);
            if (mFinishWorkoutButton != null) {
                mFinishWorkoutButton.setVisible(false);
            }
            enableNoActiveWorkoutWarning(true);
        }
        else {
            mEditWorkoutFragment.setWorkout(mUser.getActiveWorkout());
            if (mFinishWorkoutButton != null) {
                mFinishWorkoutButton.setVisible(true);
            }
            enableNoActiveWorkoutWarning(false);
        }
    }

    // Updates the database to match any changed data.
    private void saveUI() {
        UserLab.get(getContext()).updateUser(mUser);
    }

    private void finishWorkout() {
        mEditWorkoutFragment.saveUI();
        mUser.setActiveWorkout(null);
        mEditWorkoutFragment.setWorkout(null);
        UserLab.get(getContext()).updateUser(mUser);
        updateUI();

    }

}
