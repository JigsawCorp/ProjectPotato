package com.jigsawcorp.android.jigsaw.Fragments;

import android.app.Activity;
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

import com.jigsawcorp.android.jigsaw.Activities.CreateProgramActivity;
import com.jigsawcorp.android.jigsaw.Activities.EditProgramActivity;
import com.jigsawcorp.android.jigsaw.Database.Program.ProgramLab;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.Util.RequestCodes;
import com.jigsawcorp.android.jigsaw.View.RecyclerView.ProgramsAdapter;

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
        ((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setEventListener(new ProgramsAdapter.ProgramsAdatperEventListener() {
            @Override
            public void onEditProgramClicked(Program program) {
                startActivityForResult(EditProgramActivity.newIntent(getContext(), program.getId()), RequestCodes.REQUEST_CODE_EDIT_PROGRAM);
            }

            @Override
            public void onDeleteProgramClicked(Program program) {
                ProgramLab.get(getContext()).removeProgram(program);
                ((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setPrograms(ProgramLab.get(getContext()).getPrograms());
            }
        });


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
                startActivityForResult(intent, RequestCodes.REQUEST_CODE_CREATE_PROGRAM);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == RequestCodes.REQUEST_CODE_CREATE_PROGRAM) {
            if (data == null) {
                return;
            }
            else {
                if (CreateProgramActivity.wasProgramCreated(data)) {
                    ((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setPrograms(ProgramLab.get(getContext()).getPrograms());
                }
            }
        }
        else if (requestCode == RequestCodes.REQUEST_CODE_EDIT_PROGRAM) {
            if (data == null) {
                return;
            }
            else {
                if (EditProgramActivity.wasProgramModified(data)) {
                    ((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setPrograms(ProgramLab.get(getContext()).getPrograms());
                }
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Programs");
       //((ProgramsAdapter) mProgramsRecyclerView.getAdapter()).setPrograms(ProgramLab.get(getContext()).getPrograms());
    }
}
