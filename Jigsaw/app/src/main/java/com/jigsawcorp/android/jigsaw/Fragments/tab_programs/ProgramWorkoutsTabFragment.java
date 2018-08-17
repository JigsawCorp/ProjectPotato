package com.jigsawcorp.android.jigsaw.Fragments.tab_programs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.Activities.CreateProgramWorkoutActivity;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Database.ProgramWorkout.ProgramWorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.ProgramWorkoutAdapter;

import java.util.UUID;

public class ProgramWorkoutsTabFragment extends Fragment {
    private static final String EXTRA_PROGRAM_ID = "program_id";
    private RecyclerView mProgramWorkoutsRecyclerView;
    private Program mProgram;

    public static ProgramWorkoutsTabFragment newInstance(UUID programId) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_PROGRAM_ID, programId.toString());
        ProgramWorkoutsTabFragment fragment = new ProgramWorkoutsTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_program_workouts_tab, container, false);
        mProgram = ProgramLab.get(getContext()).getProgram(UUID.fromString(getArguments().getString(EXTRA_PROGRAM_ID)));
        mProgramWorkoutsRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_program_workouts_tab_recycler_view);
        mProgramWorkoutsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgramWorkoutsRecyclerView.setAdapter(new ProgramWorkoutAdapter(ProgramWorkoutLab.get(getContext()).getProgramWorkouts(mProgram.getProgramWorkouts()), getContext()));
        ((ProgramWorkoutAdapter) mProgramWorkoutsRecyclerView.getAdapter()).setEventListener(new ProgramWorkoutAdapter.ProgramWorkoutAdapterEventListener() {
            @Override
            public void onEditProgramWorkoutClicked(ProgramWorkout programWorkout) {
                startActivityForResult(CreateProgramWorkoutActivity.newIntent(getContext(),mProgram.getId(), programWorkout.getId()), RequestCodes.REQUEST_CODE_EDIT_PROGRAM_WORKOUT);
            }

            @Override
            public void onDeleteProgramWorkoutClicked(ProgramWorkout programWorkout, int position) {
                mProgram.removeProgramWorkout(programWorkout);
                ProgramWorkoutLab.get(getContext()).removeProgramWorkout(programWorkout);
                ProgramLab.get(getContext()).updateProgram(mProgram);
                ((ProgramWorkoutAdapter) mProgramWorkoutsRecyclerView.getAdapter()).removeProgramWorkout(programWorkout, position);
            }

            @Override
            public void onProgramWorkoutClicked(ProgramWorkout programWorkout) {

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateReyclerViewData() {
        Log.i("updateReyclerViewData", "size is " + ProgramWorkoutLab.get(getContext()).getProgramWorkouts(mProgram.getProgramWorkouts()).size());
        mProgram = ProgramLab.get(getContext()).getProgram(mProgram.getId());
        ((ProgramWorkoutAdapter)mProgramWorkoutsRecyclerView.getAdapter()).setPrograms(ProgramWorkoutLab.get(getContext()).getProgramWorkouts(mProgram.getProgramWorkouts()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == RequestCodes.REQUEST_CODE_EDIT_PROGRAM_WORKOUT) {
            if (data == null) {
                return;
            }
            else {
                if (CreateProgramWorkoutActivity.wasProgramWorkoutModified(data)) {
                    updateReyclerViewData();
                }
            }
        }
    }
}
