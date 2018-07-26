package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.Exercise.ExerciseLab;
import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.R;

import java.util.List;

public class ExerciseListFragment extends Fragment {
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
        mExerciseRecyclerView.setAdapter(new ExerciseAdapter(ExerciseLab.get(getContext()).getExercises()));


        return v;
    }

    public void updateAdapterExercises(List<Exercise> exercises) {
        ((ExerciseAdapter) mExerciseRecyclerView.getAdapter()).setExercises(exercises);
    }

    private class ExerciseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected Exercise mExercise;

        protected TextView mTitleTextView;
        // protected TextView mDateTextView;
        //private ImageView mSolvedImageView;


        public ExerciseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_exercise, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_exercise_exercise_title);
            // mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            //mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }


        public void bind(Exercise exercise){
            mExercise = exercise;
            mTitleTextView.setText(exercise.getName());
            //mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
            //mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            //code to start a routine activity;
        }
    }

    private class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder> {
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

    }
}

