package com.jigsawcorp.android.jigsaw.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.R;

public class SetHolder {
    private PerformedSet mPerformedSet;
    private TextView mPositionTextView;
    private TextView mWeightTextView;
    private TextView mRepsTextView;


    public static View getViewFromSet(LayoutInflater inflater, final Context context, PerformedSet performedSet, int index) {
        View setView = inflater.inflate(R.layout.list_item_set, null);

        TextView mPositionTextView;
        TextView mWeightTextView;
        TextView mRepsTextView;

        mPositionTextView = (TextView) setView.findViewById(R.id.list_item_set_position_indicator);
        mWeightTextView = (TextView) setView.findViewById(R.id.list_item_set_weight);
        mRepsTextView = (TextView) setView.findViewById(R.id.list_item_set_reps);

        mWeightTextView.setText(String.valueOf(performedSet.getWeight()));
        mRepsTextView.setText(String.valueOf(performedSet.getReps()));
        mPositionTextView.setText(String.valueOf(index + 1));

        return setView;
    }

    public static void replaceSetHolder(View v, PerformedSet performedSet) {
        ((TextView) v.findViewById(R.id.list_item_set_weight)).setText(String.valueOf(performedSet.getWeight()));
        ((TextView) v.findViewById(R.id.list_item_set_reps)).setText(String.valueOf(performedSet.getReps()));
    }

}
