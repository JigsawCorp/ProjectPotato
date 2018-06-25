package com.jigsawcorp.android.jigsaw.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.jigsawcorp.android.jigsaw.R;

public class CurrentWorkoutFragment extends Fragment {
    private FloatingActionMenu menuCreate;
    private FloatingActionButton fabAddRoutine;
    private FloatingActionButton fabAddExercise;
    private FloatingActionButton fab3;

    private BottomNavigationView mBottomNavigationView;
    private MenuItem mPrevMenuItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_workout, container, false);

        menuCreate = (FloatingActionMenu) v.findViewById(R.id.action_menu_current_workout_add);

        fabAddRoutine = (FloatingActionButton) v.findViewById(R.id.fab_add_routine);

        fabAddExercise = (FloatingActionButton) v.findViewById(R.id.fab_add_exercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fab3 = (FloatingActionButton) v.findViewById(R.id.fab12);

        mBottomNavigationView = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_current_workout:
                        break;
                    case R.id.bottom_navigation_workout_log:
                        break;
                    case R.id.bottom_navigation_routines:
                        break;
                    case R.id.bottom_navigation_progress:
                        break;
                }
                return true;
            }
        });


        return v;
    }
}
