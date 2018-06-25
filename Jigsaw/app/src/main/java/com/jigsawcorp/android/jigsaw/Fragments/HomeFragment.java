package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.Model.Exercise;
import com.jigsawcorp.android.jigsaw.R;

import java.util.List;

public class HomeFragment extends Fragment {
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        List<Exercise> exercises = Exercise.getDefaultExercises(getActivity());
        for (int i = 0; i < exercises.size(); ++i) {
            Log.i("HomeFragment", "Name: " + exercises.get(i).getName() + ", Category: " + exercises.get(i).getCategory() + ", ID: " + exercises.get(i).getId());
        }
        return v;
    }
}
