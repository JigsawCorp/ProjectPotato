package com.jigsawcorp.android.jigsaw.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Activities.CreateProgramActivity;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.Routine;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.ProgramsAdapter;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class ProgramsFragment extends Fragment {
    private RecyclerView mProgramsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_programs, container, false);
        List<Program> someRoutines = new ArrayList<>();

        mProgramsRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_programs_recycler_view);
        mProgramsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProgramsRecyclerView.setAdapter(new ProgramsAdapter(ProgramLab.get(getContext()).getPrograms(), getContext()));


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_programs_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.menu_program_fragment_create_program:
                Intent intent = new Intent(getActivity(), CreateProgramActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Programs");
    }
}
