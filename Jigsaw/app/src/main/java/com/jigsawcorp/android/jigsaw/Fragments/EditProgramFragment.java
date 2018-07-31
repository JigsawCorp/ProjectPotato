package com.jigsawcorp.android.jigsaw.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.RadioButtonListDialogFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;

import java.util.ArrayList;
import java.util.Arrays;

public class EditProgramFragment extends Fragment {

    private Spinner mTrainingTypesSpinner, mDaysPerWeekSpinner;
    private Program mProgram;

    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_program, container, false);

        mTrainingTypesSpinner = (Spinner) v.findViewById(R.id.activity_create_program_spinner_training_types);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.array_training_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTrainingTypesSpinner.setAdapter(adapter);
        mTrainingTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                switch (parent.getId()) {
                    case R.id.activity_create_program_spinner_training_types:
                        mProgram.setTrainingType(Program.TrainingTypes.values()[position]);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //mDaysPerWeekSpinner = (Spinner) v.findViewById(R.id.activity_create_program_spinner_days_per_week);
        final ArrayList<String> daysPerWeekStringArray = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", getResources().getString(R.string.variable)));
        final TextView mDaysPerWeekTextView = (TextView) v.findViewById(R.id.fragment_edit_program_textView_days_per_week);
        mDaysPerWeekTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Days Per Week", daysPerWeekStringArray, 0);
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                        mDaysPerWeekTextView.setText(daysPerWeekStringArray.get(position));
                    }
                });
                fragment.show(manager, "DaysPerWeekDialog");
            }
        });
        /*
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, daysPerWeekStringArray);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDaysPerWeekSpinner.setAdapter(adapter2);
        mDaysPerWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position > 6) {
                    mProgram.setDaysPerWeek(-1);
                }
                else {
                    mProgram.setDaysPerWeek(Integer.parseInt(daysPerWeekStringArray[position]));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */

        mProgram = new Program();
        return v;
    }


    public void setProgram(Program program) {
        mProgram = program;
    }

    public Program getProgram() {
        return mProgram;
    }
}
