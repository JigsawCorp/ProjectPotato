package com.jigsawcorp.android.jigsaw.Fragments.tab_programs;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.R;

public class EditProgramSetFragment extends Fragment {
    private ProgramSet mProgramSet;
    private EditText mMinRepsEditText, mMaxRepsEditText, mAmrapMinTextView;
    private Button mAddMinRepsButton, mRemoveMinRepsButton, mAddMaxRepsButton, mRemoveMaxRepsButton;
    private Switch mAmrapSwitch, mAmrapMinSwitch;
    private enum state {NORMAL, AMRAP, AMRAP_WITH_MIN}
    private state mCurrentState = state.NORMAL;
    private CompoundButton.OnCheckedChangeListener mAmrapSwitchListener, mAmrapMinSwitchListener;
    private OnEventListener mListener;


    public interface OnEventListener {
        void onSetChanged(ProgramSet programSet);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_program_set, container, false);
        Log.i("EditSetFragment", "onCreateView");
        mMinRepsEditText = (EditText) v.findViewById(R.id.fragment_edit_program_set_edit_text_min_reps);
        mMinRepsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mMaxRepsEditText = (EditText) v.findViewById(R.id.fragment_edit_program_set_edit_text_max_reps);

        mAddMinRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_add_min_reps);
        mAddMinRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentState.equals(state.NORMAL)) {
                    mProgramSet.setMinReps(mProgramSet.getMinReps() + 1);
                    displaySet();
                    saveChanges();
                }
            }
        });

        mRemoveMinRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_remove_min_reps);
        mRemoveMinRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentState.equals(state.NORMAL)) {
                    mProgramSet.setMinReps(mProgramSet.getMinReps() - 1);
                    displaySet();
                    saveChanges();
                }
            }
        });

        mAddMaxRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_add_max_reps);
        mAddMaxRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentState.equals(state.NORMAL)) {
                    mProgramSet.setMaxReps(mProgramSet.getMaxReps() + 1);
                    displaySet();
                    saveChanges();
                }
            }
        });

        mRemoveMaxRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_remove_max_reps);
        mRemoveMaxRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentState.equals(state.NORMAL)) {
                    mProgramSet.setMaxReps(mProgramSet.getMaxReps() - 1);
                    displaySet();
                    saveChanges();
                }
            }
        });

        mAmrapSwitch = (Switch) v.findViewById(R.id.fragment_edit_program_set_amrap_switch);
        mAmrapSwitchListener= new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    changeState(state.AMRAP);
                }
                else {
                    changeState(state.NORMAL);
                }
            }
        };

        mAmrapSwitch.setOnCheckedChangeListener(mAmrapSwitchListener);


        mAmrapMinSwitch = (Switch) v.findViewById(R.id.fragment_edit_program_set_amrap_min_switch);
        mAmrapMinSwitchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // mProgramSet.setIsAmrapWithMin(b);
                // enableMaxReps(b);
                //saveChanges();
                if (b) {
                    changeState(state.AMRAP_WITH_MIN);
                }
                else {
                    changeState(state.NORMAL);
                }
            }
        };
        mAmrapMinSwitch.setOnCheckedChangeListener(mAmrapMinSwitchListener);


        return v;
    }

    public void setProgramSet(ProgramSet programSet) {
        mProgramSet = programSet;
        if (!mProgramSet.isAmrap() && !mProgramSet.isAmrapWithMin()) {
            changeState(state.NORMAL);
        }
        else if (mProgramSet.isAmrap()) {
            changeState(state.AMRAP);
        }
        else {
            changeState(state.AMRAP_WITH_MIN);
        }
        displaySet();
    }

    public void addNewSet(ProgramSet latestProgramSet) {
        mProgramSet = latestProgramSet;
        displaySet();
    }

    private void displaySet() {
        if (mProgramSet == null) {
            mMaxRepsEditText.setText("");
            mMinRepsEditText.setText("");
        }
        else {
            mMinRepsEditText.setText(String.valueOf(mProgramSet.getMinReps()));
            mMaxRepsEditText.setText(String.valueOf(mProgramSet.getMaxReps()));
            mAmrapSwitch.setChecked(mProgramSet.isAmrap());
            mAmrapMinSwitch.setChecked(mProgramSet.isAmrapWithMin());
        }
    }

    private void saveChanges() {
        mListener.onSetChanged(mProgramSet);
    }

    public void setOnEventListener(OnEventListener listener) {
        mListener = listener;
    }


    private void enableMaxReps(boolean enable){
        if (enable) {
            mMaxRepsEditText.setTextColor(getResources().getColor(R.color.dark_black));
            mAddMaxRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.dark_black), PorterDuff.Mode.SRC_ATOP);
            mRemoveMaxRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.dark_black), PorterDuff.Mode.SRC_ATOP);

        }
        else {
            mMaxRepsEditText.setTextColor(getResources().getColor(R.color.grey));
            mAddMaxRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
            mRemoveMaxRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void enableMinReps(boolean enable) {
        if (enable) {
            mMinRepsEditText.setTextColor(getResources().getColor(R.color.dark_black));
            mAddMinRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.dark_black), PorterDuff.Mode.SRC_ATOP);
            mRemoveMinRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.dark_black), PorterDuff.Mode.SRC_ATOP);

        }
        else {
            mMinRepsEditText.setTextColor(getResources().getColor(R.color.grey));
            mAddMinRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
            mRemoveMinRepsButton.getBackground().setColorFilter(getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
        }
    }
    private void enableBothReps(boolean enable) {
        enableMinReps(enable);
        enableMaxReps(enable);
    }

    private void changeState(state wantedState) {
        switch (wantedState) {
            case NORMAL:
                if(mCurrentState.equals(state.AMRAP)) {
                    mAmrapSwitch.setOnCheckedChangeListener(null);
                    mAmrapSwitch.setChecked(false);
                    mAmrapSwitch.setOnCheckedChangeListener(mAmrapSwitchListener);
                    enableBothReps(true);
                }
                else if (mCurrentState.equals(state.AMRAP_WITH_MIN)) {
                    mAmrapMinSwitch.setOnCheckedChangeListener(null);
                    mAmrapMinSwitch.setChecked(false);
                    mAmrapMinSwitch.setOnCheckedChangeListener(mAmrapMinSwitchListener);
                    enableMaxReps(true);
                }
                mCurrentState = state.NORMAL;
                break;
            case AMRAP:
                if (mCurrentState.equals(state.AMRAP_WITH_MIN)) {
                    mAmrapMinSwitch.setOnCheckedChangeListener(null);
                    mAmrapMinSwitch.setChecked(false);
                    mAmrapMinSwitch.setOnCheckedChangeListener(mAmrapMinSwitchListener);
                    enableMinReps(false);
                }
                else if (mCurrentState.equals(state.NORMAL)) {
                    enableBothReps(false);
                }
                mCurrentState = state.AMRAP;
                break;
            case AMRAP_WITH_MIN:
                if (mCurrentState.equals(state.AMRAP)) {
                    mAmrapSwitch.setOnCheckedChangeListener(null);
                    mAmrapSwitch.setChecked(false);
                    mAmrapSwitch.setOnCheckedChangeListener(mAmrapSwitchListener);
                    enableMinReps(true);
                }
                else if (mCurrentState.equals(state.NORMAL)) {
                    enableMaxReps(false);
                }
                mCurrentState = state.AMRAP_WITH_MIN;
                break;
        }
    }

}
