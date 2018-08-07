package com.jigsawcorp.android.jigsaw.Fragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.EditTextDialog;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.NumberPickerDialog;
import com.jigsawcorp.android.jigsaw.Fragments.Dialogs.RadioButtonListDialogFragment;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.R;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsChoicePicker;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsEditText;
import com.jigsawcorp.android.jigsaw.View.CompoundViews.SettingsSwitch;

import java.util.ArrayList;
import java.util.Arrays;

public class EditProgramFragment extends Fragment {

    private Program mProgram;
    private SettingsChoicePicker mDaysPerWeekChoicePicker, mTrainingTypeChoicePicker, mProgramLengthChoicePicker, mSplitTypeChoicePicker;
    private SettingsEditText mNameEditText, mDescriptionEditText;
    private SettingsSwitch mIsDayBasedSwitch, mIsWeeklySwitch;

    @Override
    public void onCreate(Bundle savecInstanceState) {
        super.onCreate(savecInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_program, container, false);


        mNameEditText = (SettingsEditText) v.findViewById(R.id.fragment_edit_program_edit_text_name);
        mTrainingTypeChoicePicker = (SettingsChoicePicker) v.findViewById(R.id.fragment_edit_program_choice_picker_training_type);
        mSplitTypeChoicePicker = (SettingsChoicePicker) v.findViewById(R.id.fragment_edit_program_choice_picker_split_type);
        mDaysPerWeekChoicePicker = (SettingsChoicePicker) v.findViewById(R.id.fragment_edit_program_choice_picker_DaysPerWeek);
        mIsWeeklySwitch = (SettingsSwitch) v.findViewById(R.id.fragment_edit_program_switch_is_weekly);
        mProgramLengthChoicePicker = (SettingsChoicePicker) v.findViewById(R.id.fragment_edit_program_choice_picker_program_length);
        mIsDayBasedSwitch = (SettingsSwitch) v.findViewById(R.id.fragment_edit_program_is_day_based_switch);
        mDescriptionEditText = (SettingsEditText) v.findViewById(R.id.fragment_edit_program_edit_text_description);

        mNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditProgramFragment", "mNameEditText.onClick(), mProgram.getName() = " + mProgram.getName());
                FragmentManager manager = getFragmentManager();
                EditTextDialog fragment = EditTextDialog.newInstance("Program Name", mProgram.getName());
                fragment.setConfirmedTextListener(new EditTextDialog.EditTextDialogListener() {
                    @Override
                    public void onTextConfirmed(String text) {
                        mNameEditText.setDescription(text);
                        mProgram.setName(text);
                        Log.i("EditProgramFragment", "onConfirmedTextListener, mProgram.getName() = " + mProgram.getName());

                    }
                });
                fragment.show(manager, "ProgramNameDialog");
            }
        });

        final ArrayList<String> trainingTypeStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_training_types)));
        mTrainingTypeChoicePicker.setChoice(trainingTypeStringArray.get(mProgram.getTrainingType().ordinal()));
        mTrainingTypeChoicePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Training Type", trainingTypeStringArray, mProgram.getTrainingType().ordinal());
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                       mTrainingTypeChoicePicker.setChoice(trainingTypeStringArray.get(position));
                       mProgram.setTrainingType(Program.TrainingTypes.values()[position]);
                       if (trainingTypeStringArray.get(position).equals(getResources().getString(R.string.array_training_types_resistance_training))) {
                           mSplitTypeChoicePicker.setVisibility(View.VISIBLE);
                       }
                       else {
                           mSplitTypeChoicePicker.setVisibility(View.GONE);
                       }
                    }
                });
                fragment.show(manager, "TrainingTypesDialog");
            }
        });

        final ArrayList<String> splitTypeStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_split_types)));
        mSplitTypeChoicePicker.setChoice(splitTypeStringArray.get(mProgram.getSpliType().ordinal()));
        mSplitTypeChoicePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment = RadioButtonListDialogFragment.newInstance("Split Type", splitTypeStringArray, mProgram.getSpliType().ordinal());
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                        mSplitTypeChoicePicker.setChoice(splitTypeStringArray.get(position));
                        mProgram.setSpliType(Program.SplitTypes.values()[position]);
                    }
                });
                fragment.show(manager, "SplitTypesDialog");
            }
        });

        //mDaysPerWeekSpinner = (Spinner) v.findViewById(R.id.activity_create_program_spinner_days_per_week);
        final ArrayList<String> daysPerWeekStringArray = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", getResources().getString(R.string.variable)));
        if (mProgram.getDaysPerWeek() != -1) {
            mDaysPerWeekChoicePicker.setChoice(daysPerWeekStringArray.get(mProgram.getDaysPerWeek() - 1));
        }
        else {
            mDaysPerWeekChoicePicker.setChoice(daysPerWeekStringArray.get(daysPerWeekStringArray.size() - 1));
        }
        mDaysPerWeekChoicePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RadioButtonListDialogFragment fragment;
                if (mProgram.getDaysPerWeek() == -1) {
                    fragment = RadioButtonListDialogFragment.newInstance("Days Per Week", daysPerWeekStringArray, daysPerWeekStringArray.size() - 1);

                }
                else {
                    fragment = RadioButtonListDialogFragment.newInstance("Days Per Week", daysPerWeekStringArray, mProgram.getDaysPerWeek() - 1);
                }
                fragment.setConfirmedChoiceListener(new RadioButtonListDialogFragment.RadioButtonListDialogFragmentListener() {
                    @Override
                    public void onChoiceConfirmed(int position) {
                        mDaysPerWeekChoicePicker.setChoice(daysPerWeekStringArray.get(position));
                        if (position == daysPerWeekStringArray.size() - 1) {
                            mProgram.setDaysPerWeek(-1);
                        }
                        else {
                            mProgram.setDaysPerWeek(Integer.valueOf(daysPerWeekStringArray.get(position)));
                        }
                    }
                });
                fragment.show(manager, "DaysPerWeekDialog");
            }
        });


        mIsWeeklySwitch.setSettingsSwitchListener(new SettingsSwitch.SettingsSwitchListener() {
            @Override
            public void onSwitchChanged(boolean value) {
                if (value) {
                    mProgram.setDuration(7);
                    mProgramLengthChoicePicker.setVisibility(View.GONE);
                }
                else {
                    mProgram.setDuration(Integer.valueOf(mProgramLengthChoicePicker.getChoice()));
                    mProgramLengthChoicePicker.setVisibility(View.VISIBLE);
                }
            }
        });
        mIsWeeklySwitch.setSwitch(mProgram.getDuration() == 7);

        mProgramLengthChoicePicker.setChoice(String.valueOf(mProgram.getDuration()));
        mProgramLengthChoicePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                NumberPickerDialog fragment = NumberPickerDialog.newInstance("Program Length", mProgram.getDuration());
                fragment.setConfirmedNumberListener(new NumberPickerDialog.NumberPickerDialogListener() {
                    @Override
                    public void onNumberConfirmed(int number) {
                        mProgramLengthChoicePicker.setChoice(String.valueOf(number) + " days");
                        mProgram.setDuration(number);
                    }
                });
                fragment.show(manager, "TrainingTypesDialog");
            }
        });

        /*
        final ArrayList<String> programLengthStringArray = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_program_length)));
        if (mProgram.getDuration() == )
        mProgramLengthRadioButtonList.setChoice(mProgram.getDuration());
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
*/
        mIsDayBasedSwitch.setSwitch(!mProgram.isDayBased());
        mIsDayBasedSwitch.setSettingsSwitchListener(new SettingsSwitch.SettingsSwitchListener() {
            @Override
            public void onSwitchChanged(boolean value) {
                mProgram.setDayBased(value);
            }
        });

        mDescriptionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                EditTextDialog fragment = EditTextDialog.newInstance("Program Description", mProgram.getDescription());
                fragment.setConfirmedTextListener(new EditTextDialog.EditTextDialogListener() {
                    @Override
                    public void onTextConfirmed(String text) {
                        mDescriptionEditText.setDescription(text);
                        mProgram.setDescription(text);
                    }
                });
                fragment.show(manager, "ProgramDescriptionDialog");
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

    public boolean verifyFields() {
        if (mNameEditText.getDescription().equals(getResources().getString(R.string.empty_program_name_filler)) || mNameEditText.getDescription().isEmpty()) {
            Toast.makeText(getContext(), "Your program must have a name!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
 }
