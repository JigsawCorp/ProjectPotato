package com.jigsawcorp.android.jigsaw.Fragments.tab_programs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.CheckboxListDialogFragment;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.EditTextDialog;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.NumberPickerDialog;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.RadioButtonListDialogFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramWorkout;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsChoicePicker;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsEditText;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsMultipleChoicePicker;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsSwitch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class EditProgramWorkoutFragment extends Fragment {
    private ProgramWorkout mProgramWorkout;
    private SettingsEditText mNameEditText, mDescriptionEditText;
    private SettingsMultipleChoicePicker mBodyPartsTrainedChoicePicker;

    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_program_workout, container, false);


        mNameEditText = (SettingsEditText) v.findViewById(R.id.fragment_edit_program_workout_edit_text_name);
        mDescriptionEditText = (SettingsEditText) v.findViewById(R.id.fragment_edit_program_workout_edit_text_description);
        mBodyPartsTrainedChoicePicker = (SettingsMultipleChoicePicker) v.findViewById(R.id.fragment_edit_program_workout_multiple_choice_body_parts);


        setNameText();
        setDescriptionText();

        mNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditProgramFragment", "mNameEditText.onClick(), mProgram.getName() = " + mProgramWorkout.getName());
                FragmentManager manager = getFragmentManager();
                EditTextDialog fragment = EditTextDialog.newInstance("Program Name", mProgramWorkout.getName());
                fragment.setConfirmedTextListener(new EditTextDialog.EditTextDialogListener() {
                    @Override
                    public void onTextConfirmed(String text) {
                        mProgramWorkout.setName(text);
                        setNameText();
                    }
                });
                fragment.show(manager, "ProgramNameDialog");
            }
        });

        final ArrayList<String> muscleGroupsStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.muscle_groups_array)));

        mBodyPartsTrainedChoicePicker.setChoices(mProgramWorkout.getWorkedMuscleGroupsAsStrings());
        mBodyPartsTrainedChoicePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                CheckboxListDialogFragment fragment = CheckboxListDialogFragment.newInstance("Muscle Groups Trained",muscleGroupsStringArray, mProgramWorkout.getSelectedMuscleGroups());
                fragment.setConfirmedChoiceListener(new CheckboxListDialogFragment.CheckboxListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(boolean[] selections) {
                        mProgramWorkout.setSelectedMuscleGroups(selections);
                        mBodyPartsTrainedChoicePicker.setChoices(mProgramWorkout.getWorkedMuscleGroupsAsStrings());

                    }
                });
                fragment.show(manager, "WorkoutMuscleGroupsDialog");
            }
        });

        mDescriptionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                EditTextDialog fragment = EditTextDialog.newInstance("Program Description", mProgramWorkout.getDescription());
                fragment.setConfirmedTextListener(new EditTextDialog.EditTextDialogListener() {
                    @Override
                    public void onTextConfirmed(String text) {
                        mProgramWorkout.setDescription(text);
                        setDescriptionText();
                    }
                });
                fragment.show(manager, "ProgramDescriptionDialog");
            }
        });




        return v;
    }

    private void setNameText() {
        if (mProgramWorkout.getName().isEmpty()) {
            mNameEditText.setDescription(getContext().getResources().getString(R.string.empty_program_name_filler));
        }
        else {
            mNameEditText.setDescription(mProgramWorkout.getName());
        }
    }

    private void setDescriptionText() {
        if (mProgramWorkout.getDescription().isEmpty()) {
            mDescriptionEditText.setDescription(getContext().getResources().getString(R.string.empty_program_description_filler));
        }
        else {
            mDescriptionEditText.setDescription(mProgramWorkout.getDescription());
        }
    }

    public void setProgramWorkout(ProgramWorkout program) {
        mProgramWorkout = program;
    }

    public ProgramWorkout getProgram() {
        return mProgramWorkout;
    }

    private void setRadioButtonListsValues() {
    }

    public boolean verifyFields() {
        /*
        if (mNameEditText.getDescription().equals(getResources().getString(R.string.empty_program_name_filler)) || mNameEditText.getDescription().isEmpty()) {
            Toast.makeText(getContext(), "Your program must have a name!", Toast.LENGTH_LONG).show();
            return false;
        }
        */
        return true;
    }
}
