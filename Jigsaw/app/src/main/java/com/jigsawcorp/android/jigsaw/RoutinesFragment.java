package com.jigsawcorp.android.jigsaw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class RoutinesFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        List<Routine> someRoutines = new ArrayList<>();
        someRoutines.add(new Routine("Routine 1"));
        someRoutines.add(new Routine("Routine 2"));

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.routines_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCrimeRecyclerView.setAdapter(new RoutineAdapter(someRoutines));


        return view;
    }

    private class RoutineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected Routine mRoutine;

        protected TextView mTitleTextView;
       // protected TextView mDateTextView;
        //private ImageView mSolvedImageView;


        public RoutineHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_routine_item, parent, false));
            itemView.setOnClickListener(this);

           mTitleTextView = (TextView) itemView.findViewById(R.id.routine_item_title);
           // mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            //mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }


        public void bind(Routine routine){
            mRoutine = routine;
            mTitleTextView.setText(routine.getTitle());
            //mDateTextView.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getDate()));
            //mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            //code to start a routine activity;
        }
    }

    private class RoutineAdapter extends RecyclerView.Adapter<RoutineHolder> {

        private List<Routine> mRoutines;

        public RoutineAdapter(List<Routine> routines) {
            mRoutines = routines;
        }

        @Override
        public RoutineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new RoutineHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RoutineHolder holder, int position) {
            Routine routine = mRoutines.get(position);
            holder.bind(routine);
        }

        @Override
        public int getItemCount() {
            return mRoutines.size();
        }

        public void setCrimes(List<Routine> routines) {
            mRoutines = routines;
        }

    }
}
