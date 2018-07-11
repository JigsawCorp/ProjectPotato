package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jigsawcorp.android.jigsaw.R;

public class EditSetFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_set, container, false);

        return v;
    }
}
