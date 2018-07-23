package com.jigsawcorp.android.jigsaw.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.Set;
import com.jigsawcorp.android.jigsaw.R;

public class SetHolder {
    private Set mSet;
    private TextView mPositionTextView;
    private TextView mWeightTextView;
    private TextView mRepsTextView;


    public static View getViewFromSet(LayoutInflater inflater, final Context context, Set set, int index) {
        View setView = inflater.inflate(R.layout.list_item_set, null);

        TextView mPositionTextView;
        TextView mWeightTextView;
        TextView mRepsTextView;

        mPositionTextView = (TextView) setView.findViewById(R.id.list_item_set_position_indicator);
        mWeightTextView = (TextView) setView.findViewById(R.id.list_item_set_weight);
        mRepsTextView = (TextView) setView.findViewById(R.id.list_item_set_reps);

        mWeightTextView.setText(String.valueOf(set.getWeight()));
        mRepsTextView.setText(String.valueOf(set.getReps()));
        mPositionTextView.setText(String.valueOf(index + 1));

        return setView;
    }

    public static void replaceSetHolder(View v, Set set) {
        ((TextView) v.findViewById(R.id.list_item_set_weight)).setText(String.valueOf(set.getWeight()));
        ((TextView) v.findViewById(R.id.list_item_set_reps)).setText(String.valueOf(set.getReps()));
    }

}
