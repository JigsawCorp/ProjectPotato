package com.jigsawcorp.android.jigsaw.Fragments;

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

import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.R;

public class EditSetFragment extends Fragment {
    private PerformedSet mPerformedSet;
    private EditText mWeightTextView;
    private EditText mRepsTextView;
    private Button mAddWeightButton;
    private Button mRemoveWeightButton;
    private Button mAddRepsButton;
    private Button mRemoveRepsButton;
    private  OnSetModifiedListener mListener;


    public interface OnSetModifiedListener {
        void onSetChanged(PerformedSet performedSet);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_set, container, false);
        Log.i("EditSetFragment", "onCreateView");
        mWeightTextView = (EditText) v.findViewById(R.id.fragment_edit_set_edit_text_weight);
        mWeightTextView.addTextChangedListener(new TextWatcher() {
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
        mRepsTextView = (EditText) v.findViewById(R.id.fragment_edit_set_edit_text_reps);

        mAddWeightButton = (Button) v.findViewById(R.id.fragment_edit_set_button_add_weight);
        mAddWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditSetFragment", "addWeight");
                mPerformedSet.setWeight(mPerformedSet.getWeight() + 5);
                displaySet();
                saveChanges();
            }
        });

        mRemoveWeightButton = (Button) v.findViewById(R.id.fragment_edit_set_button_remove_weight);
        mRemoveWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditSetFragment", "removeWeight");
                mPerformedSet.setWeight(mPerformedSet.getWeight() - 5);
                displaySet();
                saveChanges();
            }
        });

        mAddRepsButton = (Button) v.findViewById(R.id.fragment_edit_set_button_add_reps);
        mAddRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPerformedSet.setReps(mPerformedSet.getReps() + 1);
                displaySet();
                saveChanges();
            }
        });

        mRemoveRepsButton = (Button) v.findViewById(R.id.fragment_edit_set_button_remove_reps);
        mRemoveRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPerformedSet.setReps(mPerformedSet.getReps() - 1);
                displaySet();
                saveChanges();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            if (context instanceof OnSetModifiedListener) {
                mListener = (OnSetModifiedListener) context;
            }
            else {
                if (getParentFragment() != null && getParentFragment() instanceof OnSetModifiedListener) {
                    mListener = (OnSetModifiedListener) getParentFragment();
                }
                else {
                    throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
                }
            }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setPerformedSet(PerformedSet performedSet) {
        mPerformedSet = performedSet;
        displaySet();
    }

    public void addNewSet(PerformedSet latestPerformedSet) {
        mPerformedSet = latestPerformedSet;
        displaySet();
    }

    private void displaySet() {
        if (mPerformedSet == null) {
            mWeightTextView.setText("");
            mRepsTextView.setText("");
        }
        else {
            mWeightTextView.setText(String.valueOf(mPerformedSet.getWeight()));
            mRepsTextView.setText(String.valueOf(mPerformedSet.getReps()));
        }
    }

    private void saveChanges() {
        mListener.onSetChanged(mPerformedSet);
    }
}
