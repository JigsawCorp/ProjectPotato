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

    public static View getViewFromSet(LayoutInflater inflater, final Context context, Set set) {
        View setView = inflater.inflate(R.layout.list_item_set, null);

        TextView mPositionTextView;
        TextView mWeightTextView;
        TextView mRepsTextView;

        mPositionTextView = (TextView) setView.findViewById(R.id.list_item_set_position_indicator);
        mWeightTextView = (TextView) setView.findViewById(R.id.list_item_set_weight);
        mRepsTextView = (TextView) setView.findViewById(R.id.list_item_set_reps);

        mWeightTextView.setText(String.valueOf(set.getWeight()));
        mRepsTextView.setText(String.valueOf(set.getReps()));

        setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(context.getResources().getColor(R.color.caldroid_sky_blue));
            }
        });

        return setView;
    }
}
