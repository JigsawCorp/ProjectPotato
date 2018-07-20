package com.jigsawcorp.android.jigsaw.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.R;

public class EditSetFragment extends Fragment {
    private Set mSet;
    private EditText mWeightTextView;
    private EditText mRepsTextView;
    private Button mAddWeightButton;
    private Button mRemoveWeightButton;
    private Button mAddRepsButton;
    private Button mRemoveRepsButton;
    private  OnSetModifiedListener mListener;


    public interface OnSetModifiedListener {
        void onSetChanged(Set set);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_set, container, false);
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
                mSet.setWeight(mSet.getWeight() + 5);
                displaySet();
                saveChanges();
            }
        });

        mRemoveWeightButton = (Button) v.findViewById(R.id.fragment_edit_set_button_remove_weight);
        mRemoveWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSet.setWeight(mSet.getWeight() - 5);
                displaySet();
                saveChanges();
            }
        });

        mAddRepsButton = (Button) v.findViewById(R.id.fragment_edit_set_button_add_reps);
        mAddRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSet.setReps(mSet.getReps() + 1);
                displaySet();
                saveChanges();
            }
        });

        mRemoveRepsButton = (Button) v.findViewById(R.id.fragment_edit_set_button_remove_reps);
        mRemoveRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSet.setReps(mSet.getReps() - 1);
                displaySet();
                saveChanges();
            }
        });

        v.bringToFront();
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

    public void setSet(Set set) {
        mSet = set;
        displaySet();
    }

    public void addNewSet(Set latestSet) {
        mSet = latestSet;
        displaySet();
    }

    private void displaySet() {
        if (mSet == null) {
            mWeightTextView.setText("");
            mRepsTextView.setText("");
        }
        else {
            mWeightTextView.setText(String.valueOf(mSet.getWeight()));
            mRepsTextView.setText(String.valueOf(mSet.getReps()));
        }
    }

    private void saveChanges() {
        mListener.onSetChanged(mSet);
    }
}
