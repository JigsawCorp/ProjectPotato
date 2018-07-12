package com.jigsawcorp.android.jigsaw.Fragments;

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
        return v;
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
}
