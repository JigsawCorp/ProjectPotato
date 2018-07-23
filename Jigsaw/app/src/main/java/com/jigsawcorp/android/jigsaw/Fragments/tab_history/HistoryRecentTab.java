package com.jigsawcorp.android.jigsaw.Fragments.tab_history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Database.User.UserLab;
import com.jigsawcorp.android.jigsaw.Database.Workout.WorkoutLab;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;

import java.util.List;

public class HistoryRecentTab extends Fragment {
    private TextView mTextView;
    private static final String TAG = "HistoryRecentTab";
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        Log.i(TAG,"onCreateView");
        View v = inflater.inflate(R.layout.fragment_history_recent_tab, container, false);
        mTextView = v.findViewById(R.id.fragment_history_recent_tab_text_view);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        mTextView.setText("");
        List<Workout> workoutList = WorkoutLab.get(getContext()).getWorkouts();
        mTextView.setText("Workouts are: \n");
        for (int i = 0; i < workoutList.size(); ++i) {
            mTextView.append("Workout id: " + workoutList.get(i).getId().toString() + ", Workout date: " + workoutList.get(i).getStartDate().toString() + "\n");
        }
    }
}
