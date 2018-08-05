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
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsRadioButtonList;

import java.util.ArrayList;
import java.util.Arrays;

public class EditProgramFragment extends Fragment {

    private Program mProgram;
    private SettingsRadioButtonList mDaysPerWeekRadioButtonList, mTrainingTypeRadioButtonList, mProgramLengthRadioButtonList;

    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_program, container, false);

        mTrainingTypeRadioButtonList = (SettingsRadioButtonList) v.findViewById(R.id.fragment_edit_program_RadioButtonList_training_type);
        mDaysPerWeekRadioButtonList = (SettingsRadioButtonList) v.findViewById(R.id.fragment_edit_program_RadioButtonList_DaysPerWeek);
        mProgramLengthRadioButtonList = (SettingsRadioButtonList) v.findViewById(R.id.fragment_edit_program_RadioButtonList_program_length);

        final ArrayList<String> trainingTypeStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_training_types)));
        mTrainingTypeRadioButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Training Type", trainingTypeStringArray, 0);
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                       mTrainingTypeRadioButtonList.setChoice(trainingTypeStringArray.get(position));
                    }
                });
                fragment.show(manager, "TrainingTypesDialog");
            }
        });

        //mDaysPerWeekSpinner = (Spinner) v.findViewById(R.id.activity_create_program_spinner_days_per_week);
        final ArrayList<String> daysPerWeekStringArray = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", getResources().getString(R.string.variable)));
        mDaysPerWeekRadioButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Days Per Week", daysPerWeekStringArray, 0);
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                        mDaysPerWeekRadioButtonList.setChoice(daysPerWeekStringArray.get(position));
                    }
                });
                fragment.show(manager, "DaysPerWeekDialog");
            }
        });

        final ArrayList<String> programLengthStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_program_length)));
        mProgramLengthRadioButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Program Length", programLengthStringArray, 0);
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                        mProgramLengthRadioButtonList.setChoice(programLengthStringArray.get(position));
                    }
                });
                fragment.show(manager, "ProgramLengthDialog");
            }
        });




        mProgram = new Program();
        return v;
    }


    public void setProgram(Program program) {
        mProgram = program;
    }

    public Program getProgram() {
        return mProgram;
    }

    private void setRadioButtonListsValues() {
    }
 }
