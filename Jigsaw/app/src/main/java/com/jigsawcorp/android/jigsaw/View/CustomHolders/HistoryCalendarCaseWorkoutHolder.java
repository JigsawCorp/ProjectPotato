package com.jigsawcorp.android.jigsaw.View.CustomHolders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jigsawcorp.android.jigsaw.Model.ProgramSet;
import com.jigsawcorp.android.jigsaw.Model.Workout;
import com.jigsawcorp.android.jigsaw.R;

import java.text.DateFormat;

public class HistoryCalendarCaseWorkoutHolder {
    public static View getViewFromSet(LayoutInflater inflater, final Context context, Workout workout, View.OnClickListener listener) {
        View setView = inflater.inflate(R.layout.list_item_dialog_history_calendar_case_workout, null);

        TextView workoutTypeTextView = (TextView) setView.findViewById(R.id.list_item_dialog_history_calendar_case_workout_textView_type);
        workoutTypeTextView.setText("Type");

        TextView dateTextView = (TextView) setView.findViewById(R.id.list_item_dialog_history_calendar_case_workout_textView_date);
        dateTextView.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(workout.getStartDate()));
        ImageView viewWorkoutButton = (ImageView) setView.findViewById(R.id.list_item_dialog_history_calendar_case_workout_imageView_view_workout);

        setView.setOnClickListener(listener);

        return setView;
    }
}

