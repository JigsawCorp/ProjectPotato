package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.R;

public class PlanFragment extends Fragment {
    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_plan, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Plan");
    }
}
