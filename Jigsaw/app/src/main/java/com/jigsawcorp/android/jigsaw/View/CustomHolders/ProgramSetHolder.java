package com.jigsawcorp.android.jigsaw.View.CustomHolders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.PerformedSet;
import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.R;

public class ProgramSetHolder {
    private static ProgramSet mProgramSet;
    private static TextView mPositionTextView;
    private static TextView mRepsTextView;


    public static View getViewFromSet(LayoutInflater inflater, final Context context, ProgramSet programSet, int index) {
        View setView = inflater.inflate(R.layout.list_item_program_set, null);


        mPositionTextView = (TextView) setView.findViewById(R.id.list_item_program_set_position_indicator);
        mRepsTextView = (TextView) setView.findViewById(R.id.list_item_program_set_reps_container);

        setRepsTextViewFormat(programSet, context, mRepsTextView);
        mPositionTextView.setText(String.valueOf(index + 1));

        return setView;
    }

    public static void replaceSetHolder(View v, Context context, ProgramSet programSet) {
        setRepsTextViewFormat(programSet, context, (TextView) v.findViewById(R.id.list_item_program_set_reps_container));
    }

    public static void setRepsTextViewFormat(ProgramSet programSet, Context context, TextView textView) {
        if (programSet.isAmrap()) {
            textView.setText(context.getResources().getString(R.string.program_set_holder_reps_textView_amrap));

        }
        else if (programSet.isAmrapWithMin()) {
            textView.setText(context.getResources().getString(R.string.program_set_holder_reps_textView_amrap_min, programSet.getMinReps()));

        }
        else {
            if (programSet.getMinReps() == programSet.getMaxReps()) {
                textView.setText(context.getResources().getQuantityString(R.plurals.program_set_holder_reps_textView_single_value, programSet.getMinReps(), programSet.getMinReps()));
            }
            else {
                textView.setText(context.getResources().getString(R.string.program_set_holder_reps_textView_min_max, programSet.getMinReps(), programSet.getMaxReps()));
            }
        }
    }

}
