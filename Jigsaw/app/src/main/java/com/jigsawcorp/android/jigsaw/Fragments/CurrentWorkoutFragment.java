package com.jigsawcorp.android.jigsaw.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.R;

public class CurrentWorkoutFragment extends Fragment {
    private FloatingActionMenu menuCreate;
    private FloatingActionButton fab_add_routine;
    private FloatingActionButton fab_add_exercise;
    private FloatingActionButton fab3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        menuCreate = (FloatingActionMenu) v.findViewById(R.id.menu_yellow);
        fab_add_routine = (FloatingActionButton) v.findViewById(R.id.fab12);
        fab_add_routine = (FloatingActionButton) v.findViewById(R.id.fab22);
        fab3 = (FloatingActionButton) v.findViewById(R.id.fab32);


        return v;
    }
}
