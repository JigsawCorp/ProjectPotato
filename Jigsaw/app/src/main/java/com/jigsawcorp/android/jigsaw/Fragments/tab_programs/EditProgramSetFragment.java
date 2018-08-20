package com.jigsawcorp.android.jigsaw.Fragments.tab_programs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jigsawcorp.android.jigsaw.Fragments.EditSetFragment;
import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.Program;
import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.R;

public class EditProgramSetFragment extends Fragment {
    private ProgramSet mProgramSet;
    private EditText mMinRepsEditText;
    private EditText mMaxRepsEditText;
    private Button mAddMinRepsButton;
    private Button mRemoveMinRepsButton;
    private Button mAddMaxRepsButton;
    private Button mRemoveMaxRepsButton;
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
                Log.i("EditSetFragment", "addWeight");
                mProgramSet.setMinReps(mProgramSet.getMinReps() + 1);
                displaySet();
                saveChanges();
            }
        });

        mRemoveMinRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_remove_min_reps);
        mRemoveMinRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditSetFragment", "removeWeight");
                mProgramSet.setMinReps(mProgramSet.getMinReps() - 1);
                displaySet();
                saveChanges();
            }
        });

        mAddMaxRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_add_max_reps);
        mAddMaxRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgramSet.setMaxReps(mProgramSet.getMaxReps() + 1);
                displaySet();
                saveChanges();
            }
        });

        mRemoveMaxRepsButton = (Button) v.findViewById(R.id.fragment_edit_program_set_button_remove_max_reps);
        mRemoveMaxRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgramSet.setMaxReps(mProgramSet.getMaxReps() - 1);
                displaySet();
                saveChanges();
            }
        });

        return v;
    }

    public void setProgramSet(ProgramSet programSet) {
        mProgramSet = programSet;
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
        }
    }

    private void saveChanges() {
        mListener.onSetChanged(mProgramSet);
    }

    public void setOnEventListener(OnEventListener listener) {
        mListener = listener;
    }
}
