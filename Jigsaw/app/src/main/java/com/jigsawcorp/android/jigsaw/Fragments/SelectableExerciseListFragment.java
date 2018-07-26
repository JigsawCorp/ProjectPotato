package com.jigsawcorp.android.jigsaw.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.security.auth.callback.CallbackHandler;

public class SelectableExerciseListFragment extends Fragment {
    private static final String EXTRA_LIST_EXERCISES = "com.jigsawcorp.android.jigsaw.extra_list_exercises";
    private RecyclerView mExerciseRecyclerView;
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        mExerciseRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_exercise_list_recycler);
        mExerciseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExerciseRecyclerView.setAdapter(new SelectableExerciseListFragment.ExerciseAdapter(ExerciseLab.get(getContext()).getExercises()));

        return v;
    }

    public void updateAdapterExercises(List<Exercise> exercises) {
        ((ExerciseAdapter) mExerciseRecyclerView.getAdapter()).setExercises(exercises);
    }


    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseHolder> {
        public Set<UUID> mSelectedExercises = new HashSet<>();
        private List<Exercise> mExercises;
        public ExerciseAdapter(List<Exercise> exercises) {
            mExercises = exercises;
        }

        @Override
        public ExerciseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExerciseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ExerciseHolder holder, int position) {
            Exercise exercise = mExercises.get(position);
            holder.bind(exercise);
        }

        @Override
        public int getItemCount() {
            return mExercises.size();
        }

        public void setExercises(List<Exercise> exercises) {
            mExercises = exercises;
            notifyDataSetChanged();
        }


        class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            protected Exercise mExercise;
            protected TextView mTitleTextView;
            protected CheckBox mCheckBox;
            // protected TextView mDateTextView;
            //private ImageView mSolvedImageView;


            public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.list_item_exercise_checkable, parent, false));
                itemView.setOnClickListener(this);

                mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_exercise_checkable_title);
                mCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_exercise_checkable_checkbox);
                // mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
                //mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            }


            public void bind(Exercise exercise){
                mExercise = exercise;
                mTitleTextView.setText(exercise.getName());
                if (!mSelectedExercises.contains(exercise.getId())) {
                    mCheckBox.setChecked(false);}
                else {
                    mCheckBox.setChecked(true);
                }
            }

            @Override
            public void onClick(View view) {
                if (!mSelectedExercises.contains(mExercise.getId())) {
                    mCheckBox.setChecked(true);
                    mSelectedExercises.add(mExercise.getId());
                    if (!mSelectedExercises.isEmpty()) {
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
                        }
                }
                else  {
                    mCheckBox.setChecked(false);
                    mSelectedExercises.remove(mExercise.getId());
                    if (mSelectedExercises.isEmpty()) {
                        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    }
                }
            }
        }

    }

    public void sendExercises() {
        setChosenExercises(((ExerciseAdapter) mExerciseRecyclerView.getAdapter()).mSelectedExercises);
    }

    private void setChosenExercises(Set<UUID> exercises) {
        ArrayList<String> uuidStringList = new ArrayList<>();
        for (UUID exercise:exercises) {
            uuidStringList.add(exercise.toString());
        }
        Intent data = new Intent();
        data.putStringArrayListExtra(EXTRA_LIST_EXERCISES, uuidStringList);
        getActivity().setResult(Activity.RESULT_OK, data);
        getActivity().finish();
    }

    public static List<UUID> getSelectedExercises(Intent result) {
        ArrayList<String> uuidListString = result.getStringArrayListExtra(EXTRA_LIST_EXERCISES);
        ArrayList<UUID> uuidList = new ArrayList<>();
        for (String exercise: uuidListString) {
            uuidList.add(UUID.fromString(exercise));
        }
        return uuidList;
    }
    



}
